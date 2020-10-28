package com.einschpanner.catchup.post.service;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.domain.PostTag;
import com.einschpanner.catchup.domain.post.dto.PostTagDto;
import com.einschpanner.catchup.domain.post.repository.PostRepository;
import com.einschpanner.catchup.domain.post.repository.PostTagQueryRepository;
import com.einschpanner.catchup.domain.post.repository.PostTagRepository;
import com.einschpanner.catchup.domain.tag.dao.TagQueryRepository;
import com.einschpanner.catchup.domain.tag.dao.TagRepository;
import com.einschpanner.catchup.domain.tag.domain.Tag;
import com.einschpanner.catchup.domain.tag.dto.TagDto;
import com.einschpanner.catchup.domain.user.dao.UserRepository;
import com.einschpanner.catchup.post.exception.PostNotFoundException;
import com.einschpanner.catchup.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostTagService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagQueryRepository postTagQueryRepository;
    private final PostTagRepository postTagRepository;
    private final TagQueryRepository tagQueryRepository;

    @Transactional
    public void savePostTag(PostTagDto.Req req, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findByPostIdAndUser_UserId(req.getPostId(), userId)
                .orElseThrow(PostNotFoundException::new);

        for (String tags : req.getPostTags()) {
            tags = tags.toLowerCase().trim();
            PostTag postTag = postTagQueryRepository.exists(post.getPostId(), tags);
            if (!ObjectUtils.isEmpty(postTag))
                continue;

            Tag tag = tagQueryRepository.exists(tags);
            if (ObjectUtils.isEmpty(tag)) {
                Tag newTag = Tag.builder()
                        .tagName(tags)
                        .build();
                tag = tagRepository.save(newTag);
            }

            postTagRepository.save(PostTag.of(post, tag));
        }
    }

    @Transactional
    public TagDto.Res getPostTagList(Long postId) {
        List<PostTag> postTagList = postTagQueryRepository.findAllByPostId(postId);

        return new TagDto.Res(postTagList);
    }

    @Transactional
    public void deletePostTag(PostTagDto.Req req, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findByPostIdAndUser_UserId(req.getPostId(), userId)
                .orElseThrow(PostNotFoundException::new);
        for (String tags : req.getPostTags()) {
            tags = tags.toLowerCase().trim();
            PostTag postTag = postTagQueryRepository.exists(post.getPostId(), tags);
            if (ObjectUtils.isEmpty(postTag))
                continue;

            postTagRepository.delete(postTag);
        }
    }
}
