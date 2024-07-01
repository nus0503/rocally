package com.company.rocally.domain.travel;

import com.company.rocally.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ReserveStatus reserveStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "available_dates_id")
    private AvailableDates availableDates;
//    private LocalDate availableDate;
//
//    private LocalTime startTime;
//
//    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Reserve(ReserveStatus reserveStatus, AvailableDates availableDates, Travel travel, User user) {
        this.reserveStatus = reserveStatus;
        this.availableDates = availableDates;
        this.travel = travel;
        this.user = user;
    }

    public static Reserve generateReserve(ReserveStatus reserveStatus, AvailableDates availableDates, Travel travel, User user) {
        return new Reserve(reserveStatus, availableDates, travel, user);
    }

    public void changeReserveStatus(ReserveStatus reserveStatus) {
        this.reserveStatus = reserveStatus;
    }
}
