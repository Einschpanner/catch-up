package com.einschpanner.catchup.domain.post.domain;

import com.einschpanner.catchup.common.models.BaseTimeEntity;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = Post.TABLE_NAME)
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {
    public static final String TABLE_NAME= "POST";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String email;

    @Column
    private String urlThumbnail;

    @Column
    @ColumnDefault(value = "0")
    private int cntLike = 0;

    @Column
    @ColumnDefault(value = "0")
    private int cntComment = 0;

    @Column(columnDefinition="tinyint(1) default 0")
    private boolean isDeleted = false;
}

