package com.einschpanner.catchup.post.service;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.domain.PostComment;
import com.einschpanner.catchup.domain.post.dto.PostCommentDto;
import com.einschpanner.catchup.domain.post.dao.PostCommentRepository;
import com.einschpanner.catchup.domain.post.dao.PostQueryRepository;
import com.einschpanner.catchup.domain.post.dao.PostRepository;
import com.einschpanner.catchup.domain.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.einschpanner.catchup.post.exception.PostCommentNotFoundException;
import com.einschpanner.catchup.post.exception.PostNotFoundException;
import com.einschpanner.catchup.user.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostQueryRepository postQueryRepository;

    @Transactional
    public PostComment savePostComment(PostCommentDto.Req req) {
        Post post = postRepository.findById(req.getPostId()).orElseThrow(PostNotFoundException::new);
        PostComment parents = null;
        if (req.getParentsId() != null)
            parents = postCommentRepository.findById(req.getParentsId()).orElseThrow(PostCommentNotFoundException::new);
        userRepository.findByEmail(req.getEmail()).orElseThrow(UserNotFoundException::new);

        return postCommentRepository.save(new PostComment(req, post, parents));
    }

    @Transactional
    public List<PostComment> getPostCommentList(Long post_id) {
        postRepository.findById(post_id).orElseThrow(PostNotFoundException::new);
        List<PostComment> PostCommentList = postQueryRepository.findAllByPostAndParents(post_id, null);
        return PostCommentList;
    }

    @Transactional
    public List<PostComment> getPostCommentReplyList(Long comment_id) {
        PostComment postComment = postCommentRepository.findById(comment_id)
                .orElseThrow(PostCommentNotFoundException::new);
        List<PostComment> PostCommentReplyList = postQueryRepository.findAllByPostAndParents(postComment.getPost().getPostId(), comment_id);
        return PostCommentReplyList;
    }

    @Transactional
    public void deletePostComment(Long comment_id) {
        PostComment postComment = postCommentRepository.findById(comment_id)
                .orElseThrow(PostCommentNotFoundException::new);
        postComment.setDeleted(Boolean.TRUE);
        postComment.getPost().minusCommentCnt();
    }
}
