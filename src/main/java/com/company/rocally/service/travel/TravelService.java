package com.company.rocally.service.travel;

import com.company.rocally.common.dto.ImageDto;
import com.company.rocally.common.file.image.ImageStore;
import com.company.rocally.controller.file.dto.ImageFileDto;
import com.company.rocally.controller.travel.dto.TravelImageRequestDto;
import com.company.rocally.controller.travel.dto.TravelRegisterRequestDto;
import com.company.rocally.domain.travel.Travel;
import com.company.rocally.domain.travel.TravelImage;
import com.company.rocally.domain.travel.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;

    private final ImageStore imageStore;

    public void createTravel(TravelRegisterRequestDto dto, ImageFileDto imageFileDto) throws IOException {
        List<String> fileNames = new ArrayList<>();
        List<String> filePaths = new ArrayList<>();

        String imagePath = System.getProperty("user.dir") + "/src/main/resources/static/images/";

        Travel travel = new Travel(dto.getTitle(), dto.getContent(), dto.getPrice());
        for (MultipartFile file : imageFileDto.getImgFiles()) {

            String uuid = UUID.randomUUID().toString();
            String filename = uuid + "_" + file.getOriginalFilename();

            File dest = new File(imagePath, filename);
            file.transferTo(dest);


            TravelImage travelImage = new TravelImage(filename, "/images/" + filename, travel);

            fileNames.add(filename);
            filePaths.add("/images/" + filename);

            travel.getTravelImages().add(travelImage);
        }

        travelRepository.save(travel);
    }

    public void createTravelWithImage(TravelRegisterRequestDto travelRegisterRequestDto) throws IOException {
        List<ImageDto> imageDtos = ImageStore.getFileDtoFromMultipartFile(travelRegisterRequestDto.getImages());
        travelRegisterRequestDto.setImageDto(imageDtos);
        travelRepository.save(travelRegisterRequestDto.toTravelEntity());

    }
}
