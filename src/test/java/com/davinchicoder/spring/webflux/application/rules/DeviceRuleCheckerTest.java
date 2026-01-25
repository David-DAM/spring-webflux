package com.davinchicoder.spring.webflux.application.rules;

import com.davinchicoder.spring.webflux.domain.Transaction;
import com.davinchicoder.spring.webflux.infrastructure.database.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceRuleCheckerTest {

    public static final String RULE_CHECKER_NAME = "DeviceRuleChecker";
    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private DeviceRuleChecker deviceRuleChecker;

    @Test
    void check_shouldReturnScore20_whenCountGreaterThanZero() {
        // Arrange
        Transaction transaction = Transaction.builder()
                .userId("user1")
                .deviceId("device1")
                .build();

        when(repository.countByUserIdAndDeviceIdAndStatusRejected(anyString(), anyString()))
                .thenReturn(Mono.just(3L));

        // Act & Assert
        StepVerifier.create(deviceRuleChecker.check(transaction))
                .expectNextMatches(result -> result.score() == 20 && RULE_CHECKER_NAME.equals(result.rule()))
                .verifyComplete();

        verify(repository, times(1)).countByUserIdAndDeviceIdAndStatusRejected("user1", "device1");
    }

    @Test
    void check_shouldReturnScore0_whenCountIsZero() {
        // Arrange
        Transaction transaction = Transaction.builder()
                .userId("user2")
                .deviceId("device2")
                .build();
        when(repository.countByUserIdAndDeviceIdAndStatusRejected(anyString(), anyString()))
                .thenReturn(Mono.just(0L));

        // Act & Assert
        StepVerifier.create(deviceRuleChecker.check(transaction))
                .expectNextMatches(result -> result.score() == 0 && RULE_CHECKER_NAME.equals(result.rule()))
                .verifyComplete();

        verify(repository, times(1)).countByUserIdAndDeviceIdAndStatusRejected("user2", "device2");
    }

    @Test
    void check_shouldPropagateError_whenRepositoryThrowsException() {
        // Arrange
        Transaction transaction = Transaction.builder()
                .userId("user3")
                .deviceId("device3")
                .build();

        when(repository.countByUserIdAndDeviceIdAndStatusRejected(anyString(), anyString()))
                .thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(deviceRuleChecker.check(transaction))
                .expectErrorMessage("Database error")
                .verify();

        verify(repository, times(1)).countByUserIdAndDeviceIdAndStatusRejected("user3", "device3");
    }
}