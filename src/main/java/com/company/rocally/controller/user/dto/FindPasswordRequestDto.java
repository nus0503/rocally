package com.company.rocally.controller.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordRequestDto {

    private String email;
    private String name;
    private String phoneNumber;
}
