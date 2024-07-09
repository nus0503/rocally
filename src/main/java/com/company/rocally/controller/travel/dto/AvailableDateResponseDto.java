package com.company.rocally.controller.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AvailableDateResponseDto {

    private Long id;

    private LocalDate availableDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean isReserved;

    public AvailableDateResponseDto(LocalDate availableDate, LocalTime startTime, LocalTime endTime) {
        this.availableDate = availableDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public AvailableDateResponseDto(Long id, LocalDate availableDate, LocalTime startTime, LocalTime endTime, boolean isReserved) {
        this.id = id;
        this.availableDate = availableDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isReserved = isReserved;
    }
}
