package com.example.demo.service;

import com.example.demo.aspect.ContextProducer;
import org.springframework.stereotype.Service;

/**
 * @author Luis Alves
 */
@Service
public class ExampleService {

    @ContextProducer("helloWorld")
    public String hello() {
        return "Hello";
    }

}
