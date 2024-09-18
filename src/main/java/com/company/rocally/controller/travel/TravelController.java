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

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/travel/{id}")
    public String getTravel(@PathVariable Long id, @LoginUser SessionUser user, HttpServletRequest request, HttpServletResponse response, Model model) {
        TravelDetailResponseDto travelDetailResponseDto = travelService.getTravelProgramDetail(id, user);
        model.addAttribute("travelDetailResponseDto", travelDetailResponseDto);

        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                travelService.updateView(id);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            travelService.updateView(id);
            Cookie newCookie = new Cookie("postView", "[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
        return "product/product-detail";
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
        return "dashboard/reservation-detail";
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
        return "dashboard/waiting-reservation";
    }

    @GetMapping("/complete-reserve")
    public String checkCompleteReservation(@LoginUser SessionUser user, Model model) {
        model.addAttribute("completeReservationList", travelService.completeReservation(user));
        return "dashboard/complete-reservation";
    }

    @PostConstruct
    public void travelPostInto() {

    }

}
