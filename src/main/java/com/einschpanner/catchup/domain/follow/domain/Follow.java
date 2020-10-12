package com.einschpanner.catchup.domain.follow.domain;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.domain.PostLike;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.global.model.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "T_FOLLOW")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "followingId")
    private User following;

    @ManyToOne
    @JoinColumn(name = "followerId")
    private User follower;

    public static Follow of(User follower, User following){
        return Follow.builder()
                .follower(follower)
                .following(following)
                .build();
    }
}


