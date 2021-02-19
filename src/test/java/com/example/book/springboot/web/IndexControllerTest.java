package com.example.book.springboot.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void loadMainPageTest() {
        // given : None

        // when
        String body = this.restTemplate.getForObject("/", String.class);

        // then
        assertTrue(body.contains("스프링 부트로 시작하는 웹 서비스"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void loadSavePageTest() throws Exception {
        // given
        //String body = this.restTemplate.getForObject("/posts/save", String.class);
        String url = "http://localhost:" + port + "/posts/save";

        // when
        String body = mvc.perform(get(url)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        //then
        assertTrue(body.contains("게시글 등록"));
    }
}
