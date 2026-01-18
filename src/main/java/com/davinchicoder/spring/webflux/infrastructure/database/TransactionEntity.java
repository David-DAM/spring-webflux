package com.davinchicoder.spring.webflux.infrastructure.database;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("transactions")
@Data
public class TransactionEntity {

    private String id;
    private String userId;
    private String amount;
    private String currency;
    private String country;
    private String deviceId;
    private String type;
    private Instant createdAt;

}
