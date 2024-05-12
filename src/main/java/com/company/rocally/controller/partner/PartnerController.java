package com.company.rocally.controller.partner;

import com.company.rocally.config.auth.LoginUser;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.partner.dto.PartnerRegistRequestDto;
import com.company.rocally.service.partner.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping("/partner-regist")
    public String partnerRegist() {
        return "partner-regist";
    }

    @PostMapping("/partner-regist")
    public String partnerRegister(@LoginUser SessionUser user, @ModelAttribute PartnerRegistRequestDto partnerRegistRequestDto) {
        partnerService.regist(user, partnerRegistRequestDto);
        return "index";
    }
}
