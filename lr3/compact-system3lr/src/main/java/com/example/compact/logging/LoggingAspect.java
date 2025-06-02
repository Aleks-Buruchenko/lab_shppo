package com.example.compact.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.compact.components.ComponentFactory.createComponent(String, ..)) && args(id, ..)")
    public void logComponentCreation(String id) {
        System.out.println("AOP: Created component " + id);
    }
}