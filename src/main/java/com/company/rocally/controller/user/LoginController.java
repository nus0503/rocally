package com.company.rocally.controller.user;

import com.company.rocally.domain.user.Role;
import com.company.rocally.domain.user.User;
import com.company.rocally.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    @GetMapping("/loginForm")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/loginForm";
    }

    @PostConstruct
    public void userInfo() {
        User user = User.builder()
                .email("aaa@aaa.com")
                .role(Role.USER)
                .bank("신한")
                .discoveryRoute("인터넷")
                .koreaLanguageLevel("1")
                .account("1111")
                .phoneNumber("010-3702-7428")
                .birthDate("1997-05-03")
                .username("김정수")
                .password(bCryptPasswordEncoder.encode("Rla7539750!"))
                .build();

        userRepository.save(user);
    }
}
