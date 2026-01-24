package com.davinchicoder.spring.webflux.application.mediator;

import reactor.core.publisher.Mono;

public interface RequestHandler<T extends Request<R>, R> {

    Mono<R> handle(T request);

    Class<T> getRequestType();

}
