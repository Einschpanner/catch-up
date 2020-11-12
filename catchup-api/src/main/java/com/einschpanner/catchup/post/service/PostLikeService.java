package com.einschpanner.catchup.post.service;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.domain.PostLike;
import com.einschpanner.catchup.domain.post.dao.PostLikeQueryRepository;
import com.einschpanner.catchup.domain.post.dao.PostLikeRepository;
import com.einschpanner.catchup.domain.post.dao.PostRepository;
import com.einschpanner.catchup.domain.user.dao.UserRepository;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.einschpanner.catchup.post.exception.PostNotFoundException;
import com.einschpanner.catchup.user.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostLikeQueryRepository postLikeQueryRepository;


    /**
     * 게시글 좋아요 생성 or 삭제 (토글)
     */
    @Transactional
    public void toggle(Long postId, Long userId){

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        PostLike postLike = postLikeQueryRepository.exists(postId, userId);
        if (ObjectUtils.isEmpty(postLike)){
            post.plusLikeCnt();
            postLikeRepository.save(PostLike.of(post, user));
        } else {
            post.minusLikeCnt();
            postLikeRepository.deleteById(postLike.getPostLikeId());
        }
    }

    /**
     * 특정 게시글을 좋아요한 PostLike(사용자) 모두 찾기
     */
    @Transactional
    public List<PostLike> findAllByPostId(Long postId){
        return postLikeQueryRepository.findAllByPostId(postId);
    }
}
