package com.einschpanner.catchup.domain.tag.domain;

import com.einschpanner.catchup.common.models.BaseTimeEntity;
import com.einschpanner.catchup.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = Tag.TABLE_NAME)
@Getter
@NoArgsConstructor
public class Tag extends BaseTimeEntity {
    public static final String TABLE_NAME= "TAG";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagId;

    @ManyToOne
    private Post post;

    @Column(nullable = false, length = 30)
    private String tagName;
}

