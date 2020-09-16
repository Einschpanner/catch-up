package com.einschpanner.catchup.global.security.dto;

import com.einschpanner.catchup.domain.user.domain.AuthProvider;
import com.einschpanner.catchup.domain.user.domain.Role;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuth2Dto {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private AuthProvider providerId;

    @Builder
    public OAuth2Dto(Map<String, Object> attributes, String nameAttributeKey, String nickname, String email, String picture, AuthProvider providerId) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = nickname;
        this.email = email;
        this.picture = picture;
        this.providerId = providerId;
    }

    public static OAuth2Dto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals(AuthProvider.google.toString()))
            return ofGoogle(AuthProvider.google, userNameAttributeName, attributes);
        return OAuth2Dto.builder().build();
    }

    private static OAuth2Dto ofGoogle(AuthProvider providerId, String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Dto.builder()
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .providerId(providerId)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .nickname(name)
                .email(email)
                .urlProfile(picture)
                .role(Role.USER)
                .provider(providerId)
                .build();
    }
}
