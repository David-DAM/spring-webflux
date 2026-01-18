package com.davinchicoder.spring.webflux.application.rules;

import com.davinchicoder.spring.webflux.domain.RuleResult;
import com.davinchicoder.spring.webflux.domain.Transaction;
import com.davinchicoder.spring.webflux.infrastructure.database.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class VelocityRuleChecker implements RuleChecker {

    public static final int TRANSACTION_TIME_WINDOW_SECONDS = 300;
    public static final int MAX_TRANSACTION_COUNT = 10;
    private final TransactionRepository repository;

    @Override
    public Mono<RuleResult> check(Transaction tx) {
        return repository.countTransactionsLastSeconds(tx.getUserId(), TRANSACTION_TIME_WINDOW_SECONDS)
                .map(count -> {
                            if (count > MAX_TRANSACTION_COUNT) {
                                return new RuleResult(VelocityRuleChecker.class.getSimpleName(), 60);
                            }

                            return new RuleResult(VelocityRuleChecker.class.getSimpleName(), 0);
                        }
                )
                .timeout(Duration.ofMillis(200))
                .onErrorReturn(new RuleResult(VelocityRuleChecker.class.getSimpleName(), 10));
    }
}
