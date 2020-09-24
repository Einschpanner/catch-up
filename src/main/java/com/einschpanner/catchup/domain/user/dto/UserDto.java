package com.einschpanner.catchup.domain.user.dto;

import com.einschpanner.catchup.domain.user.domain.User;
import lombok.Getter;

public class UserDto {

    @Getter
    public static class Response {
        private Long userId;
        private String nickname;
        private String email;
        private String urlProfile;
        private int cntFollowing;
        private int cntFollower;

        public Response(User user){
            this.userId = user.getUserId();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.urlProfile = user.getUrlProfile();
            this.cntFollowing = user.getCntFollowing();
            this.cntFollower = user.getCntFollower();
        }
    }
}
