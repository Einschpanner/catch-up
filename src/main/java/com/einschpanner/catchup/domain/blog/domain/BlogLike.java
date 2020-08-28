package com.einschpanner.catchup.domain.blog.domain;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "T_BLOG_LIKE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogLikeId;

    @ManyToOne
    @JoinColumn(name = "blogId")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
