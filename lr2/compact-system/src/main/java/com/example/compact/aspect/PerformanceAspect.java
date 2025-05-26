package com.example.compact.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {
    @Around("execution(* com.example.compact.ConfigurationGenerator.generateConfigurations(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();
        System.out.println("Method " + joinPoint.getSignature().getName() + 
                          " executed in " + (endTime - startTime) / 1_000_000 + " ms");
        return result;
    }
}