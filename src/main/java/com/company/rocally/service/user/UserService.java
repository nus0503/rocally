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
                .account(userRegisterRequestDto.getAccount())
                .koreaLanguageLevel(userRegisterRequestDto.getKoreaLanguageLevel())
                .bank(userRegisterRequestDto.getBank())
                .discoveryRoute(userRegisterRequestDto.getDiscoveryRoute())
                .role(Role.USER)
                .build()).getId();
    }
}
