package com.davinchicoder.spring.webflux.infrastructure.api;

public record TransactionDto(String userId, String amount, String currency, String country, String deviceId) {

}
