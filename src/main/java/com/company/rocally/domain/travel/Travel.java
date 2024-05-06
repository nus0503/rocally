package com.company.rocally.domain.travel;

import com.company.rocally.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "travels")
@Entity
public class Travel extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int price;

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL)
    private List<TravelImage> travelImages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_local_id")
    private TravelLocal travelLocal;

    public Travel(String title, String content, int price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }
}
