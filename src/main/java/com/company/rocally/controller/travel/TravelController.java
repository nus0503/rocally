package com.company.rocally.controller.travel;

import com.company.rocally.controller.file.dto.ImageFileDto;
import com.company.rocally.controller.travel.dto.TravelImageRequestDto;
import com.company.rocally.controller.travel.dto.TravelRegisterRequestDto;
import com.company.rocally.service.travel.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    @GetMapping("/travel-register")
    public String TravelRegister() {
        return "travel-register";
    }

    @PostMapping("/travel-register")
    public String createTravel(@ModelAttribute TravelRegisterRequestDto travelRegisterRequestDto) throws IOException {
        travelService.createTravelWithImage(travelRegisterRequestDto);
        return "index";
    }
}
