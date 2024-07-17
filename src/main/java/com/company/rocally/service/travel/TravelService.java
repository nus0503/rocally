package com.company.rocally.service.travel;

import com.company.rocally.common.dto.ImageDto;
import com.company.rocally.common.file.image.ImageStore;
import com.company.rocally.common.page.PageableRequest;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.file.dto.ImageFileDto;
import com.company.rocally.controller.travel.dto.*;
import com.company.rocally.domain.travel.*;
import com.company.rocally.domain.user.User;
import com.company.rocally.domain.user.UserRepository;
import com.company.rocally.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TravelService {

    private final TravelRepository travelRepository;

    private final UserRepository userRepository;

    private final AvailableDatesRepository availableDatesRepository;

    private final ReserveRepository reserveRepository;
    private final ImageStore imageStore;

    private final CommentService commentService;

    public void createTravel(TravelRegisterRequestDto dto, ImageFileDto imageFileDto) throws IOException {
        List<String> fileNames = new ArrayList<>();
        List<String> filePaths = new ArrayList<>();

        String imagePath = System.getProperty("user.dir") + "/src/main/resources/static/images/";

        Travel travel = new Travel(dto.getTitle(), dto.getContent(), dto.getPrice());
        for (MultipartFile file : imageFileDto.getImgFiles()) {

            String uuid = UUID.randomUUID().toString();
            String filename = uuid + "_" + file.getOriginalFilename();

            File dest = new File(imagePath, filename);
            file.transferTo(dest);


            TravelImage travelImage = new TravelImage(filename, "/images/" + filename, travel);

            fileNames.add(filename);
            filePaths.add("/images/" + filename);

            travel.getTravelImages().add(travelImage);
        }

        travelRepository.save(travel);
    }

    @Transactional
    public void createTravelWithImage(SessionUser user,
                                      TravelRegisterRequestDto travelRegisterRequestDto,
                                      AvailableDateWrapper availableDateWrapper) throws IOException {
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 없습니다.")
        );
        List<ImageDto> imageDtos = ImageStore.getFileDtoFromMultipartFile(travelRegisterRequestDto.getImages());
        travelRegisterRequestDto.setImageDto(imageDtos);
//        travelRegisterRequestDto.setAvailableDateWrapper(availableDateWrapper);
        Travel travel = travelRegisterRequestDto.toTravelEntity(user1);
        List<AvailableDates> availableDates = availableDateWrapper.toAvailableDatesList(travel);
        travel.addAvailableDates(availableDates);
        travelRepository.save(travel);

    }

    public TravelDetailResponseDto getTravelProgramDetail(Long num) {
        Travel travel = travelRepository.findById(num).orElseThrow(
                () -> new IllegalArgumentException("없다.")
        );
//        Travel testTravel = travel;
        List<CommentResponseDto> commentsWithReplies = commentService.getCommentsWithReplies(num);
        TravelDetailResponseDto travelDetailResponseDto = new TravelDetailResponseDto(travel, commentsWithReplies);
        return travelDetailResponseDto;
    }

    public Page<TravelsResponseDto> getTravels(PageableRequest request) {
        Page<Travel> page = travelRepository.findAll(request.toPageable());
        return page.map(TravelsResponseDto::new);

    }

    @Transactional
    public void reserveTravel(SessionUser user, TravelReserveRequestDto travelReserveRequestDto) {
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 없습니다."));

        Travel travel = travelRepository.findById(travelReserveRequestDto.getTravelId()).orElseThrow(
                () -> new IllegalArgumentException("해당 체험이 없습니다."));

        AvailableDates availableDate = availableDatesRepository.findById(travelReserveRequestDto.getAvailableDateId()).orElseThrow(
                () -> new IllegalArgumentException("선택하신 예약날짜가 없습니다."));

        availableDate.isReserved(); // 보안성 추가 (나중에)

        availableDate.changeIsReservedToTrue();
        availableDatesRepository.save(availableDate);
        Reserve reserve = Reserve.generateReserve(ReserveStatus.WAIT, availableDate, travel, user1);
        reserveRepository.save(reserve);
    }

    public List<TravelReserveResponseDto> checkReservedTravel(SessionUser user) { //반환타입 변경해야 함
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다."));
        return user1.getReserves().stream()
                .map(reserve -> new TravelReserveResponseDto(
                        reserve.getId(), reserve.getReserveStatus(), reserve.getAvailableDates().getAvailableDate(),
                        reserve.getAvailableDates().getStartTime(), reserve.getAvailableDates().getEndTime(),
                        reserve.getTravel().getId(), reserve.getTravel().getTitle()
                )).collect(Collectors.toList());
    }

    public  List<TravelReservedAsPartnerResponseDto> checkReservedTravelAsPartner(SessionUser user) {
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다."));

//        return user1.getTravels().stream()
//                .map(travel -> {
//                    Long reservedCount = travel.getReserves().stream().count();
//
//                    return new TravelReservedAsPartnerResponseDto(travel.getId(), travel.getTitle(), reservedCount);
//                }).collect(Collectors.toList());
        List<TravelReservedAsPartnerResponseDto> collect = user1.getTravels().stream()
                .flatMap(travel -> travel.getReserves().stream()
                        .map(reserve -> {
                            User reservedUser = reserve.getUser();
                            Long reservedCount = travel.getReserves().stream().count();
                            return new TravelReservedAsPartnerResponseDto(
                                    travel.getId(),
                                    travel.getTitle(),
                                    reservedCount,
                                    reserve.getReserveStatus(),
                                    reservedUser.getId(),
                                    reservedUser.getUsername(),
                                    reservedUser.getEmail(),
                                    reserve.getId()
                            );
                        })
                ).collect(Collectors.toList());
        return collect;
    }

    @Transactional
    public void acceptReservation(Long reserveId) {
        Reserve reserve = reserveRepository.findById(reserveId).orElseThrow(
                () -> new IllegalArgumentException("예약정보가 없습니다."));

        reserve.changeReserveStatus(ReserveStatus.COMPLETE);
    }

    public List<TravelWaitingReservationResponseDto> waitingReservation(SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다."));

        return user.getTravels().stream()
                .flatMap(travel -> travel.getReserves().stream()
                        .filter(reserve -> ReserveStatus.WAIT.equals(reserve.getReserveStatus()))
                        .map(reserve -> {
                            User reservedUser = reserve.getUser();
                            return new TravelWaitingReservationResponseDto(
                                    travel.getId(),
                                    travel.getTitle(),
                                    reserve.getReserveStatus(),
                                    reservedUser.getId(),
                                    reservedUser.getUsername(),
                                    reservedUser.getEmail(),
                                    reserve.getId()
                            );
                        })
                ).collect(Collectors.toList());
    }

    public List<TravelCompletedReservationResponseDto> completeReservation(SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("회원정보가 없습니다."));

        return user.getTravels().stream()
                .flatMap(travel -> travel.getReserves().stream()
                        .filter(reserve -> ReserveStatus.COMPLETE.equals(reserve.getReserveStatus()))
                        .map(reserve -> new TravelCompletedReservationResponseDto(
                                travel.getId(),
                                travel.getTitle(),
                                reserve.getReserveStatus(),
                                reserve.getUser().getId(),
                                reserve.getUser().getUsername(),
                                reserve.getUser().getEmail(),
                                reserve.getId()
                        ))
                ).collect(Collectors.toList());
    }
}
