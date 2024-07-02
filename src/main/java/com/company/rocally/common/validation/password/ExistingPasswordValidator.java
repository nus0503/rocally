package com.company.rocally.common.validation.password;

import com.company.rocally.controller.user.dto.PasswordChangeDto;
import com.company.rocally.domain.user.User;
import com.company.rocally.domain.user.UserRepository;
import com.company.rocally.service.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
@RequiredArgsConstructor

@Component
public class ExistingPasswordValidator implements Validator {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordChangeDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordChangeDto dto = (PasswordChangeDto) target;

//        if (dto.getUserId() == null || dto.getUserId().) {
//            errors.rejectValue("userId", "userId.empty", "User ID is required");
//            return;
//        }

        if (dto.getExistingPassword() == null || dto.getExistingPassword().isEmpty()) {
            errors.rejectValue("existingPassword","existingPassword", "기존 비밀번호를 입력해주세요.");
            return;
        }

        User user = userRepository.findById(dto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다."));
        if (user == null || !bCryptPasswordEncoder.matches(dto.getExistingPassword(), user.getPassword())) {
            errors.rejectValue("matchPassword", "matchPassword", "기존의 비밀번호가 일치하지 않습니다.");
        }
    }
}
