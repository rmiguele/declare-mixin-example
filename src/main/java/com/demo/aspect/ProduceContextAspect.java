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

import java.lang.reflect.Method;
import java.util.Optional;

import static java.util.Arrays.asList;
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
        log.info("@Around is working!");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String contextName = signature.getMethod().getAnnotation(ContextProducer.class).value();
        Object proceed = joinPoint.proceed();
        Class<?> returningType = proceed.getClass();
        for (Object bean : applicationContext.getBeansWithAnnotation(ContextDecorator.class).values()) {
            Class<?> beanClass = bean.getClass();
            ContextDecorator annotation = beanClass.getAnnotation(ContextDecorator.class);
            if (proceed != null && contextName.equals(annotation.value())) {
                Optional<Method> methodOptional = stream(beanClass.getDeclaredMethods())
                        .filter(method -> method.getParameterCount() == 1 &&
                                asList(method.getParameterTypes()).contains(returningType) &&
                                returningType.equals(method.getReturnType())).findFirst();
                if (methodOptional.isPresent()) {
                    Method method = methodOptional.get();
                    method.setAccessible(true);
                    proceed = method.invoke(bean, proceed);
                }
            }
        }
        return proceed;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
