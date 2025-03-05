package com.example.nbc_outsourcingproject.config.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OrderAccessLoggingAspect {

    private final HttpServletRequest request;
    private final ObjectMapper objectMapper;

    @Around("@annotation(com.example.nbc_outsourcingproject.config.aop.annotation.Order)")
    public Object logOrderApiAccess(ProceedingJoinPoint joinPoint) throws Throwable {
//        Long userId = (Long) request.getAttribute("userId");
        String url = request.getRequestURI();
        long requestTimestamp = System.currentTimeMillis();

        String requestBody = objectMapper.writeValueAsString(joinPoint.getArgs());
//        log.info("AOP - Order API Request: 요청시각={}, RequestBody={}",
//                 requestTimestamp, requestBody);

        Object result = joinPoint.proceed();

        String responseBody = objectMapper.writeValueAsString(result);
        log.info("AOP - Order API Request: Timestamp={}, RequestBody={}, ResponseBody={}",
                requestTimestamp, requestBody, responseBody);

        return result;
    }
}
