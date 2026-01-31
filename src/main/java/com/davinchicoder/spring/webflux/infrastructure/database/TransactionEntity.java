package com.davinchicoder.spring.webflux.infrastructure.database;

import com.davinchicoder.spring.webflux.domain.TransactionStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Table("transactions")
@Data
public class TransactionEntity {

    @Id
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
