package com.example.book.springboot.web;

import com.example.book.springboot.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
public class HelloControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    HelloController helloController;

    @Test
    @WithMockUser(roles = "USER")
    public void helloTest() throws Exception {
        String result = "hello";

        MockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(helloController);
        MockMvc mvc = standaloneMockMvcBuilder.build();
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(result));

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(result));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void helloDtoTest() throws Exception {
        String name = "hello";
        int amount = 1000;

        mockMvc.perform(get("/hello/dto").param("name", name).param("amount", Integer.toString(amount)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.amount").value(amount));
                /*.andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));*/
    }
}
