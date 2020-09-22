package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.PostComment;
import com.einschpanner.catchup.domain.post.dto.PostCommentDto;
import com.einschpanner.catchup.domain.post.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    /*
    댓글/대댓 등록
     */
    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public void savePostComment(
            @RequestBody final PostCommentDto.Req req
    ) {
        postCommentService.savePostComment(req);
    }


    /*
    post에 대한 댓글 리스트 조회
     */
    @GetMapping("/{post_id}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<PostCommentDto.Res> getPostCommentList(
            @PathVariable final long post_id
    ) {
        List<PostComment> postCommentList = postCommentService.getPostCommentList(post_id);
        return postCommentList.stream()
                .map(PostCommentDto.Res::new)
                .collect(Collectors.toList());
    }

    /*
    대댓글 조회
     */
    @GetMapping("/comments/replies/{comment_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PostCommentDto.Res> getPostCommentReplyList(
            @PathVariable final long comment_id
    ) {
        List<PostComment> postCommentList = postCommentService.getPostCommentReplyList(comment_id);
        return postCommentList.stream()
                .map(PostCommentDto.Res::new)
                .collect(Collectors.toList());
    }

    /*
    댓글 삭제
     */
    @DeleteMapping("/comments/{comment_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostComment(
            @PathVariable final long comment_id
    ) {
        postCommentService.deletePostComment(comment_id);
    }

}
