package com.einschpanner.catchup.domain.comment.domain;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.global.common.models.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_COMMENT")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String contents;

    @Column
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @OneToMany
    @JoinColumn(name = "parentsId")
    private List<Comment> parents;
}
