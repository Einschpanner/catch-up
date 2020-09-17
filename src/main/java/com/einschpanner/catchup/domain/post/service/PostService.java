package com.einschpanner.catchup.domain.post.service;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.PostDto;
import com.einschpanner.catchup.domain.post.exception.PostNotFoundException;
import com.einschpanner.catchup.domain.post.repository.PostRepository;
import com.einschpanner.catchup.global.error.ErrorCode;
import com.einschpanner.catchup.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    /**
     * 게시글 생성
     */
    public Post save(PostDto.CreateRequest dto) {
        Post post = modelMapper.map(dto, Post.class);

        return postRepository.save(post);
    }

    /**
     * 게시글 List 모두 조회
     */
    public List<PostDto.Response> findAll() {
        List<Post> postList = postRepository.findAll();

        return postList.stream()
                .map(PostDto.Response::new)
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글 1개 조회
     */
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    /**
     * 특정 게시글 1개 수정하기
     */
    public Post update(Long postId, PostDto.UpdateRequest dto) {
        Post post = this.findById(postId);
        post.updateMyPost(dto);

        return postRepository.save(post);
    }

    /**
     * 특정 게시글 1개 삭제하기
     */
    public void delete(Long postId) {
        this.findById(postId);
        postRepository.deleteById(postId);
    }
}
