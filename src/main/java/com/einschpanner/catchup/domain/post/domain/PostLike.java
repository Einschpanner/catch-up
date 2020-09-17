package com.einschpanner.catchup.domain.post.domain;

import com.einschpanner.catchup.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "T_POST_LIKE", uniqueConstraints={
        @UniqueConstraint(columnNames = {"postId", "userId"})
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    // 1안
    public PostLike(Post post, User user){
        this.post = post;
        this.user = user;
    }

    // 2안
    public static PostLike of(Post post, User user){
        return PostLike.builder()
                .post(post)
                .user(user)
                .build();
    }
}
