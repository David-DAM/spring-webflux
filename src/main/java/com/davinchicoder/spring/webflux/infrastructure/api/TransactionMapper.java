package com.davinchicoder.spring.webflux.infrastructure.api;

import com.davinchicoder.spring.webflux.domain.FraudResult;
import com.davinchicoder.spring.webflux.domain.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TransactionMapper {

    Transaction toDomain(TransactionDto dto);

    FraudResultDto toFraudResultDto(FraudResult result);

}
