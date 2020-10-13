package com.einschpanner.catchup.domain.user.dto;

import com.einschpanner.catchup.domain.blog.domain.Blog;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.*;

import java.util.List;

public class ProfileDto {

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class UpdateReq {
        private String nickname;
        private String urlProfile;
        private String description;
        private String addrRss;
        private String addrGithub;
        private String addrBlog;
    }

    @Getter
    public static class UpdateRes {
        private String nickname;
        private String urlProfile;
        private String description;
        private String addrRss;
        private String addrGithub;
        private String addrBlog;
        private String email;
        private int cntFollowing;
        private int cntFollower;

        public UpdateRes(User user) {
            this.nickname = user.getNickname();
            this.urlProfile = user.getUrlProfile();
            this.description = user.getDescription();
            this.addrRss = user.getAddrRss();
            this.addrGithub = user.getAddrGithub();
            this.addrBlog = user.getAddrBlog();
            this.email = user.getEmail();
            this.cntFollowing = user.getCntFollowing();
            this.cntFollower = user.getCntFollower();
        }
    }

    @Getter
    public static class Res {
        private String nickname;
        private String urlProfile;
        private String description;
        private String addrRss;
        private String addrGithub;
        private String addrBlog;
        private String email;
        private int cntFollowing;
        private int cntFollower;
        private List<Blog> blogs;


        public Res(User user) {
            this.nickname = user.getNickname();
            this.urlProfile = user.getUrlProfile();
            this.description = user.getDescription();
            this.addrRss = user.getAddrRss();
            this.addrGithub = user.getAddrGithub();
            this.addrBlog = user.getAddrBlog();
            this.email = user.getEmail();
            this.cntFollowing = user.getCntFollowing();
            this.cntFollower = user.getCntFollower();
            this.blogs = user.getBlogs();
        }
    }
}
