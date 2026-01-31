package com.davinchicoder.spring.webflux.infrastructure.database;

import com.davinchicoder.spring.webflux.domain.Transaction;
import com.davinchicoder.spring.webflux.domain.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;

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

    public Mono<Transaction> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public Mono<Void> deleteAll() {
        return repository.deleteAll();
    }

    public Mono<Long> countByUserIdAndCreatedBefore(String userId, long secondsAgo) {
        return repository.countByUserIdAndCreatedAtBefore(userId, Instant.now().minusSeconds(secondsAgo));
    }

    public Mono<Long> countByUserIdAndDeviceIdAndStatusRejected(String userId, String deviceId) {
        return repository.countByUserIdAndDeviceIdAndStatus(userId, deviceId, TransactionStatus.REJECT);
    }

    public Mono<Long> countByUserIdAndCountryAndStatusRejected(String userId, String country) {
        return repository.countByUserIdAndCountryAndStatus(userId, country, TransactionStatus.REJECT);
    }


}
