package com.einschpanner.catchup.domain.user.domain;

import com.einschpanner.catchup.common.models.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = User.TABLE_NAME)
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {
    public static final String TABLE_NAME= "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String urlProfile;

    @Column
    private String addrRss; // ? addrRSS (rss가 약자)

    @Column
    private String addrGithub;

    @Column
    private String addrBlog;

    @Column
    @ColumnDefault(value = "0")
    private int cntFollowing = 0;

    @Column
    @ColumnDefault(value = "0")
    private int cntFollower = 0;
}
