package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareMixin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Luis Alves
 */
@Aspect
@Component
public class ExampleAspect {

  private static final Logger log = LoggerFactory.getLogger(ExampleAspect.class);

  @DeclareMixin(value = "com.example.service.* *")
  public ExampleInterface exampleInterface() {
    return new ExampleInterfaceImpl();
  }

  @Around("@annotation(com.example.demo.aspect.ExampleAnnotation)")
  public Object debugAssembler(ProceedingJoinPoint joinPoint) throws Throwable {
    Object proceed = joinPoint.proceed();

    log.info("@Around is working!");

    return proceed;
  }
}
