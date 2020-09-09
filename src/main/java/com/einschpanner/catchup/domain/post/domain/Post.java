package com.einschpanner.catchup.domain.post.domain;

import com.einschpanner.catchup.domain.post.dto.request.PostUpdateRequest;
import com.einschpanner.catchup.domain.tag.domain.Tag;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.global.model.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_POST")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String email;

    @Column
    private String urlThumbnail;

    @Column
    private int cntLike;

    @Column
    private int cntComment;

    @Column
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany
    @JoinColumn(name = "tagId")
    private List<Tag> tags;

    /**
     * Update Dto to Post
     * @param postUpdateRequest
     */
    public void updateMyPost(PostUpdateRequest postUpdateRequest) {
        // null 이 아닌 것들만 업데이트?
        this.title = postUpdateRequest.getTitle();
        this.description = postUpdateRequest.getDescription();
        this.email = postUpdateRequest.getEmail();
        this.urlThumbnail = postUpdateRequest.getUrlThumbnail();
        this.cntLike = postUpdateRequest.getCntLike();
        this.cntComment = postUpdateRequest.getCntComment();
        this.isDeleted = postUpdateRequest.isDeleted();
    }

}

