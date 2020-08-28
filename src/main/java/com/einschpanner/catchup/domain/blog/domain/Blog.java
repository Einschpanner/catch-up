package com.einschpanner.catchup.domain.blog.domain;

import com.einschpanner.catchup.common.models.BaseTimeEntity;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = Blog.TABLE_NAME)
@Getter
@NoArgsConstructor
public class Blog extends BaseTimeEntity {
    public static final String TABLE_NAME= "BLOG";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long blogId;

    @ManyToOne
    private User user;

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
    @ColumnDefault(value = "0")
    private int cntLike = 0;
}
