package com.einschpanner.catchup.post.service;

import com.einschpanner.catchup.post.exception.PostNotFoundException;
import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.PostDto;
import com.einschpanner.catchup.domain.post.dao.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Post save(PostDto.CreateReq dto) {
        Post post = modelMapper.map(dto, Post.class);

        return postRepository.save(post);
    }

    /**
     * 게시글 List 모두 조회
     */
    @Transactional
    public List<PostDto.Res> findAll() {
        List<Post> postList = postRepository.findAll();

        return postList.stream()
                .map(PostDto.Res::new)
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글 1개 조회
     */
    @Transactional
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    /**
     * 특정 게시글 1개 수정하기
     */
    @Transactional
    public Post update(Long postId, PostDto.UpdateReq dto) {
        Post post = this.findById(postId);
        post.updateMyPost(dto);

        return postRepository.save(post);
    }

    /**
     * 특정 게시글 1개 삭제하기
     */
    @Transactional
    public void delete(Long postId) {
        Post post = this.findById(postId);
        post.setDeleted(Boolean.TRUE);
    }
}
