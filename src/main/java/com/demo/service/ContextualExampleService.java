package com.example.demo.service;

import com.example.demo.aspect.Context;
import com.example.demo.aspect.ContextualService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

/**
 * @author Luis Alves
 */
@Scope(value = SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@ContextualService
public class ContextualExampleService {

    @Context("hello")
    private String hello;

    public String world() {
        return hello + " World!";
    }

}
