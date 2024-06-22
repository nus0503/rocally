package com.company.rocally.controller.travel.dto;

import com.company.rocally.domain.travel.ReserveStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TravelReserveResponseDto {

    private Long id;
    private ReserveStatus reserveStatus;
    private LocalDate availableDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private Long travelId;

    private String travelTitle;

    public TravelReserveResponseDto(Long id, ReserveStatus reserveStatus, LocalDate availableDate, LocalTime startTime, LocalTime endTime, Long travelId, String travelTitle) {
        this.id = id;
        this.reserveStatus = reserveStatus;
        this.availableDate = availableDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.travelId = travelId;
        this.travelTitle = travelTitle;
    }
}
