package com.einschpanner.catchup.domain.blog.domain;

import com.einschpanner.catchup.domain.BaseTimeEntity;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column
    private String urlThumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private LocalDateTime publishedDate;

    public Blog updateBlog(Blog blog){
        this.title = blog.getTitle();
        this.description = blog.getDescription();
        this.publishedDate = blog.getPublishedDate();
        return this;
    }

    public void initCntLike(){
        this.cntLike = 0;
    }
}
