package com.einschpanner.catchup.domain.post.domain;

import com.einschpanner.catchup.domain.BaseTimeEntity;
import com.einschpanner.catchup.domain.post.dto.PostDto;
import com.einschpanner.catchup.domain.user.domain.User;
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
    @Setter
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "userId")
    @Setter
    private User user;

    @OneToMany(mappedBy = "post")
    private List<PostTag> postTags;

    /**
     * Update Dto to Post
     *
     * @param dto
     */
    public void updateMyPost(PostDto.UpdateReq dto) {
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.email = dto.getEmail();
        this.urlThumbnail = dto.getUrlThumbnail();
        this.cntLike = dto.getCntLike();
        this.cntComment = dto.getCntComment();
        this.isDeleted = dto.getIsDeleted();
    }

    public void plusCommentCnt() {
        this.cntComment++;
    }

    public void minusCommentCnt() {
        this.cntComment--;
    }

    public void plusLikeCnt() {
        this.cntLike++;
    }

    public void minusLikeCnt() {
        this.cntLike--;
    }

    public boolean isNotOwner(User user){
        return !this.user.getUserId().equals(user.getUserId());
    }
}

