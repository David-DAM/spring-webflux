package com.davinchicoder.spring.webflux.application.rules;

import com.davinchicoder.spring.webflux.domain.RuleResult;
import com.davinchicoder.spring.webflux.domain.Transaction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DeviceRuleChecker implements RuleChecker {

    @Override
    public Mono<RuleResult> check(Transaction tx) {

    }
}
