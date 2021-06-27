package com.jojoldu.book.springboot.framework;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ServiceLogging {
    public ServiceLogging() {
        log.info("================================================ServiceLogging");
    }

    @Pointcut("execution(* com.jojoldu.book.springboot.*.*.*Service.*(..))")
    public void getService() { }

    @Before("getService()")
    public void beforeLog(JoinPoint joinPoint) {
        log.info( "============================================================================================+"
                + "\n==========================  > " + joinPoint.getSignature().toLongString()
                + "\n==========================  > " + Arrays.toString(joinPoint.getArgs())) ;
        log.info( "============================================================================================+");
    }
}
