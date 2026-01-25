package com.davinchicoder.spring.webflux.application.rules;

import com.davinchicoder.spring.webflux.domain.RuleResult;
import com.davinchicoder.spring.webflux.domain.Transaction;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmountRuleCheckerTest {

    public static final String RULE_CHECKER_NAME = "AmountRuleChecker";
    private final AmountRuleChecker checker = new AmountRuleChecker();

    @Test
    void shouldReturnScore40ForTransactionAboveThreshold() {
        Transaction tx = Transaction.builder()
                .amount(BigDecimal.valueOf(15_000D))
                .build();

        Mono<RuleResult> resultMono = checker.check(tx);

        StepVerifier.create(resultMono)
                .assertNext(result -> {
                    assertEquals(40, result.score());
                    assertEquals(RULE_CHECKER_NAME, result.rule());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnScore0ForTransactionBelowOrEqualThreshold() {
        Transaction tx = Transaction.builder()
                .amount(BigDecimal.valueOf(10_000D))
                .build();

        Mono<RuleResult> resultMono = checker.check(tx);

        StepVerifier.create(resultMono)
                .assertNext(result -> {
                    assertEquals(0, result.score());
                    assertEquals(RULE_CHECKER_NAME, result.rule());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnRuleNameAsAmountRuleChecker() {
        Transaction tx = Transaction.builder()
                .amount(BigDecimal.valueOf(5_000D))
                .build();

        Mono<RuleResult> resultMono = checker.check(tx);

        StepVerifier.create(resultMono)
                .assertNext(result -> assertEquals(RULE_CHECKER_NAME, result.rule()))
                .verifyComplete();
    }
}