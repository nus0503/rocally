package com.company.rocally.domain.travel;

import com.company.rocally.domain.BaseTimeEntity;
import com.company.rocally.domain.heart.Heart;
import com.company.rocally.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "travels")
@Entity
public class Travel extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int price; //BigDecimal으로 바꾸기

    @Column(name = "meetingPlace", nullable = false)
    private String meetingPlace;

    @Column(name = "max_people")
    private String maxPeople; // int로 바꾸기

    @Column(name = "like_count", columnDefinition = "integer default 0")
    private int likeCount;

    @Column(name = "view_count", columnDefinition = "integer default 0", nullable = false)
    private int viewCount;

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TravelImage> travelImages = new ArrayList<>();

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserve> reserves = new ArrayList<>();

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AvailableDates> availableDates = new ArrayList<>();

    @OneToMany(mappedBy = "travel")
    private List<Comment> comments = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "travel_local_id")
//    private TravelLocal travelLocal;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    public void addAvailableDates(List<AvailableDates> availableDates) {
        this.availableDates = availableDates;

    }
    public void addTravelImages(List<TravelImage> travelImages) {

        for (TravelImage travelImage : travelImages) {
            this.travelImages.add(travelImage);
            travelImage.addTravel(this);
        }
    }

    public void addUser(User user) {
        this.user = user;
        user.getTravels().add(this);
    }

    public Travel(String title, String content, int price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }

    private Travel(String title, String content, int price, String meetingPlace, String maxPeople) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.meetingPlace = meetingPlace;
        this.maxPeople = maxPeople;
    }

    public static Travel generateTravel(String title, String content, int price, String meetingPlace, String maxPeople) {
        return new Travel(title, content, price, meetingPlace, maxPeople);
    }

    public void addImage(List<TravelImage> travelImages) {
        this.travelImages.addAll(travelImages);
    }

    public void updateLikeCount(boolean bool) {
        if (bool) {
            this.likeCount += 1;
        } else {
            this.likeCount -= 1;
        }

    }
}
