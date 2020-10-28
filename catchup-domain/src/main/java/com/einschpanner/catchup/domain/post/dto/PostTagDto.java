package com.einschpanner.catchup.domain.post.dto;

import com.einschpanner.catchup.domain.tag.domain.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class PostTagDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Req {
        private Long postId;
        private List<String> postTags;
    }

    @Getter
    public class Res {
        private String tagName;

        public Res(Tag tag) {
            this.tagName = tag.getTagName();
        }
    }
}
