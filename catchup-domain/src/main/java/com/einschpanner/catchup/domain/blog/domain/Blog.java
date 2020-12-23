package com.einschpanner.catchup.domain.blog.domain;

import com.einschpanner.catchup.domain.BaseTimeEntity;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_BLOG")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Blog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, unique = true)
    private String link;

    @Column
    private int cntLike;

    @Column(columnDefinition = "TEXT")
    private String urlThumbnail;

    @Column
    private LocalDateTime publishedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public Blog update(Blog blog){
        this.title = blog.getTitle();
        this.description = blog.getDescription();
        this.urlThumbnail = blog.getUrlThumbnail();
        this.publishedDate = blog.getPublishedDate();
        return this;
    }

    public void updateUser(User user){
        this.user = user;
    }
}
