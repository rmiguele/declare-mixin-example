package com.example.demo.service;

import com.example.demo.aspect.ExampleAnnotation;
import com.example.demo.aspect.ExampleInterface;
import org.springframework.stereotype.Service;

/**
 * @author Luis Alves
 */
@Service
@ExampleAnnotation
public class ExampleService {

  @ExampleAnnotation
  public String hello() {
    String output = "Hello";
    if (this instanceof ExampleInterface) {
      output += ((ExampleInterface) this).world();
    }
    return output;
  }

}
