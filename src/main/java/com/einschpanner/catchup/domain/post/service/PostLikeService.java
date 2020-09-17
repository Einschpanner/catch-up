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
     * 게시글 좋아요 생성
     */
    public PostLike save(PostLikeDto.CreateRequest dto){

        PostLike postLike = postLikeQueryRepository.exists(dto.getPostId(), dto.getUserId());
        if (postLike != null) throw new PostLikeDuplicatedException();

        // 이게 맞나 각각의 service를 가져와서 찾는게 맞나..?!
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(PostNotFoundException::new);
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        return postLikeRepository.save(new PostLike(post, user));
    }

    /**
     * 게시글 좋아요 취소
     */
    public void delete(PostLikeDto.CancelRequest dto){

        PostLike postLike = postLikeQueryRepository.exists(dto.getPostId(), dto.getUserId());
        if (postLike == null) throw new PostLikeNotFoundException();

        postLikeRepository.deleteById(postLike.getPostLikeId());
    }

    /**
     * 특정 사용자가 좋아요한 PostLike(게시글) 모두 찾기
     */
    public List<PostLike> findAllByUserId(PostLikeDto.FindByUserRequest dto){
        return postLikeQueryRepository.findAllByUserId(dto.getUserId());
    }

    /**
     * 특정 게시글을 좋아요한 PostLike(사용자) 모두 찾기
     */
    public List<PostLike> findAllByPostId(PostLikeDto.FindByPostRequest dto){
        return postLikeQueryRepository.findAllByPostId(dto.getPostId());
    }
}
