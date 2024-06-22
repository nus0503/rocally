package com.company.rocally.domain.travel;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
public class AvailableDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate availableDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean isReserved;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    public void changeTravel(Travel travel) {
        this.travel = travel;
        travel.getAvailableDates().add(this);
    }

    private AvailableDates(LocalDate availableDate, LocalTime startTime, LocalTime endTime, boolean isReserved) {
        this.availableDate = availableDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isReserved = isReserved;

    }

    public AvailableDates generateAvailableDates(LocalDate availableDate, LocalTime startTime, LocalTime endTime, boolean isReserved) {
        return new AvailableDates(availableDate, startTime, endTime, isReserved);
    }

    public void changeIsReservedToTrue() {
        this.isReserved = true;
    }
}
