package com.company.rocally.controller.file.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ImageFileDto {

    private List<MultipartFile> imgFiles;
}
