package com.example.nbc_outsourcingproject.global.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OrderAccessLoggingAspect {

    private final HttpServletRequest request;
    private final ObjectMapper objectMapper;

    @Around("@annotation(com.example.nbc_outsourcingproject.global.aop.annotation.Order)")
    public Object logOrderApiAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Long userId = (Long) request.getAttribute("userId");
        String url = request.getRequestURI();
        LocalDateTime now = LocalDateTime.now();

        String requestBody = objectMapper.writeValueAsString(joinPoint.getArgs());
        Object result = joinPoint.proceed();

        String responseBody = objectMapper.writeValueAsString(result);
        log.info("AOP - Order API Request: 요청 시각={}, RequestBody={}, ResponseBody={}",
                now, requestBody, responseBody);

        return result;
    }
}
