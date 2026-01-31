package com.davinchicoder.spring.webflux.IT;

import com.davinchicoder.spring.webflux.domain.TransactionStatus;
import com.davinchicoder.spring.webflux.infrastructure.api.FraudResultDto;
import com.davinchicoder.spring.webflux.infrastructure.api.TransactionDto;
import com.davinchicoder.spring.webflux.infrastructure.database.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TransactionIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TransactionRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll().block();
    }

    @Test
    void testSuccessfulTransaction() {

        TransactionDto request = new TransactionDto("DavinchiCoder", BigDecimal.valueOf(100.0), "EUR", "Spain", "Xiaomi Mi 10");

        webTestClient.post().uri("/api/v1/transactions")
                .bodyValue(request)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(FraudResultDto.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    FraudResultDto fraudResponse = response.getResponseBody();
                    assertNotNull(fraudResponse.transactionId());
                    assertEquals(TransactionStatus.APPROVE, fraudResponse.status());
                    assertEquals(0, fraudResponse.score());

                    repository.findById(fraudResponse.transactionId())
                            .subscribe(transaction -> assertEquals(TransactionStatus.APPROVE, transaction.getStatus()));
                });
    }
}
