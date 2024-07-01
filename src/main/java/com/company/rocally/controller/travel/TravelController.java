package com.company.rocally.controller.travel;

import com.company.rocally.common.page.PageableRequest;
import com.company.rocally.config.auth.LoginUser;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.travel.dto.*;
import com.company.rocally.service.travel.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    @GetMapping("/travel-register")
    public String TravelRegister(Model model) {
        model.addAttribute("travelRegisterRequestDto", new TravelRegisterRequestDto());
        return "travel-register";
    }

    @PostMapping("/travel-register")
    public String createTravel(@LoginUser SessionUser user,
                               @ModelAttribute @Valid TravelRegisterRequestDto travelRegisterRequestDto,
                               @ModelAttribute AvailableDateWrapper availableDateWrapper,
                               Errors errors, Model model) throws IOException {

        if (errors.hasErrors()) {
            Map<String, String> validatorResult = errors.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            error -> String.format("valid_%s", error.getField()),
                            FieldError::getDefaultMessage,
                            (msg1, msg2) -> msg1
                    ));
            validatorResult.forEach(
                    (key, value) -> model.addAttribute(key, value)
            );
            model.addAttribute("travelRegisterRequestDto", travelRegisterRequestDto);
            return "travel-register";
        }
        travelService.createTravelWithImage(user, travelRegisterRequestDto, availableDateWrapper);
        return "index";
    }

    @PostMapping("/나중에 짓기")
    public String reserveTravel(@LoginUser SessionUser user, @ModelAttribute TravelReserveRequestDto travelReserveRequestDto) {
        return "";
    }

    @GetMapping("/travel/{id}")
    public String getTravel(@PathVariable Long id, Model model) {
        TravelDetailResponseDto travelDetailResponseDto = travelService.getTravelProgramDetail(id);
        model.addAttribute("travelDetailResponseDto", travelDetailResponseDto);
        return "a";
    }

    @GetMapping("/travel-list")
    public String getTravelList(@ModelAttribute PageableRequest request, Model model) {
        Page<TravelsResponseDto> travels = travelService.getTravels(request);
        model.addAttribute("travelList", travels);
        return "package";
    }

    @PostMapping("/reserve")
    @ResponseBody
    public ResponseEntity<String> reserve(@LoginUser SessionUser user, @RequestBody TravelReserveRequestDto dto) {
        travelService.reserveTravel(user, dto);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/check-reserve")
    public String checkReserve(@LoginUser SessionUser user, Model model) {
        model.addAttribute("reserveList", travelService.checkReservedTravel(user));
        return "b";
    }

    @GetMapping("/check-reserve-partner")
    public String checkReservePartner(@LoginUser SessionUser user, Model model) {
        model.addAttribute("partnerReserveList", travelService.checkReservedTravelAsPartner(user));
        return "c";
    }

    @PostMapping("/accept-reserve")
    @ResponseBody
    public ResponseEntity<String> acceptReserve(@LoginUser SessionUser user, @RequestParam Long reservationId) {

        travelService.acceptReservation(reservationId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/waiting-reserve")
    public String checkWaitingReservation(@LoginUser SessionUser user, Model model) {
        model.addAttribute("waitingReservationList", travelService.waitingReservation(user));
        return "waiting-reservation";
    }

    @GetMapping("/complete-reserve")
    public String checkCompleteReservation(@LoginUser SessionUser user, Model model) {
        model.addAttribute("completeReservationList", travelService.completeReservation(user));
        return "complete-reservation";
    }
}
