package com.company.rocally.service.user;

import com.company.rocally.common.codes.error.UserErrorCode;
import com.company.rocally.common.customException.RestApiException;
import com.company.rocally.common.password.PasswordUtil;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.user.dto.FindIdRequestDto;
import com.company.rocally.controller.user.dto.FindPasswordRequestDto;
import com.company.rocally.controller.user.dto.UserRegisterRequestDto;
import com.company.rocally.controller.user.dto.UserUpdateRequestDto;
import com.company.rocally.domain.user.Role;
import com.company.rocally.domain.user.User;
import com.company.rocally.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public Long save(UserRegisterRequestDto userRegisterRequestDto) {
        return userRepository.save(User.builder()
                .username(userRegisterRequestDto.getUsername())
                .birthDate(userRegisterRequestDto.getBirthDateString())
                .email(userRegisterRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userRegisterRequestDto.getPassword()))
                .phoneNumber(userRegisterRequestDto.getPhoneNumber())
                .discoveryRoute(userRegisterRequestDto.getDiscoveryRoute())
                .role(Role.USER)
                .build()).getId();
    }

    public int findByEmail(String email) {
        User user = userRepository.findByEmailAsObject(email);

        if (user == null) {
            return 0;
        } else {
            if (user.getPassword() == null) {
                return 1;
            }
        }
        return 2;
    }

    @Transactional
    public void updateUser(SessionUser user, UserUpdateRequestDto dto) {
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다."));

        user1.setNewPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
    }

    public String findIdAsNameAndPhone(FindIdRequestDto dto) {
        User user = userRepository.findIdAsNameAndPhone(dto.getName(), dto.getPhoneNumber()).orElseThrow(
                () -> new RestApiException(UserErrorCode.NOT_FOUND_INFORMATION));

        return user.getEmail();
    }

    @Transactional
    public String findPassword(FindPasswordRequestDto dto) {
        String tempPassword = PasswordUtil.tempPasswordGenerate();
        User user = userRepository.findByEmailAndNameAndPhoneNumber(dto.getEmail(), dto.getName(), dto.getPhoneNumber()).orElseThrow(
                () -> new RestApiException(UserErrorCode.NOT_FOUND_INFORMATION));

        String encodedPassword = bCryptPasswordEncoder.encode(tempPassword);
        user.setNewPassword(encodedPassword);
        return tempPassword;

    }
}
