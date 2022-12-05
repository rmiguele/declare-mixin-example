package com.example.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareMixin;

/**
 * @author Luis Alves
 */
@Aspect
public class ExampleAspect {

  @DeclareMixin(value = "com.example.service.* *")
  public ExampleInterface exampleInterface() {
    return new ExampleInterfaceImpl();
  }

}
