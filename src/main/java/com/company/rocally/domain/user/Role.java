package com.company.rocally.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    PARTNER("ROLE_PARTNER", "파트너"),
    USER("ROLE_USER", "일반 이용자");

    private final String key;

    private final String title;
}

