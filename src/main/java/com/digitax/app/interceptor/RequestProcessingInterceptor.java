package com.digitax.app.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Component
public class RequestProcessingInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RequestProcessingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        final String requestUrl = request.getRequestURI().toString();
        logger.info("Request:: Time {} : Url {}", startTime, requestUrl);
        logger.info("Request:: Url  {} : IP  {}", requestUrl, request.getHeader("X-Forwarded-For") + " : " + request.getRemoteAddr() + " " + request.getRemoteHost());
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        final String requestUrl = request.getRequestURI().toString();
        long time = System.currentTimeMillis();
        logger.info("Request:: Time {} : Url {}", time, requestUrl);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        final String requestUrl = request.getRequestURI().toString();
        long startTime = (Long) request.getAttribute("startTime");
        long time = System.currentTimeMillis();
        logger.info("Request:: Time {} : Url {}", time, requestUrl);
        logger.info("Request:: Diff {} : Url {}", time - startTime, requestUrl);
    }

}