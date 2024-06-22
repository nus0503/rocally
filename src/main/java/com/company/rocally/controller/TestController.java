package com.company.rocally.controller;

import com.company.rocally.controller.travel.dto.AvailableDateWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

}
