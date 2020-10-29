package com.einschpanner.catchup.global.common.testFactory.post;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.domain.PostTag;
import com.einschpanner.catchup.domain.post.dto.PostTagDto;
import com.einschpanner.catchup.domain.tag.domain.Tag;

import java.util.ArrayList;
import java.util.List;

public class TestPostTagFactory {
    public static List<PostTag> createPostTagList() {
        List<PostTag> postTagList = new ArrayList<PostTag>();
        postTagList.add(createPostTag("spring boot"));
        postTagList.add(createPostTag("spring data jpa"));
        postTagList.add(createPostTag("node.js"));

        return postTagList;
    }

    public static PostTag createPostTag(String tag) {
        return PostTag.builder()
                .post(Post.builder().build())
                .tag(Tag.builder()
                        .tagName(tag)
                        .build())
                .build();
    }

    public static PostTagDto.Req createPostTagDto() {
        return PostTagDto.Req.builder()
                .postId((long) 1)
                .postTags(createTagList())
                .build();
    }

    public static List<String> createTagList() {
        List<String> tags = new ArrayList<>();
        tags.add("spring boot");
        tags.add("spring data jpa");
        tags.add("mysql");
        return tags;
    }
}
