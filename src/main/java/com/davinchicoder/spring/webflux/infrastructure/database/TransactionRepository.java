package com.davinchicoder.spring.webflux.infrastructure.database;

import com.davinchicoder.spring.webflux.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {

    private final TransactionQueryRepository repository;
    private final TransactionEntityMapper mapper;

    public Mono<Transaction> upsert(Transaction transaction) {
        TransactionEntity entity = mapper.toEntity(transaction);
        Mono<TransactionEntity> saved = repository.save(entity);
        return saved.map(mapper::toDomain);
    }


}
