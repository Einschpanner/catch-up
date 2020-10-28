package com.einschpanner.catchup.domain.blog.domain;

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
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String link;

    @Column
    private int cntLike;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private LocalDateTime publishedDate;

    @CreatedDate
    private LocalDateTime createdDate;

    public Blog updateBlog(Blog blog){
        this.title = blog.getTitle();
        this.description = blog.getDescription();
        this.email = blog.getEmail();
        this.publishedDate = blog.getPublishedDate();
        return this;
    }
}
