package com.davinchicoder.spring.webflux.infrastructure.api;

import java.math.BigDecimal;

public record TransactionDto(String userId, BigDecimal amount, String currency, String country, String deviceId) {

}
