package com.example.compact.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
public class PerformanceAspect {
    @Around("execution(* com.example.compact.combinator.ConfigurationGenerator.generateConfigurations(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();
        System.out.println("Method " + joinPoint.getSignature().getName() + 
                          " executed in " + (endTime - startTime) / 1_000_000 + " ms");
        return result;
    }
}