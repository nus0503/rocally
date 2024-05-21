package com.company.rocally.service.travel;

import com.company.rocally.common.dto.ImageDto;
import com.company.rocally.common.file.image.ImageStore;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.file.dto.ImageFileDto;
import com.company.rocally.controller.travel.dto.TravelImageRequestDto;
import com.company.rocally.controller.travel.dto.TravelRegisterRequestDto;
import com.company.rocally.domain.travel.Travel;
import com.company.rocally.domain.travel.TravelImage;
import com.company.rocally.domain.travel.TravelRepository;
import com.company.rocally.domain.user.User;
import com.company.rocally.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TravelService {

    private final TravelRepository travelRepository;

    private final UserRepository userRepository;
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

    @Transactional
    public void createTravelWithImage(SessionUser user, TravelRegisterRequestDto travelRegisterRequestDto) throws IOException {
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 없습니다.")
        );
        List<ImageDto> imageDtos = ImageStore.getFileDtoFromMultipartFile(travelRegisterRequestDto.getImages());
        travelRegisterRequestDto.setImageDto(imageDtos);
        travelRepository.save(travelRegisterRequestDto.toTravelEntity(user1));

    }
}
