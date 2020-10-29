package com.einschpanner.catchup.global.common.testFactory.post;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.PostDto;
import com.einschpanner.catchup.domain.tag.domain.Tag;
import com.einschpanner.catchup.domain.user.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestPostFactory {

    public static Post createPost(Long postId, User user, List<Tag> tags){
        return Post.builder()
                .postId(postId)
                .title("Test Title")
                .description("Test Description")
                .email("test@naver.com")
                .urlThumbnail("Test Thumbnail")
                .cntLike(0)
                .cntComment(0)
                .isDeleted(false)
                .user(user)
                .tags(tags)
                .build();
    }

    public static PostDto.CreateReq createPostDto() {
        return PostDto.CreateReq.builder()
                .title("Test Title")
                .description("Test Description")
                .email("test@naver.com")
                .urlThumbnail("Test Thumbnail")
                .build();
    }

    public static PostDto.UpdateReq updatePostDto(){
        return PostDto.UpdateReq.builder()
                .title("Update Test Title")
                .description("Update Test Description")
                .email("test@naver.com")
                .urlThumbnail("Update Thumbnail")
                .cntComment(10)
                .cntLike(10)
                .isDeleted(false)
                .build();
    }

    public static List<Post> findAllPosts() {
        return new ArrayList<>(
                Arrays.asList(
                        createPost(1L, null, null),
                        createPost(2L, null, null),
                        createPost(3L, null, null),
                        createPost(4L, null, null)
                ));
    }

    public static Post findPost(){
        User user = User.builder()
                .userId(1L)
                .nickname("Test User")
                .email("Test Email")
                .urlProfile("https://url.link")
                .build();

        return createPost(1L, user, null);
    }
}
