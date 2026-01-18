package com.davinchicoder.spring.webflux.application;

import com.davinchicoder.spring.webflux.application.rules.RuleChecker;
import com.davinchicoder.spring.webflux.domain.FraudDecision;
import com.davinchicoder.spring.webflux.domain.FraudResult;
import com.davinchicoder.spring.webflux.domain.RuleResult;
import com.davinchicoder.spring.webflux.domain.Transaction;
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

    private final TransactionRepository repository;
    private final EventPublisher eventPublisher;
    private final List<RuleChecker> rules;

    public Mono<FraudResult> process(Transaction tx) {

        return Mono.just(tx)
                .flatMapMany(validTx ->
                        Flux.fromIterable(rules)
                                .flatMap(rule -> rule.check(validTx))
                )
                .collectList()
                .flatMap(ruleResults -> this.aggregate(tx, ruleResults))
                .flatMap(repository::upsert)
                .flatMap(eventPublisher::publish)
                .onErrorResume(this::handleFailure);
    }

    private Mono<Transaction> aggregate(Transaction transaction, List<RuleResult> results) {

        int score = results.stream()
                .mapToInt(RuleResult::score)
                .sum();

        FraudDecision decision = score >= 70 ? FraudDecision.REJECT : FraudDecision.APPROVE;

        return Mono.just(new Transaction());
    }

    private Mono<Transaction> handleFailure(Throwable ex) {
        log.error("Error processing transaction", ex);
        return Mono.error(ex);
    }


}
