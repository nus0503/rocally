package com.company.rocally.controller.travel.dto;

import com.company.rocally.domain.travel.Travel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelRegisterRequestDto {

    private String title;

    private String content;

    private int price;


}
