package com.nisum.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Aspect for logging method execution times.
 * This demonstrates the Aspect-Oriented Programming (AOP) capabilities of Spring.
 * Following SRP by focusing solely on cross-cutting concerns (logging).
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    /**
     * Pointcut for controller methods
     */
    @Pointcut("execution(* com.nisum.controller.*.*(..))")
    public void controllerMethods() {}

    /**
     * Pointcut for DAO methods
     */
    @Pointcut("execution(* com.nisum.dao.*.*(..))")
    public void daoMethods() {}

    /**
     * Pointcut for TextEditor methods
     */
    @Pointcut("execution(* com.nisum.model.TextEditor.*(..))")
    public void textEditorMethods() {}

    /**
     * Before advice - logs before method execution
     */
    @Before("controllerMethods()")
    public void logBeforeControllerMethodExecution() {
        logger.info("About to execute controller method");
    }

    /**
     * Around advice - logs method execution time
     * This demonstrates using @Around advice to measure and log method execution time
     */
    @Around("controllerMethods() || daoMethods() || textEditorMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get method name
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // Log method entry
        logger.info("Entering " + className + "." + methodName);

        // Record start time
        long startTime = System.currentTimeMillis();

        // Execute the method
        Object result = joinPoint.proceed();

        // Record end time and calculate duration
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Log method execution time
        logger.info(className + "." + methodName + " executed in " + duration + " ms");

        return result;
    }
}
