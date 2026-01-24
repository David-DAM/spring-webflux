package com.davinchicoder.spring.webflux.application.new_transaction;

import com.davinchicoder.spring.webflux.domain.TransactionStatus;

public record NewTransactionResponse(String transactionId, TransactionStatus status, int score) {


}
