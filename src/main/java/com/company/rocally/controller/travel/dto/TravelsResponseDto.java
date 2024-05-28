package com.company.rocally.controller.travel.dto;

import com.company.rocally.domain.travel.Travel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TravelsResponseDto {

    private String title;

    private List<TravelImageResponseDto> travelImages;


    public TravelsResponseDto(Travel travel) {
        this.title = travel.getTitle();
        this.travelImages = travel.getTravelImages().stream()
                .map(travelImage -> new TravelImageResponseDto(travelImage.getFilename(), travelImage.getFilepath()))
                .collect(Collectors.toList());
    }
}
