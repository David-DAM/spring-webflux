package com.davinchicoder.spring.webflux.application.mediator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class Mediator {

    Map<? extends Class<?>, RequestHandler<?, ?>> requestHandlerMap;

    public Mediator(List<RequestHandler<?, ?>> requestHandlers) {
        requestHandlerMap = requestHandlers.stream().collect(Collectors.toMap(RequestHandler::getRequestType, Function.identity()));
    }

    public <R, T extends Request<R>> Mono<R> dispatch(T request) {
        RequestHandler<T, R> handler = (RequestHandler<T, R>) requestHandlerMap.get(request.getClass());
        if (handler == null) {
            log.error("No handler found for request type: {}", request.getClass());
            return Mono.error(
                    new IllegalStateException(
                            "No handler for request " + request.getClass()
                    )
            );
        }
        return handler.handle(request);
    }


}
