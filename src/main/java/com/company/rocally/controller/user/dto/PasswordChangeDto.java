package com.company.rocally.controller.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeDto {
    private String existingPassword;
    private String newPassword;
    private Long userId;
}
