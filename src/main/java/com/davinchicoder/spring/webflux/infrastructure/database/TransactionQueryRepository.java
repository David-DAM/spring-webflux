package com.davinchicoder.spring.webflux.infrastructure.database;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionQueryRepository extends R2dbcRepository<TransactionEntity, String> {
}
