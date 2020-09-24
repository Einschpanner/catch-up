package com.einschpanner.catchup.domain.post.domain;

import com.einschpanner.catchup.domain.post.dto.PostCommentDto;
import com.einschpanner.catchup.global.model.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "T_POST_COMMENT")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private String contents;

    @Column
    @Setter
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "parentsId")
    private PostComment parents;

    @Builder
    public PostComment(PostCommentDto.Req req, Post post, PostComment parents) {
        this.post = post;
        this.parents = parents;
        this.email = req.getEmail();
        this.contents = req.getContents();
        this.isDeleted = Boolean.FALSE;
        post.plusCommentCnt();
    }

}
