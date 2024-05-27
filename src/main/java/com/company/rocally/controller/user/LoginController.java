package com.company.rocally.controller.user;

import com.company.rocally.controller.user.dto.LoginOrSignupRequestDto;
import com.company.rocally.domain.partner.Partner;
import com.company.rocally.domain.partner.PartnerRepository;
import com.company.rocally.domain.user.Role;
import com.company.rocally.domain.user.User;
import com.company.rocally.domain.user.UserRepository;
import com.company.rocally.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private final UserService userService;

    private final PartnerRepository partnerRepository;


    @GetMapping("/login-process1")
    public String loginOrSignup() {
        return "login_or_signup";
    }


    @PostMapping("/login-step1")
    @ResponseBody
    public ResponseEntity loginPost(@RequestBody LoginOrSignupRequestDto dto) {
        return ResponseEntity.ok(userService.findByEmail(dto.getEmail()));
    }


    @GetMapping("/login-process2")
    public String loginProcess2() {
        return "login-process2";
    }

    @GetMapping("/signup2")
    public String signup2() {
        return "signup2";
    }

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

        userRepository.save(user);
    }
}
