package com.example.demo.service;

import com.example.demo.aspect.ContextDecorator;
import org.springframework.stereotype.Service;

/**
 * @author Luis Alves
 */
@ContextDecorator("helloWorld")
@Service
public class ContextDecoratorService {

    public String world(String hello) {
        return hello + " World!";
    }

}
