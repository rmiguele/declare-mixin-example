package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

import static java.util.Arrays.stream;

/**
 * @author Luis Alves
 */
@Aspect
@Component
public class ProduceContextAspect implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private static final Logger log = LoggerFactory.getLogger(ProduceContextAspect.class);

    @Around("@annotation(com.example.demo.aspect.ContextProducer)")
    public Object logAroundBehavior(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String contextName = signature.getMethod().getAnnotation(ContextProducer.class).value();
        Object proceed = joinPoint.proceed();
        Class<?> returningType = proceed.getClass();
        for (Object bean : applicationContext.getBeansWithAnnotation(ContextualService.class).values()) {
            List<Field> contextFields = stream(bean.getClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Context.class) &&
                            field.getAnnotation(Context.class).value().equals(contextName) &&
                            field.getType().equals(returningType)).toList();
            for (Field contextField : contextFields) {
                contextField.setAccessible(true);
                contextField.set(bean, proceed);
            }
        }
        return proceed;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
