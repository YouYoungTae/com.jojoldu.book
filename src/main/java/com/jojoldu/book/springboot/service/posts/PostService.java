package com.jojoldu.book.springboot.service.posts;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsListResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private static Logger log = LoggerFactory.getLogger(PostService.class );
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto) {
        log.debug("=============+save:" +postsSaveRequestDto );
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts= postsRepository.findById(id)
                .orElseThrow ( () -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update( requestDto.getTitle() , requestDto.getContent());
        return id;

    }

    public PostsResponseDto findById(Long id) {
        Posts entity= postsRepository.findById( id).orElseThrow( () -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id ));
        return new PostsResponseDto(entity);
    }
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {

        return postsRepository.findAllDesc().stream()
                .map( PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public void delete(Long id) {
        log.info(String.format("=======delete: %d", id));
        Posts posts= postsRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("해당게시글이 없습니다."));
        postsRepository.delete(posts);

    }
}