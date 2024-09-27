package com.company.rocally.controller.user;

import com.company.rocally.common.codes.error.UserErrorCode;
import com.company.rocally.common.security.SecurityService;
import com.company.rocally.common.validation.email.CheckEmailValidator;
import com.company.rocally.common.validation.password.ExistingPasswordValidator;
import com.company.rocally.config.auth.LoginUser;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.user.dto.*;
import com.company.rocally.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final CheckEmailValidator checkEmailValidator;

    private final ExistingPasswordValidator existingPasswordValidator;

    private final UserService userService;

    private final SecurityService securityService;

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
        return "user/authentication-register";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute @Valid UserRegisterRequestDto userRegisterRequestDto,
                         Errors errors,
                         Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("userRegisterRequestDto", userRegisterRequestDto);
            return "user/authentication-register";
        }

        userService.save(userRegisterRequestDto);
        return "index";
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
    public ResponseEntity<String> modify(@LoginUser SessionUser user, @RequestBody UserUpdateRequestDto dto) {
        userService.updateUser(user, dto);
        securityService.updateAuthentication(user, dto);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/findId")
    @ResponseBody
    public ResponseEntity<?> findId(@RequestBody FindIdRequestDto dto) {
        return new ResponseEntity<>(userService.findIdAsNameAndPhone(dto), HttpStatus.OK);
    }

    @PostMapping("/findPassword")
    @ResponseBody
    public ResponseEntity<Map<String, String>> findPassword(@RequestBody FindPasswordRequestDto dto) {
        String tempPassword = userService.findPassword(dto);
        HashMap<String, String> response = new HashMap<>();
        response.put("tempPassword", tempPassword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
