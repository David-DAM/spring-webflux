package com.davinchicoder.spring.webflux.application.rules;

import com.davinchicoder.spring.webflux.domain.RuleResult;
import com.davinchicoder.spring.webflux.domain.Transaction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AmountRuleChecker implements RuleChecker {

    private static final double MAX_AMOUNT = 10_000;

    @Override
    public Mono<RuleResult> check(Transaction tx) {

        return Mono.fromSupplier(() -> {

            if (tx.getAmount().floatValue() > MAX_AMOUNT) {
                return new RuleResult(AmountRuleChecker.class.getSimpleName(), 40);
            }

            return new RuleResult(AmountRuleChecker.class.getSimpleName(), 0);
        });
    }
}
