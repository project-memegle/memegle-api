package com.krince.memegle.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Aspect
@Component
public class MethodCallAspect {

    private final int MAX_DEPTH_AREA_LENGTH = 28;
    private final String TRACE_ID_VALUE = "traceId";
    private final String TRACE_DEPTH_VALUE = "traceDepth";
    private final String START_TIME_VALUE = "startTime";
    private final String INDENT_MARKER = "|---";
    private final String REVERS_INDENT_MARKER = "---|";
    private final String CALL_STACK_MARKER = "|-->|";
    private final String CALL_END_MARKER = "|<--|";

    @Pointcut("execution(* com.krince.memegle..*(..))")
    public void allMethodPointcut() {}

    @Before("allMethodPointcut()")
    public void nextLog(JoinPoint joinPoint) {
        String traceId = MDC.get(TRACE_ID_VALUE);

        if (isFirstDepthMethod(traceId)) {
            traceId = generateRandomTraceId();
            MDC.put(TRACE_ID_VALUE, traceId);
        }

        int traceDepth = generateTraceDepth();
        long startTime = System.currentTimeMillis();

        MDC.put(START_TIME_VALUE + traceDepth, String.valueOf(startTime));

        String callStackDepthMarker = INDENT_MARKER.repeat(traceDepth) + CALL_STACK_MARKER;
        traceDepth++;
        MDC.put(TRACE_DEPTH_VALUE, String.valueOf(traceDepth));
        String target = joinPoint.getSignature().toShortString();

        log.info("[{}] {}: {}",traceId, callStackDepthMarker, target);
    }

    private Integer generateTraceDepth() {
        return Optional.ofNullable(MDC.get(TRACE_DEPTH_VALUE))
                .map(Integer::parseInt)
                .orElse(0);
    }

    private static String generateRandomTraceId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private boolean isFirstDepthMethod(String traceId) {
        return Optional.ofNullable(traceId).isEmpty();
    }

    @After("allMethodPointcut()")
    public void provLog(JoinPoint joinPoint) {
        String traceId = MDC.get(TRACE_ID_VALUE);
        Integer traceDepth = Optional.ofNullable(MDC.get(TRACE_DEPTH_VALUE))
                .map(Integer::parseInt)
                .orElse(1);

        traceDepth--;
        String depthIndicator = CALL_END_MARKER + REVERS_INDENT_MARKER.repeat(traceDepth);
        String target = joinPoint.getSignature().toShortString();

        long startTime = Long.parseLong(MDC.get(START_TIME_VALUE + traceDepth));
        long executionTime = System.currentTimeMillis() - startTime;

        if (executionTime > 200 && executionTime < 500) {
            log.warn("[{}] {}: {}: execution time: {} ms",traceId, depthIndicator, target, executionTime);
        }

        if (executionTime <= 200) {
            log.info("[{}] {}: {}: execution time: {} ms",traceId, depthIndicator, target, executionTime);
        }

        if (executionTime > 500) {
            log.error("[{}] {}: {}: execution time: {} ms",traceId, depthIndicator, target, executionTime);
        }


        MDC.put(TRACE_DEPTH_VALUE, String.valueOf(traceDepth));

        if (traceDepth == 0) {
            MDC.remove(TRACE_ID_VALUE);
            MDC.remove(TRACE_DEPTH_VALUE);
            MDC.remove(START_TIME_VALUE);
        }
    }

}
