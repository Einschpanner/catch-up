package com.einschpanner.catchup.domain.post.service;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.domain.PostLike;
import com.einschpanner.catchup.domain.post.dto.PostLikeDto;
import com.einschpanner.catchup.domain.post.exception.PostLikeDuplicatedException;
import com.einschpanner.catchup.domain.post.exception.PostLikeNotFoundException;
import com.einschpanner.catchup.domain.post.exception.PostNotFoundException;
import com.einschpanner.catchup.domain.post.repository.PostLikeQueryRepository;
import com.einschpanner.catchup.domain.post.repository.PostLikeRepository;
import com.einschpanner.catchup.domain.post.repository.PostRepository;
import com.einschpanner.catchup.domain.user.dao.UserRepository;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void toggle(Long postId, Long userId){

        PostLike postLike = postLikeQueryRepository.exists(postId, userId);
        if (postLike != null){
            postLikeRepository.deleteById(postLike.getPostLikeId());
            return;
        }

        Post post = Post.builder().postId(postId).build();
        User user = User.builder().userId(userId).build();

//        Post post = postRepository.findById(postId)
//                .orElseThrow(PostNotFoundException::new);
//        User user = userRepository.findById(userId)
//                .orElseThrow(UserNotFoundException::new);

        postLikeRepository.save(PostLike.of(post, user));
    }

    /**
     * 특정 게시글을 좋아요한 PostLike(사용자) 모두 찾기
     */
    public List<PostLike> findAllByPostId(Long postId){
        return postLikeQueryRepository.findAllByPostId(postId);
    }
}
