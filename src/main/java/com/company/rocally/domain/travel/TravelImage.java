package com.company.rocally.domain.travel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "travel_image")
@Entity
public class TravelImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_image_id")
    private Long id;
    @Column(name = "filename")
    private String filename;
    @Column(name = "filepath")
    private String filepath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    public TravelImage(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
    }

    public TravelImage(String filename, String filepath, Travel travel) {
        this.filename = filename;
        this.filepath = filepath;
        this.travel = travel;
    }
}
