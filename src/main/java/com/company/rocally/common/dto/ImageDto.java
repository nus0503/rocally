package com.company.rocally.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDto {

    private String filename;

    private String filepath;

    public ImageDto(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
    }
}
