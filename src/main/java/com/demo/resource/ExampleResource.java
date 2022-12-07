package com.example.demo.resource;

import com.example.demo.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Luis Alves
 */
@RestController
public class ExampleResource {

  @Autowired
  private ExampleService service;

  @GetMapping("/hello-aspect")
  public String hello() {
    return service.hello();
  }

}
