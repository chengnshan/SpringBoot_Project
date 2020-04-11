package com.cxp.redissessiontwo.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author : cheng
 * @date : 2020-03-31 10:09
 */
@Slf4j
public class CustomSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("CustomSessionListener Created source : {}", se.getSource());
        log.info("CustomSessionListener Created sessionId : {}", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("CustomSessionListener Destroyed source : {}", se.getSource());
        log.info("CustomSessionListener Destroyed sessionId : {}", se.getSession().getId());
    }
}
