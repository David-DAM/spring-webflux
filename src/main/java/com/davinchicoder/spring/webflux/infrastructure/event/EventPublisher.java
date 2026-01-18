package com.davinchicoder.spring.webflux.infrastructure.event;

import com.davinchicoder.spring.webflux.domain.FraudResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class EventPublisher {


    public Mono<Void> publish(FraudResult result) {
        log.info("Publishing event for tx {}", result.transactionId());
        return Mono.empty();
    }

}
