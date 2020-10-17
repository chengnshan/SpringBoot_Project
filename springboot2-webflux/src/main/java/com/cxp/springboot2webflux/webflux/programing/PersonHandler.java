package com.cxp.springboot2webflux.webflux.programing;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author : cheng
 * @date : 2019-10-27 21:46
 */
@Component
public class PersonHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ok().contentType(TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello Spring!"));
    }

    public Mono<ServerResponse> requestBodyMono(ServerRequest request) {
        String username = request.pathVariable("username");
        String userJson = "{\"username\":\""+username+"\"}";
        return ServerResponse.ok().contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(userJson));
    }
}
