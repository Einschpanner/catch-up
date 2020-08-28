package com.einschpanner.catchup.domain.blog.domain;

import com.einschpanner.catchup.global.common.models.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "T_BLOG")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String link;

    @Column
    private String urlThumbnail;

    @Column
    private int cntLike;
}
