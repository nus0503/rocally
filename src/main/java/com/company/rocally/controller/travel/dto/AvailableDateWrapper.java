package com.company.rocally.controller.travel.dto;

import com.company.rocally.domain.travel.AvailableDates;
import com.company.rocally.domain.travel.Travel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class AvailableDateWrapper {

    private List<AvailableDate> availableDates;

    public List<AvailableDates> toAvailableDatesList(Travel travel) {
        List<AvailableDates> availableDatesList = new ArrayList<>();
        for (AvailableDate availableDate : availableDates) {
            for (AvailableTime availableTime : availableDate.getAvailableTimes()) {
                AvailableDates availableDates = new AvailableDates();
                AvailableDates generatedAvailableDates = availableDates.generateAvailableDates(availableDate.getDate(), availableTime.getStartTime(), availableTime.getEndTime(), false);
                generatedAvailableDates.changeTravel(travel);
                availableDatesList.add(generatedAvailableDates);
            }
        }
        return availableDatesList;
    }

    @Getter
    @Setter
    public static class AvailableDate {
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate date;

        private List<AvailableTime> availableTimes;
    }

    @Getter
    @Setter
    public static class AvailableTime {
        @DateTimeFormat(pattern = "HH:mm")
        private LocalTime startTime;
        @DateTimeFormat(pattern = "HH:mm")
        private LocalTime endTime;
    }
}
