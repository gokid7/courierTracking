package com.courier.tracking.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionTimeAspect {
    @Around("execution(* *(..)) && @annotation(ExecutionTime)")
    public Object evaluateExecutionTime(ProceedingJoinPoint jointPoint) throws Throwable{
        long start = System.currentTimeMillis();
        try{
            return jointPoint.proceed();
        }finally {
            log.info(jointPoint.getSignature().getName() + " request executed in " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
