package com.davinchicoder.spring.webflux.application;

import com.davinchicoder.spring.webflux.application.rules.RuleChecker;
import com.davinchicoder.spring.webflux.domain.FraudResult;
import com.davinchicoder.spring.webflux.domain.RuleResult;
import com.davinchicoder.spring.webflux.domain.Transaction;
import com.davinchicoder.spring.webflux.domain.TransactionStatus;
import com.davinchicoder.spring.webflux.infrastructure.database.TransactionRepository;
import com.davinchicoder.spring.webflux.infrastructure.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudService {

    public static final int MAX_CONCURRENT_RULE_CHECKS = 4;
    public static final int FRAUD_THRESHOLD = 70;
    private final TransactionRepository repository;
    private final EventPublisher eventPublisher;
    private final List<RuleChecker> rules;

    public Mono<FraudResult> process(Transaction tx) {

        return Flux.fromIterable(rules)
                .flatMap(rule -> rule.check(tx), MAX_CONCURRENT_RULE_CHECKS)
                .collectList()
                .flatMap(ruleResults -> this.aggregate(tx, ruleResults))
                .flatMap(repository::upsert)
                .map(transaction -> new FraudResult(transaction.getId(), transaction.getStatus(), transaction.getScore()))
                .flatMap(result ->
                        triggerEventAsync(result)
                                .thenReturn(result)
                )
                .onErrorResume(this::handleFailure);
    }

    private Mono<Transaction> aggregate(Transaction transaction, List<RuleResult> results) {

        int score = results.stream()
                .mapToInt(RuleResult::score)
                .sum();

        TransactionStatus status = score >= FRAUD_THRESHOLD ? TransactionStatus.REJECT : TransactionStatus.APPROVE;
        transaction.setStatus(status);
        transaction.setScore(score);

        return Mono.just(transaction);
    }

    private Mono<Void> triggerEventAsync(FraudResult result) {
        return eventPublisher.publish(result)
                .doOnError(e ->
                        log.error("Failed to publish event for tx {}", result.transactionId(), e)
                )
                .onErrorResume(_ -> Mono.empty());
    }


    private Mono<FraudResult> handleFailure(Throwable ex) {
        log.error("Error processing transaction", ex);
        return Mono.error(ex);
    }


}
