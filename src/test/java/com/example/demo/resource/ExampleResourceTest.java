package com.example.demo.resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * @author Luis Alves
 */
@SpringBootTest
@AutoConfigureMockMvc
class ExampleResourceTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void hello() throws Exception {
    mockMvc.perform(get("/hello-aspect")).andExpect(content().string(containsString("Hello world!")));
  }

}