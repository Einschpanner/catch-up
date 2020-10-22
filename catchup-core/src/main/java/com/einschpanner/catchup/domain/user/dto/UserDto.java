package com.einschpanner.catchup.domain.user.dto;

import com.einschpanner.catchup.domain.user.domain.User;
import lombok.Getter;

public class UserDto {

    @Getter
    public static class Res {
        private String nickname;
        private String email;
        private String urlProfile;

        public Res(User user){
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.urlProfile = user.getUrlProfile();
        }
    }
}
