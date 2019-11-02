package com.cxp.springboot2webflux.webflux.programing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * @author : cheng
 * @date : 2019-10-27 21:57
 */
@Configuration
public class PersonRouter {

    @Bean
    public RouterFunction<ServerResponse> route(PersonHandler personHandler){
        return RouterFunctions.route(
                GET("/getPerson1").and(accept(TEXT_PLAIN)),personHandler::hello)
                .andRoute(GET("/requestBodyMono/{username}").and(accept(APPLICATION_JSON_UTF8)),
                        personHandler::requestBodyMono );
    }
}
