package com.davinchicoder.spring.webflux.application.new_transaction;

import com.davinchicoder.spring.webflux.application.rules.RuleChecker;
import com.davinchicoder.spring.webflux.domain.RuleResult;
import com.davinchicoder.spring.webflux.domain.Transaction;
import com.davinchicoder.spring.webflux.domain.TransactionStatus;
import com.davinchicoder.spring.webflux.infrastructure.database.TransactionRepository;
import com.davinchicoder.spring.webflux.infrastructure.event.EventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewTransactionHandlerTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private NewTransactionHandler handler;


    @Test
    void testHandleTransactionApprove() {
        // Arrange
        List<RuleChecker> rules = List.of(
                _ -> Mono.just(new RuleResult("Rule1", 20)),
                _ -> Mono.just(new RuleResult("Rule2", 30))
        );
        ReflectionTestUtils.setField(handler, "rules", rules);

        NewTransactionRequest request = buildRequest();
        Transaction transaction = buildTransaction(request);
        when(repository.upsert(any())).thenReturn(Mono.just(transaction));
        when(eventPublisher.publish(any())).thenReturn(Mono.empty());

        // Assert
        StepVerifier.create(handler.handle(request))
                .assertNext(result -> {
                    assertEquals(TransactionStatus.APPROVE, result.status());
                    assertTrue(result.score() < NewTransactionHandler.FRAUD_THRESHOLD);
                })
                .verifyComplete();

    }

    @Test
    void testHandleTransactionReject() {
        // Arrange
        List<RuleChecker> rules = List.of(
                _ -> Mono.just(new RuleResult("Rule1", 50)),
                _ -> Mono.just(new RuleResult("Rule2", 40))
        );
        ReflectionTestUtils.setField(handler, "rules", rules);

        NewTransactionRequest request = buildRequest();
        Transaction transaction = buildTransaction(request);
        transaction.setStatus(TransactionStatus.REJECT);
        transaction.setScore(90);
        when(repository.upsert(any())).thenReturn(Mono.just(transaction));
        when(eventPublisher.publish(any())).thenReturn(Mono.empty());

        // Assert
        StepVerifier.create(handler.handle(request))
                .assertNext(result -> {
                    assertEquals(TransactionStatus.REJECT, result.status());
                    assertTrue(result.score() >= NewTransactionHandler.FRAUD_THRESHOLD);
                })
                .verifyComplete();

    }

    @Test
    void testHandleTransactionError() {
        // Arrange
        List<RuleChecker> rules = List.of(
                _ -> Mono.just(new RuleResult("Rule1", 50))
        );
        ReflectionTestUtils.setField(handler, "rules", rules);

        NewTransactionRequest request = buildRequest();
        when(repository.upsert(any())).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(handler.handle(request))
                .verifyError();

    }

    private NewTransactionRequest buildRequest() {
        NewTransactionRequest request = new NewTransactionRequest();
        request.setUserId("user123");
        request.setDeviceId("device123");
        request.setAmount(BigDecimal.valueOf(100.0));
        request.setCurrency("USD");
        request.setCountry("US");
        return request;
    }

    private Transaction buildTransaction(NewTransactionRequest request) {
        return Transaction.builder()
                .id(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .deviceId(request.getDeviceId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .country(request.getCountry())
                .createdAt(Instant.now())
                .status(TransactionStatus.APPROVE)
                .score(50)
                .build();
    }
}