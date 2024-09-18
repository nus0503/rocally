package com.company.rocally.controller.user;

import com.company.rocally.controller.user.dto.LoginOrSignupRequestDto;
import com.company.rocally.controller.user.dto.UserRegisterRequestDto;
import com.company.rocally.domain.partner.Partner;
import com.company.rocally.domain.partner.PartnerRepository;
import com.company.rocally.domain.user.Role;
import com.company.rocally.domain.user.User;
import com.company.rocally.domain.user.UserRepository;
import com.company.rocally.service.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private final UserService userService;

    private final PartnerRepository partnerRepository;


    @PostMapping("/login-step1")
//    @ResponseBody
    public String loginPost(@ModelAttribute LoginOrSignupRequestDto dto, Model model) {
        int num = userService.findByEmail(dto.getEmail());
        if (num == 0) {
            model.addAttribute("userRegisterRequestDto", new UserRegisterRequestDto());
            return "signup";
        } else if (num == 1) {
            model.addAttribute("email", dto.getEmail());
            return "login_sns";
        } else {
            model.addAttribute("email", dto.getEmail());
            return "loginForm";
        }
    }

    @GetMapping("/loginForm")
    public String login(HttpServletRequest request,
                        @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("email") != null) {
            String email = (String) session.getAttribute("email");
            model.addAttribute("email", email);

            session.removeAttribute("email");
        }
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "user/authentication-login";
    }

    @GetMapping("/user/update")
    public String update() {
        return "user/authentication-update";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/dashboardIndex";
    }

    @GetMapping("/find-id")
    public String findId() {
        return "user/find-user-id";
    }

    @GetMapping("/find-password")
    public String findPassword() {
        return "user/find-user-password";
    }


    @PostConstruct
    public void userInfo() {
        Partner partner1 = new Partner("1", "2", "3", "4", "5", "6");
//        Partner save = partnerRepository.save(partner1);
        User user = User.builder()
                .email("aaa@aaa.com")
                .role(Role.USER)
                .discoveryRoute("인터넷")
                .phoneNumber("010-3702-7428")
                .birthDate("1997-05-03")
                .username("김정수")
                .password(bCryptPasswordEncoder.encode("Rla7539750!"))
                .build();
//        user.addPartner(partner1);
        User user2 = User.builder()
                .email("aaaa@aaaa.com")
                .role(Role.USER)
                .discoveryRoute("인터넷")
                .phoneNumber("010-3703-7428")
                .birthDate("1997-05-03")
                .username("김정수")
                .build();
        userRepository.save(user);
        userRepository.save(user2);
    }
}
