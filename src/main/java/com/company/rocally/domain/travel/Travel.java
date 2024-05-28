package com.company.rocally.domain.travel;

import com.company.rocally.domain.BaseTimeEntity;
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

    @Column(name = "local", nullable = false)
    private String local; // 따로 테이블

    @Column(name = "country", nullable = false)
    private String country; // 따로 테이블

    @Column(name = "lat")
    private String lat; //축약 ㄴㄴ

    @Column(name = "lng")
    private String lng;

    @Column(name = "max_people")
    private String maxPeople; // int로 바꾸기

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TravelImage> travelImages = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "travel_local_id")
//    private TravelLocal travelLocal;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

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

    private Travel(String title, String content, int price, String local, String country, String lat, String lng, String maxPeople) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.local = local;
        this.country = country;
        this.lat = lat;
        this.lng = lng;
        this.maxPeople = maxPeople;
    }

    public static Travel generateTravel(String title, String content, int price, String local, String country, String lat, String lng, String maxPeople) {
        return new Travel(title, content, price, local, country, lat, lng, maxPeople);
    }

    public void addImage(List<TravelImage> travelImages) {
        this.travelImages.addAll(travelImages);
    }
}
