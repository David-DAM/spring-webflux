package com.davinchicoder.spring.webflux.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
public class Transaction {

    private String id;
    private String userId;
    private BigDecimal amount;
    private String currency;
    private String country;
    private String deviceId;
    private TransactionStatus status;
    private int score;
    private Instant createdAt;

}
