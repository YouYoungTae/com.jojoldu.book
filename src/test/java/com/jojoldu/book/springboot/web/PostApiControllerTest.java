package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.service.posts.PostService;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostApiController postApiController;
    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }
    @Test
    public void PostsSaveServiceTest(){
        String title= "title";
        String content ="content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().
                title(title)
                .content(content)
                .author("author")
                .build();
        Long ret = postService.save( requestDto);
        assertThat(ret).isGreaterThan(0l);

    }


    @Test
    public void PostsSaveTest() throws Exception {
        //given
        String title= "title";
        String content ="content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().
                title(title)
                .content(content)
                .author("author")
                .build();

        String url ="http://localhost:" + port + "/api/vi/posts";

        //when
        ResponseEntity<Long> responseEntity =restTemplate.postForEntity( url,requestDto,Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0l);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    @Test
    public void Posts_updateTest() throws Exception {

        //given
        Posts savedPosts =postsRepository.save(Posts.builder()
            .title("title")
            .content("content")
            .author("author")
            .build());
        Long updateId= savedPosts.getId();
        String expectedTitle= "title2";
        String expectedContent="cotent2";

        PostsUpdateRequestDto requestDto =
                PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url= "http://localhost:"+ port + "/api/vi/posts/"+ updateId;
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,requestEntity ,Long.class);

        //then
        assertThat( responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0l);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
    @Test
    public void BaseTimeEnitityTest(){
        //given
        LocalDateTime now = LocalDateTime.of(2021,1,1,0,0,0);
        postsRepository.save( Posts.builder()
        .title("title")
        .content("content")
        .author("author")
        .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then

        Posts posts= postsList.get(0);

        System.out.println(">>>>>>>>>>>>>>>>>> createDate=" +posts.getCreatedDate()+ ", modifieddate =" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}