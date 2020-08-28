package com.einschpanner.catchup.domain.post.domain;

import com.einschpanner.catchup.domain.tag.domain.Tag;
import com.einschpanner.catchup.global.common.models.BaseTimeEntity;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_POST")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}

