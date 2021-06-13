package com.jojoldu.book.springboot.web;
import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.service.posts.PostService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto postsSaveRequestDto){
        log.info("=============+ save:" + postsSaveRequestDto);
        return postService.save(postsSaveRequestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto ){
        log.info(String.format("=============+ update id:%d , requestDto:%S", id,requestDto));
        return postService.update(id,requestDto);
    }

    @GetMapping(value = "/api/v1/posts/{id}" )
    public PostsResponseDto findById (@PathVariable Long id) {
        log.debug(String.format("===========+ findById: %d",id));
        PostsResponseDto dto =postService.findById( id);
        return  dto;
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete (@PathVariable Long id) {
        log.debug(String.format("===========+ delete: %d",id));
         postService.delete(id);
         return  id;
    }

}
