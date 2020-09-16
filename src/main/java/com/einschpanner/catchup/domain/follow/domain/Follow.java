package com.einschpanner.catchup.domain.follow.domain;

import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.global.model.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "T_FOLLOW")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}


