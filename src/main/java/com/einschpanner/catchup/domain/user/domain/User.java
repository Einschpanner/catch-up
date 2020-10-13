package com.einschpanner.catchup.domain.user.domain;

import com.einschpanner.catchup.domain.blog.domain.Blog;
import com.einschpanner.catchup.domain.user.dto.ProfileDto;
import com.einschpanner.catchup.global.model.BaseTimeEntity;
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

    @Enumerated(EnumType.STRING)
    @Column
    private AuthProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany
    @JoinColumn(name = "userId")
    private List<Blog> blogs;

    public String getRolekey() {
        return this.role.getKey();
    }

    public User update(String nickname, String picture, AuthProvider providerId) {
        this.nickname = nickname;
        this.urlProfile = picture;
        this.provider = providerId;

        return this;
    }

    public void plusFollowerCount(){
        this.cntFollower++;
    }

    public void minusFollowerCount(){
        this.cntFollower--;
    }

    public void plusFollowingCount(){
        this.cntFollowing++;
    }

    public void minusFollowingCount(){
        this.cntFollowing--;
      
    public User update(ProfileDto.UpdateReq updateReq) {
        this.nickname = updateReq.getNickname();
        this.urlProfile = updateReq.getUrlProfile();
        this.description = updateReq.getDescription();
        this.addrRss = updateReq.getAddrRss();
        this.addrGithub = updateReq.getAddrGithub();
        this.addrBlog = updateReq.getAddrBlog();

        return this;
    }
}
