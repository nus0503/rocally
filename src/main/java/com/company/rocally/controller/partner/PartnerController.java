package com.company.rocally.controller.partner;

import com.company.rocally.config.auth.LoginUser;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.partner.dto.PartnerRegistRequestDto;
import com.company.rocally.service.partner.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping("/partner-regist")
    public String partnerRegist() {
        return "partner-regist";
    }

    @PostMapping("/partner-regist")
    public String partnerRegister(@LoginUser SessionUser user, @ModelAttribute PartnerRegistRequestDto partnerRegistRequestDto, RedirectAttributes redirectAttributes, Model model) {
        try {
            partnerService.regist(user, partnerRegistRequestDto);
            redirectAttributes.addFlashAttribute("message", "파트너 등록이 완료되었습니다.");
            return "redirect:/";  // 성공 시 루트 페이지로 리다이렉트
        } catch (Exception e) {
            model.addAttribute("error", "등록 중 오류가 발생했습니다.");
            model.addAttribute("partnerRegistRequestDto", partnerRegistRequestDto);  // 입력 데이터 유지
            return "partner-regist";  // 실패 시 같은 페이지 렌더링
        }
    }
}
