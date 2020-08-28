package com.einschpanner.catchup.domain.follow.domain;

import com.einschpanner.catchup.common.models.BaseTimeEntity;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = Follow.TABLE_NAME)
@Getter
@NoArgsConstructor
public class Follow extends BaseTimeEntity {
    public static final String TABLE_NAME= "FOLLOW";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long followId;

    @ManyToOne
    private User following;

    @ManyToOne
    private User follower;
}


