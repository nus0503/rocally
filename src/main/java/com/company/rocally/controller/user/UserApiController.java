package com.company.rocally.controller.user;

import com.company.rocally.common.validation.email.CheckEmailValidator;
import com.company.rocally.common.validation.password.ExistingPasswordValidator;
import com.company.rocally.controller.user.dto.PasswordChangeDto;
import com.company.rocally.controller.user.dto.UserRegisterRequestDto;
import com.company.rocally.controller.user.dto.UserUpdateRequestDto;
import com.company.rocally.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final CheckEmailValidator checkEmailValidator;

    private final ExistingPasswordValidator existingPasswordValidator;

    private final UserService userService;

    @InitBinder(value = "userRegisterRequestDto")
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkEmailValidator);
    }

    @InitBinder(value = "passwordChangeDto")
    public void passwordValidatorBinder(WebDataBinder binder) {
        binder.addValidators(existingPasswordValidator);
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

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboardIndex";
    }

    @PostMapping("matchPassword")
    public String matchPassword(PasswordChangeDto passwordChangeDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("passwordChangeDto", passwordChangeDto);
            return "checkExistingPassword";
        }
        return "updateUser";
    }

    @PutMapping("/user")
    @ResponseBody
    public ResponseEntity<String> modify(@RequestBody UserUpdateRequestDto dto) {
        return ResponseEntity.ok().build();
    }

}
