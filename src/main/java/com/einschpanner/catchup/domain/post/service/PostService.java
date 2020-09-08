package com.einschpanner.catchup.domain.post.service;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.request.PostCreateRequest;
import com.einschpanner.catchup.domain.post.dto.response.PostListResponse;
import com.einschpanner.catchup.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
//    private final ModelMapper modelMapper;

    /**
     * Post 생성
     * TODO : 사용자 추가하기
     */
    public void save(PostCreateRequest postCreateRequest){
        System.out.println(postCreateRequest.toString());
//        Post post = modelMapper.map(postCreateRequest, Post.class);
//        System.out.println(post.toString());

        Post post = postCreateRequest.toEntity();
        postRepository.save(post);
    }

    /**
     * Post List 조회
     */
    public List<PostListResponse> findAll(){
        List<Post> postList = postRepository.findAll();

        return postList.stream()
                .map(PostListResponse::of)
                .collect(Collectors.toList());
    }
}
