package com.company.rocally.common.validation.email;

import com.company.rocally.controller.user.dto.UserRegisterRequestDto;
import com.company.rocally.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class CheckEmailValidator extends AbstractValidator<UserRegisterRequestDto>{

    private final UserRepository userRepository;
    @Override
    protected void doValidate(UserRegisterRequestDto target, Errors errors) {
        if (userRepository.existsByEmail(target.getEmail())) {
            errors.rejectValue("email", "multi", "이미 사용중인 이메일 입니다.");
        }
    }
}
