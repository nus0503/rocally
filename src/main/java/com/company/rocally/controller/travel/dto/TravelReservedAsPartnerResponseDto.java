package com.company.rocally.controller.travel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelReservedAsPartnerResponseDto {

    private Long travelId;
    private String travelTitle;
    private Long reservedCount;

    public TravelReservedAsPartnerResponseDto(Long travelId, String travelTitle, Long reservedCount) {
        this.travelId = travelId;
        this.travelTitle = travelTitle;
        this.reservedCount = reservedCount;
    }
}
