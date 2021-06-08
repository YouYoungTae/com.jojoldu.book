package com.jojoldu.book.springboot.web;
import com.jojoldu.book.springboot.service.posts.PostService;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private static Logger log = LoggerFactory.getLogger(PostApiController.class );
    private final PostService postService;

    @PostMapping("/api/vi/posts")
    public Long save(@RequestBody PostsSaveRequestDto postsSaveRequestDto){
        log.debug("=============+ save:" + postsSaveRequestDto);
        return postService.save(postsSaveRequestDto);
    }


}
