package com.einschpanner.catchup.domain.user.domain;

import com.einschpanner.catchup.domain.blog.domain.Blog;
import com.einschpanner.catchup.global.common.models.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_USER")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String urlProfile;

    @Column
    private String addrRss;

    @Column
    private String addrGithub;

    @Column
    private String addrBlog;

    @Column
    private int cntFollowing;

    @Column
    private int cntFollower;

    @Column
    private String provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany
    @JoinColumn(name = "userId")
    private List<Blog> blogs;

    public String getRolekey() {
        return this.role.getKey();
    }

    public User update(String nickname, String picture, String providerId) {
        this.nickname = nickname;
        this.urlProfile = picture;
        this.provider = providerId;

        return this;
    }
}
