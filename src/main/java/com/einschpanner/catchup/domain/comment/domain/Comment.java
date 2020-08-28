package com.einschpanner.catchup.domain.comment.domain;

import com.einschpanner.catchup.common.models.BaseTimeEntity;
import com.einschpanner.catchup.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = Comment.TABLE_NAME)
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {
    public static final String TABLE_NAME = "COMMENT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @ManyToOne
    private Post post;

    @OneToOne
    private Comment parents;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String contents;

    @Column(columnDefinition="tinyint(1) default 0")
    private boolean isDeleted = false;
}
