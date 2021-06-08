package com.jojoldu.book.springboot.domain.posts;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest extends TestCase {

    private static Logger log  = LoggerFactory.getLogger(PostsRepositoryTest.class);
    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }
    @Test
    public void savePosts_loadPostsTest(){

        //given
        String title ="테스트 게시글";
        String content ="테스트 본문";

        postsRepository.save(Posts.builder()
                                    .title(title)
                                    .content(content)
                                    .author("jojoldu@gmail.com")
                                    .build());
        //when
        List<Posts> postsList =postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo( title);
        assertThat(posts.getId()).isGreaterThan(0l);
        log.info(String.format("=============== id:%d",posts.getId() ));
        assertThat(posts.getContent()).isEqualTo(content);

    }
}