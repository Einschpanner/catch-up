package com.einschpanner.catchup.post.service;

import com.einschpanner.catchup.domain.post.dao.PostRepository;
import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.PostDto;
import com.einschpanner.catchup.domain.user.dao.UserRepository;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.post.exception.PostAccessDeniedException;
import com.einschpanner.catchup.post.exception.PostNotFoundException;
import com.einschpanner.catchup.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /**
     * 게시글 생성
     */
    @Transactional
    public Post save(Long userId, PostDto.CreateReq dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Post post = modelMapper.map(dto, Post.class);
        post.setUser(user);

        return postRepository.save(post);
    }

    /**
     * 게시글 List 모두 조회
     */
    @Transactional
    public List<Post> findAll() {
        return postRepository.findAll();
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
    public Post update(Long userId, Long postId, PostDto.UpdateReq dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Post post = this.findById(postId);
        if (post.isNotOwner(user)) throw new PostAccessDeniedException();

        post.updateMyPost(dto);
        return postRepository.save(post);
    }

    /**
     * 특정 게시글 1개 삭제하기
     */
    @Transactional
    public void delete(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Post post = this.findById(postId);
        if (post.isNotOwner(user)) throw new PostAccessDeniedException();

        post.setIsDeleted(Boolean.TRUE);
    }
}
