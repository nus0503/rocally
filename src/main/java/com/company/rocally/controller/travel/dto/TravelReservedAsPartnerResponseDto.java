package com.company.rocally.controller.travel.dto;

import com.company.rocally.domain.travel.ReserveStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelReservedAsPartnerResponseDto {

    private Long travelId;
    private String travelTitle;
    private Long reservedCount;
    private ReserveStatus reserveStatus;
    private Long userId;
    private String username;
    private String email;
    private Long reserveId;


    public TravelReservedAsPartnerResponseDto(Long travelId, String travelTitle, Long reservedCount) {
        this.travelId = travelId;
        this.travelTitle = travelTitle;
        this.reservedCount = reservedCount;
    }

    public TravelReservedAsPartnerResponseDto(Long travelId, String travelTitle, Long reservedCount, ReserveStatus reserveStatus, Long userId, String username, String email, Long reserveId) {
        this.travelId = travelId;
        this.travelTitle = travelTitle;
        this.reservedCount = reservedCount;
        this.reserveStatus = reserveStatus;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.reserveId = reserveId;
    }
}
