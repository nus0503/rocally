package com.company.rocally.controller;

import com.company.rocally.common.codes.error.UserErrorCode;
import com.company.rocally.common.customException.RestApiException;
import com.company.rocally.controller.travel.dto.AvailableDateWrapper;
import com.company.rocally.domain.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {

    @GetMapping("/test100")
    public String test() {
        return "a";
    }

    @PostMapping("/test100")
    public String test11(@ModelAttribute AvailableDateWrapper availableDateWrapper) {
        return "a";
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser() {
        throw new RestApiException(UserErrorCode.INACTIVE_USER);
    }

}
