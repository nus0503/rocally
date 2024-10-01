package com.company.rocally.config.auth.dto;

import com.company.rocally.domain.user.Role;
import com.company.rocally.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String usernameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        }
        return ofGoogle(usernameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> attributesProfile = (Map<String, Object>) attributesAccount.get("profile");
        return OAuthAttributes.builder()
                .name((String) attributesAccount.get("email"))
                .email((String) attributesProfile.get("nickname"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
//                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String usernameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
//                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .username(name)
                .email(email)
                .phoneNumber(UUID.randomUUID().toString())
                .role(Role.USER)
                .build();
    }
}
