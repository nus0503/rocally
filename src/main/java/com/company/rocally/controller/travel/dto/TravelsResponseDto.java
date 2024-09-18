package com.company.rocally.controller.travel.dto;

import com.company.rocally.domain.travel.AvailableDates;
import com.company.rocally.domain.travel.Travel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TravelsResponseDto {

    private Long id;
    private String title;
    private int price;
    private String maxPeople;
    private String meetingPlace;
    private int viewCount;

    private int likeCount;
    private List<TravelImageResponseDto> travelImages;



    public TravelsResponseDto(Travel travel) {
        this.id = travel.getId();
        this.title = travel.getTitle();
        this.price = travel.getPrice();
        this.maxPeople = travel.getMaxPeople();
        this.meetingPlace = travel.getMeetingPlace();
        this.viewCount = travel.getViewCount();
        this.likeCount = travel.getLikeCount();
        this.travelImages = travel.getTravelImages().stream()
                .map(travelImage -> new TravelImageResponseDto(travelImage.getFilename(), travelImage.getFilepath()))
                .collect(Collectors.toList());
    }
}
