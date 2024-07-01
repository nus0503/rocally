package com.company.rocally.controller.travel.dto;

import com.company.rocally.domain.travel.ReserveStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelWaitingReservationResponseDto {

    private Long travelId;
    private String travelTitle;
    private ReserveStatus reserveStatus;
    private Long userId;
    private String username;
    private String userEmail;
    private Long reserveId;

    public TravelWaitingReservationResponseDto(Long travelId, String travelTitle, ReserveStatus reserveStatus, Long userId, String username, String userEmail, Long reserveId) {
        this.travelId = travelId;
        this.travelTitle = travelTitle;
        this.reserveStatus = reserveStatus;
        this.userId = userId;
        this.username = username;
        this.userEmail = userEmail;
        this.reserveId = reserveId;
    }
}
