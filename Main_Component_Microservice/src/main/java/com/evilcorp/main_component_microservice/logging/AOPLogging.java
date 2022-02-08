package com.evilcorp.main_component_microservice.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class AOPLogging {

    @Pointcut("execution(public * *(..))")
    private void publicMethod(){}

    @Pointcut("execution(private * *(..))")
    private void privateMethod(){}

    @Pointcut("execution(* com.evilcorp.main_component_microservice.user..*.*(..))")
    private void inUser(){}

    @Pointcut("publicMethod() && inUser()")
    private void publicInUser(){}

    @Pointcut("privateMethod() && inUser()")
    private void privateInUser(){}

    @Pointcut("execution(* com.evilcorp.main_component_microservice.movie..*.*(..))")
    private void inMovie(){}

    @Pointcut("publicMethod() && inMovie()")
    private void publicInMovie(){}

    @Pointcut("privateMethod() && inMovie()")
    private void privateInMovie(){}

    @Pointcut("execution(* com.evilcorp.main_component_microservice.user_movie_relations..*.*(..))")
    private void inMovieRelations(){}

    @Pointcut("publicMethod() && inMovieRelations()")
    private void publicInMovieRelations(){}

    @Pointcut("privateMethod() && publicInMovieRelations()")
    private void privateInMovieRelations(){}

    @Around("publicInUser() || publicInMovie() || publicInMovieRelations()")
    public Object logAroundPublic(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isInfoEnabled()) {
            this.beforeMethodCallLogging(joinPoint);
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isInfoEnabled()) {
                this.afterMethodCallLogging(joinPoint, result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            this.illegalArgumentExceptionLogging(joinPoint);
            throw e;
        }
    }

    @Around("privateInUser() || privateInMovie() || privateInMovieRelations()")
    public Object logAroundPrivate(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            this.beforeMethodCallLoggingDebug(joinPoint);
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                this.afterMethodCallLoggingDebug(joinPoint, result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            this.illegalArgumentExceptionLogging(joinPoint);
            throw e;
        }
    }

    private void beforeMethodCallLogging(ProceedingJoinPoint joinPoint){
        log.info("\nEntering: {}.{}()\nArgument/s = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    private void afterMethodCallLogging(ProceedingJoinPoint joinPoint, Object result){
        log.info("\nExiting: {}.{}()\nResult = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);
    }

    private void beforeMethodCallLoggingDebug(ProceedingJoinPoint joinPoint){
        log.info("\nEntering: {}.{}()\nArgument/s = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    private void afterMethodCallLoggingDebug(ProceedingJoinPoint joinPoint, Object result){
        log.info("\nExiting: {}.{}()\nResult = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);
    }

    private void illegalArgumentExceptionLogging(ProceedingJoinPoint joinPoint){
        log.error("\nIllegal argument: {}\nIn {}.{}()",
                Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }
}