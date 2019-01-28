package com.zhenikhov.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoggerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        LOGGER.info(String.format("Session: %s Method: %s URL: %s Authorized: %b  -- Request is processing",
                request.getSession().getId(),
                request.getMethod(),
                request.getRequestURI(),
                request.getUserPrincipal() != null));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) {
        LOGGER.info(String.format("Session: %s With errors: %b -- Request has processed",
                request.getSession().getId(),
                ex != null));
    }
}
