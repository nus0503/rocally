package com.company.rocally.controller.travel.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class TravelImageRequestDto {

    private List<MultipartFile> multipartFiles;

}
