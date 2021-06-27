package com.jojoldu.book.springboot.framework;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class WebLogging {

    public WebLogging() {
        log.info("================================================WebLogging");
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMapping() { }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void deleteMapping() { }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() { }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() { }

    @Pointcut("getMapping() || deleteMapping() || postMapping() || putMapping()" )
    public void combinationPointCut() {

    }
    @Before("combinationPointCut()")
    @Order(0)
    public void logBefore(JoinPoint joinPoint) {
        log.info( "############################################################################################+"
                + "\n##########################  > " + joinPoint.getSignature().toLongString()
                + "\n##########################  Params: " + Arrays.toString(joinPoint.getArgs())) ;
        log.info( "######################################################################################_START");
    }
    @After("combinationPointCut()")
    public void logAfter(JoinPoint joinPoint) {
        log.info( "############################################################################################+"
                + "\n < " + joinPoint.getSignature().toLongString()) ;
        log.info( "########################################################################################_END");
    }

    @Before("combinationPointCut()")
    @Order(1)
    public void webValidation(JoinPoint joinPoint) {
        log.info( "####################################################################################_Validation:");
    }

}
