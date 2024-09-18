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

        // 좋아요가 이미 존재하는지 체크
        if (heartRepository.existsByUserIdAndTravelId(user.getId(), travel.getId())) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }
        heartRepository.save(Heart.generateHeart(user, travel));
        travel.updateLikeCount(true);
//        travelRepository.increaseLikeCount(travel.getId());
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
        travel.updateLikeCount(false);
//        travelRepository.decreaseLikeCount(travel.getId());
    }
}
