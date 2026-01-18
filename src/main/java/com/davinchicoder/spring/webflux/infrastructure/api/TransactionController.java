package com.davinchicoder.spring.webflux.infrastructure.api;

import com.davinchicoder.spring.webflux.application.FraudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final FraudService fraudService;
    private final TransactionMapper mapper;

    @PostMapping()
    public Mono<FraudResultDto> handle(@RequestBody Mono<TransactionDto> tx) {
        return tx.map(mapper::toDomain)
                .flatMap(fraudService::process)
                .map(mapper::toFraudResultDto);
    }
}
