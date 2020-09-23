package com.einschpanner.catchup.domain.post.service;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.domain.PostComment;
import com.einschpanner.catchup.domain.post.dto.PostCommentDto;
import com.einschpanner.catchup.domain.post.exception.PostCommentNotFoundException;
import com.einschpanner.catchup.domain.post.exception.PostNotFoundException;
import com.einschpanner.catchup.domain.post.repository.PostCommentRepository;
import com.einschpanner.catchup.domain.post.repository.PostQueryRepository;
import com.einschpanner.catchup.domain.post.repository.PostRepository;
import com.einschpanner.catchup.domain.user.dao.UserRepository;
import com.einschpanner.catchup.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        Post post = this.postRepository.findById(req.getPostId()).orElseThrow(PostNotFoundException::new);
        PostComment parents = null;
        if (req.getParentsId() != null)
            parents = this.postCommentRepository.findById(req.getParentsId()).orElseThrow(PostCommentNotFoundException::new);
        // 머지하 충돌날 익셉션 usernotfoundException
        this.userRepository.findByEmail(req.getEmail()).orElseThrow(UserNotFoundException::new);

        return postCommentRepository.save(new PostComment(req, post, parents));
    }

    @Transactional
    public List<PostComment> getPostCommentList(Long post_id) {
        this.postRepository.findById(post_id).orElseThrow(PostNotFoundException::new);
        List<PostComment> PostCommentList = this.postQueryRepository.findAllByPostAndParents(post_id, null);
        return PostCommentList;
    }

    @Transactional
    public List<PostComment> getPostCommentReplyList(Long comment_id) {
        PostComment postComment = this.postCommentRepository.findById(comment_id).orElseThrow(PostCommentNotFoundException::new);
        List<PostComment> PostCommentReplyList = this.postQueryRepository.findAllByPostAndParents(postComment.getPost().getPostId(), comment_id);
        return PostCommentReplyList;
    }

    @Transactional
    public void deletePostComment(Long comment_id) {
        PostComment postComment = this.postCommentRepository.findById(comment_id).orElseThrow(PostCommentNotFoundException::new);
        postComment.setDeleted(Boolean.TRUE);
        postComment.getPost().minusCommentCnt();
    }
}
