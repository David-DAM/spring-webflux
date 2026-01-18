package com.davinchicoder.spring.webflux.domain;

import lombok.Data;

import java.time.Instant;

@Data
public class Transaction {

    private String id;
    private String userId;
    private String amount;
    private String currency;
    private String country;
    private String deviceId;
    private String type;
    private Instant createdAt;

}
