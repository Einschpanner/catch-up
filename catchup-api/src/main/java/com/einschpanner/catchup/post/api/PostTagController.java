package com.einschpanner.catchup.post.api;

import com.einschpanner.catchup.domain.post.dto.PostTagDto;
import com.einschpanner.catchup.domain.tag.dto.TagDto;
import com.einschpanner.catchup.post.service.PostTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostTagController {
    private final PostTagService postTagService;

    /**
     * 포스트 태그 저장
     *
     * @param req
     */
    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public void savePostTag(
            @RequestBody final PostTagDto.Req req
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        postTagService.savePostTag(req, userId);
    }

    /**
     * 포스트 태그 리스트 조회
     *
     * @param postId
     * @return
     */
    @GetMapping("/{postId}/tags")
    @ResponseStatus(HttpStatus.OK)
    public TagDto.Res getPostTagList(
            @PathVariable final Long postId
    ) {
        return postTagService.getPostTagList(postId);
    }

    /**
     * 포스트 태그 삭제
     *
     * @param req
     */
    @DeleteMapping("/tags")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostTag(
            @RequestBody final PostTagDto.Req req
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        postTagService.deletePostTag(req, userId);
    }
}
