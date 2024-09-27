package com.company.rocally.controller.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIdResponseDto {

    private boolean success;
    private String userId;

    public FindIdResponseDto(boolean success, String userId) {
        this.success = success;
        this.userId = userId;
    }
}
