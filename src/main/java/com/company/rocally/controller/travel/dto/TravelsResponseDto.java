package com.company.rocally.controller.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TravelsResponseDto {

    private String title;

    private List<TravelImageResponseDto> travelImages;

}
