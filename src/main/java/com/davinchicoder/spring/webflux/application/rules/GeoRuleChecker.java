package com.davinchicoder.spring.webflux.application.rules;

import com.davinchicoder.spring.webflux.domain.RuleResult;
import com.davinchicoder.spring.webflux.domain.Transaction;
import com.davinchicoder.spring.webflux.infrastructure.database.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class GeoRuleChecker implements RuleChecker {

    private final TransactionRepository repository;

    @Override
    public Mono<RuleResult> check(Transaction tx) {
        return repository.countByUserIdAndCountryAndStatusRejected(tx.getUserId(), tx.getCountry())
                .map(count -> new RuleResult(GeoRuleChecker.class.getSimpleName(), count > 0 ? 30 : 0));
    }
}
