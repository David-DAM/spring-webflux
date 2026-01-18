package com.davinchicoder.spring.webflux.infrastructure.api;

import com.davinchicoder.spring.webflux.domain.TransactionStatus;

public record FraudResultDto(String transactionId, TransactionStatus status, int score) {

}
