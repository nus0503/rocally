package com.company.rocally.controller.travel;

import com.company.rocally.controller.file.dto.ImageFileDto;
import com.company.rocally.controller.travel.dto.TravelRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
@RequiredArgsConstructor
public class TravelController {

    @GetMapping("/travel-register")
    public String TravelRegister() {
        return "travel-register";
    }

    @PostMapping("/trevel-register")
    public ResponseEntity createTravel(@ModelAttribute TravelRegisterRequestDto travelRegisterRequestDto, @RequestPart("images")ImageFileDto imageFileDto) {

    }
}
