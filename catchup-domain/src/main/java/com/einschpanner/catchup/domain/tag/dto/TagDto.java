package com.einschpanner.catchup.domain.tag.dto;

import com.einschpanner.catchup.domain.post.domain.PostTag;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TagDto {

    @Getter
    public static class Res {
        private List<String> tagNameList = new ArrayList<>();

        public Res(List<PostTag> postTags) {
            for (PostTag tag : postTags) {
                tagNameList.add(tag.getTag().getTagName());
            }

        }
    }
}
