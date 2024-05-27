package com.company.rocally.controller.travel.dto;

import com.company.rocally.common.dto.ImageDto;
import com.company.rocally.common.validation.file.FileUploadValid;
import com.company.rocally.common.validation.file.UploadAllowFileDefine;
import com.company.rocally.domain.travel.Travel;
import com.company.rocally.domain.travel.TravelImage;
import com.company.rocally.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TravelRegisterRequestDto {

    private String title;

    private String content;

    private int price;

    private String local;

    private String country;

    private String lat;

    private String lng;

    private String maxPeople;

    @FileUploadValid(allowFileDefine = {UploadAllowFileDefine.JPG}, message = "유효한 img파일만 업로드 가능합니다.")
    private List<MultipartFile> images;

    private List<ImageDto> imageDto;

    public Travel toTravelEntity(User user) {
        List<TravelImage> travelImages = toTravelImageEntity();
//        Travel travel1 = new Travel("1", "2", 3);
        Travel travel = Travel.generateTravel(title, content, price, local, country, lat, lng, maxPeople);
        travel.addTravelImages(travelImages);
        travel.addUser(user);
        return travel;
    }

    public List<TravelImage> toTravelImageEntity() {
        return imageDto.stream()
                .map(imageDto1 -> new TravelImage(imageDto1.getFilename(), imageDto1.getFilepath()))
                .collect(Collectors.toList());
    }


}
