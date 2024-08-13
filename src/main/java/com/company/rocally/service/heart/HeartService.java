package com.company.rocally.service.heart;

import com.company.rocally.common.codes.error.UserErrorCode;
import com.company.rocally.common.customException.RestApiException;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.heart.dto.HeartRequestDto;
import com.company.rocally.domain.heart.Heart;
import com.company.rocally.domain.heart.HeartRepository;
import com.company.rocally.domain.travel.Travel;
import com.company.rocally.domain.travel.TravelRepository;
import com.company.rocally.domain.user.User;
import com.company.rocally.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;

    @Transactional
    public void insert(HeartRequestDto heartRequestDto, SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new RestApiException(UserErrorCode.INACTIVE_USER));

        Travel travel = travelRepository.findById(heartRequestDto.getTravelId()).orElseThrow(
                () -> new IllegalArgumentException("해당 프로그램이 없습니다."));

        heartRepository.save(Heart.generateHeart(user, travel));
    }

    @Transactional
    public void delete(HeartRequestDto heartRequestDto, SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new RestApiException(UserErrorCode.INACTIVE_USER));

        Travel travel = travelRepository.findById(heartRequestDto.getTravelId()).orElseThrow(
                () -> new IllegalArgumentException("해당 프로그램이 없습니다."));

        Heart heart = heartRepository.findByUserAndTravel(user, travel).orElseThrow(
                () -> new IllegalArgumentException("에러에러"));

        heartRepository.delete(heart);
    }
}
