package com.company.rocally.controller.partner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PartnerController {

    @GetMapping("/partner-regist")
    public String partnerRegist() {
        return "partner-regist";
    }
}
