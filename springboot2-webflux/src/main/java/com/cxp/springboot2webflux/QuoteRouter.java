package com.cxp.springboot2webflux;

import com.cxp.springboot2webflux.webflux.programing.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

//@Configuration
public class QuoteRouter {

    @Bean
    public RouterFunction<ServerResponse> route( QuoteHandler quoteHandler, PersonHandler personHandler) {
        return RouterFunctions
                .route(GET("/hello").and(accept(TEXT_PLAIN)), personHandler::hello)
                .andRoute(POST("/echo").and(accept(TEXT_PLAIN).and(contentType(TEXT_PLAIN))), quoteHandler::echo)
                .andRoute(GET("/quotes").and(accept(APPLICATION_JSON)), quoteHandler::fetchQuotes)
                .andRoute(GET("/quotes").and(accept(APPLICATION_STREAM_JSON)), quoteHandler::streamQuotes)
                .andRoute(GET("/hello1").and(accept(APPLICATION_STREAM_JSON)), quoteHandler::hello);
    }
}