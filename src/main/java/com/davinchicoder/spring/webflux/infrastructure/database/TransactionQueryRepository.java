package com.davinchicoder.spring.webflux.infrastructure.database;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Repository
public interface TransactionQueryRepository extends R2dbcRepository<TransactionEntity, String> {

    Mono<Long> countByUserIdAndCreatedAtBefore(String userId, Instant createdAtBefore);

}
