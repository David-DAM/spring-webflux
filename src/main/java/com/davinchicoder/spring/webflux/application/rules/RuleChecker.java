package com.davinchicoder.spring.webflux.application.rules;

import com.davinchicoder.spring.webflux.domain.RuleResult;
import com.davinchicoder.spring.webflux.domain.Transaction;
import reactor.core.publisher.Mono;

public interface RuleChecker {

    Mono<RuleResult> check(Transaction tx);
}
