package com.einschpanner.catchup.domain.post.service;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.request.PostCreateRequest;
import com.einschpanner.catchup.domain.post.dto.request.PostUpdateRequest;
import com.einschpanner.catchup.domain.post.dto.response.PostResponse;
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
     * Post 생성
     * TODO : 사용자 추가하기
     */
    public Post save(PostCreateRequest postCreateRequest) {
        System.out.println(postCreateRequest.toString());
//        Post post = modelMapper.map(postCreateRequest, Post.class);
//        System.out.println(post.toString());

        Post post = postCreateRequest.toEntity();
        return postRepository.save(post);
    }

    /**
     * Post List 모두 조회
     */
    public List<PostResponse> findAll() {
        List<Post> postList = postRepository.findAll();

        return postList.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 특정 Post 1개 조회
     */
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId + " 포스트를 찾을 수 없습니다."));
    }

    /**
     * 특정 Post 1개 수정하기
     */
    public Post update(Long postId, PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        post.updateMyPost(postUpdateRequest);
        return postRepository.save(post);
    }

    /**
     * 특정 Post 1개 삭제하기
     */
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
}
