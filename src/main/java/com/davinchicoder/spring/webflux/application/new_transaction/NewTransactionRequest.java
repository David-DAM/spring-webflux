package com.davinchicoder.spring.webflux.application.new_transaction;

import com.davinchicoder.spring.webflux.application.mediator.Request;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class NewTransactionRequest implements Request<NewTransactionResponse> {

    private String userId;
    private BigDecimal amount;
    private String currency;
    private String country;
    private String deviceId;

}
