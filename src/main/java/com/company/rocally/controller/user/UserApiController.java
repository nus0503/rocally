package com.company.rocally.controller.user;

import com.company.rocally.common.validation.email.CheckEmailValidator;
import com.company.rocally.controller.user.dto.UserRegisterRequestDto;
import com.company.rocally.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final CheckEmailValidator checkEmailValidator;

    private final UserService userService;

    @InitBinder(value = "userRegisterRequestDto")
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkEmailValidator);
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userRegisterRequestDto", new UserRegisterRequestDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute @Valid UserRegisterRequestDto userRegisterRequestDto,
                         Errors errors,
                         Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("userRegisterRequestDto", userRegisterRequestDto);
            return "signup";
        }

        userService.save(userRegisterRequestDto);
        return "index";
    }
}
