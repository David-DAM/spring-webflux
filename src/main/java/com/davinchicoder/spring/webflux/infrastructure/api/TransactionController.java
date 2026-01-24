package com.davinchicoder.spring.webflux.infrastructure.api;

import com.davinchicoder.spring.webflux.application.mediator.Mediator;
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

    private final Mediator mediator;
    private final TransactionMapper mapper;

    @PostMapping()
    public Mono<FraudResultDto> handle(@RequestBody Mono<TransactionDto> tx) {
        return tx.map(mapper::toNewTransactionRequest)
                .flatMap(mediator::dispatch)
                .map(mapper::toFraudResultDto);
    }
}
