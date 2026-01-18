package com.davinchicoder.spring.webflux.infrastructure.database;

import com.davinchicoder.spring.webflux.domain.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TransactionEntityMapper {

    TransactionEntity toEntity(Transaction tx);

    Transaction toDomain(TransactionEntity tx);
}
