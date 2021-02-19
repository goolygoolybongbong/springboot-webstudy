package com.example.book.springboot.domain.posts;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
       postsRepository.deleteAll();
    }

    @Test
    public void postSaveAndFetchTest() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String author = "user@example.com";

        postsRepository.save(Posts.builder()
                        .title(title)
                        .content(content)
                        .author(author)
                        .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertEquals(title, posts.getTitle());
        assertEquals(content, posts.getContent());
        assertEquals(author, posts.getAuthor());
    }

    @Test
    public void BaseTimeEntityRegistryTest() {
        // given
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        assertTrue(posts.getCreatedDate().isAfter(now));
        assertTrue(posts.getModifiedDate().isAfter(now));
    }
}
