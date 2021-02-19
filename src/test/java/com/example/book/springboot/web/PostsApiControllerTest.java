package com.example.book.springboot.web;

import com.example.book.springboot.domain.posts.Posts;
import com.example.book.springboot.domain.posts.PostsRepository;
import com.example.book.springboot.web.dto.PostsSaveRequestDto;
import com.example.book.springboot.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

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

    @AfterEach
    public void tearDown() {
        postsRepository.deleteAll();
    }


    @Test
    @WithMockUser(roles="USER")
    public void postsRegistryTest() throws Exception {
        // given
        String title =  "test title";
        String content = "test content";
        String author = "test@example.com";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        /*ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);*/
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        /*assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(0L < responseEntity.getBody());*/

        List<Posts> all = postsRepository.findAll();
        assertEquals(title, all.get(0).getTitle());
        assertEquals(content, all.get(0).getContent());
        assertEquals(author, all.get(0).getAuthor());

        System.out.println("TEST ID : " + all.get(0).getId());
    }

    @Test
    @WithMockUser(roles="USER")
    public void updateTest() throws Exception {
        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        //ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, Long.class);
        mvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        /*assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(0L < responseEntity.getBody());*/

        List<Posts> all = postsRepository.findAll();
        assertEquals(expectedTitle, all.get(0).getTitle());
        assertEquals(expectedContent, all.get(0).getContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void deleteTest() throws Exception{
        // given
        Posts posts = postsRepository.save(Posts.builder()
                .title("test title")
                .content("test content")
                .author("test author")
                .build());

        Long deleteId = posts.getId();
        String url = "http://localhost:" + port + "/api/v1/posts/" + deleteId;

        // when
        //restTemplate.delete(url);
        mvc.perform(delete(url)).andExpect(status().isOk());

        // then
        assertEquals(0, postsRepository.findAll().size());
    }
}
