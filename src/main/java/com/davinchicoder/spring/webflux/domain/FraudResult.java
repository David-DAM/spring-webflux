package com.davinchicoder.spring.webflux.domain;

public record FraudResult(String transactionId, TransactionStatus status, int score) {

}
