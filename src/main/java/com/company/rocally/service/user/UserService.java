package com.company.rocally.service.user;

import com.company.rocally.controller.user.dto.UserRegisterRequestDto;
import com.company.rocally.domain.user.Role;
import com.company.rocally.domain.user.User;
import com.company.rocally.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public String findByEmail(String email) {
        String getEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다.")).getEmail();
        return getEmail;
    }
}
