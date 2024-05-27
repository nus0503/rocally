package com.company.rocally.controller.travel.dto;

import com.company.rocally.controller.user.dto.UserResponseDto;
import com.company.rocally.domain.travel.Travel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TravelDetailResponseDto {

    private String title;

    private String content;

    private int price; //BigDecimal으로 바꾸기

    private String local; // 따로 테이블

    private String country; // 따로 테이블

    private String lat; //축약 ㄴㄴ

    private String lng;

    private String maxPeople; // int로 바꾸기

    private List<TravelImageResponseDto> travelImages;

    private UserResponseDto user;

    public TravelDetailResponseDto(Travel travel) {
        this.title = travel.getTitle();
        this.content = travel.getContent();
        this.price = travel.getPrice();
        this.local = travel.getLocal();
        this.country = travel.getCountry();
        this.lat = travel.getLat();
        this.lng = travel.getLng();
        this.maxPeople = travel.getMaxPeople();
        this.travelImages = travel.getTravelImages().stream()
                .map(travelImage -> new TravelImageResponseDto(travelImage.getFilename(), travelImage.getFilepath()))
                .collect(Collectors.toList());
        this.user = new UserResponseDto(travel.getUser().getUsername());
    }
}
