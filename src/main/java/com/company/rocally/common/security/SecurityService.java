package com.company.rocally.common.security;

import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.user.dto.UserUpdateRequestDto;
import com.company.rocally.domain.user.Role;

public interface SecurityService {

    void updateAuthority();

    void updateAuthentication(SessionUser user, UserUpdateRequestDto dto);
}
