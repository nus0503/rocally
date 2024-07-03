package com.company.rocally.controller;

import com.company.rocally.config.auth.LoginUser;
import com.company.rocally.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String hello(@LoginUser SessionUser user, Model model, HttpServletRequest request) {
        if (user != null) {
            String id = httpSession.getId();
            HttpSession session = request.getSession();
            model.addAttribute("user", user);
        }
        return "index";
    }

}
