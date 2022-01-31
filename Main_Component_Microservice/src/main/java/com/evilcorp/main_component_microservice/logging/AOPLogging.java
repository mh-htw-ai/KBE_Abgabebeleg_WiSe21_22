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

    @Pointcut("execution(* com.evilcorp.main_component_microservice.user..*.*(..))")
    private void inUser(){}

    @Pointcut("publicMethod() && inUser()")
    private void publicInUser(){}

    @Pointcut("execution(* com.evilcorp.main_component_microservice.movie..*.*(..))")
    private void inMovie(){}

    @Pointcut("publicMethod() && inMovie()")
    private void publicInMovie(){}

    @Pointcut("execution(* com.evilcorp.main_component_microservice.user_movie_relations..*.*(..))")
    private void inMovieRelations(){}

    @Pointcut("publicMethod() && inMovieRelations()")
    private void publicInMovieRelations(){}

    @Around("publicInUser() || publicInMovie() || publicInMovieRelations()")
    public Object logAroundPublic(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isInfoEnabled()) {
            log.info("\nEntering: {}.{}()\nArgument/s = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isInfoEnabled()) {
                log.info("\nExiting: {}.{}()\nResult = {}",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("\nIllegal argument: {}\nIn {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }
}