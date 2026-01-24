package com.davinchicoder.spring.webflux.infrastructure.api;

import com.davinchicoder.spring.webflux.application.new_transaction.NewTransactionRequest;
import com.davinchicoder.spring.webflux.application.new_transaction.NewTransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TransactionMapper {

    NewTransactionRequest toNewTransactionRequest(TransactionDto dto);
 
    FraudResultDto toFraudResultDto(NewTransactionResponse result);

}
