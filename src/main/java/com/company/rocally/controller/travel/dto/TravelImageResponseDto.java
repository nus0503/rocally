package com.company.rocally.controller.travel.dto;

import com.company.rocally.domain.travel.TravelImage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class TravelImageResponseDto {

    private String filename;
    private String filepath;

    public TravelImageResponseDto(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
    }
}
