package com.company.rocally.service.comment;

import com.company.rocally.common.customException.RestApiException;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.comment.dto.CommentRequestDto;
import com.company.rocally.controller.travel.dto.CommentResponseDto;
import com.company.rocally.domain.rating.Rating;
import com.company.rocally.domain.rating.RatingRepository;
import com.company.rocally.domain.travel.Comment;
import com.company.rocally.domain.travel.CommentRepository;
import com.company.rocally.domain.travel.Travel;
import com.company.rocally.domain.travel.TravelRepository;
import com.company.rocally.domain.user.User;
import com.company.rocally.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final TravelRepository travelRepository;

    private final UserRepository userRepository;

    private final RatingRepository ratingRepository;

    public List<CommentResponseDto> getCommentsWithReplies(Long travelId) {
        List<Comment> topLevelComments = commentRepository.findByTravelIdAndParentIdIsNullOrderByCreatedDateDesc(travelId);
        if (topLevelComments == null) {
            return new ArrayList<>();
        }
        return topLevelComments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto addComment(CommentRequestDto dto, SessionUser sessionUser) {
        Travel travel = travelRepository.findById(dto.getTravelId()).orElseThrow(
                () -> new IllegalArgumentException("해당 상품은 없습니다."));
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("유저가 없습니다."));

        Comment parent = null;
        if (dto.getParentId() != null) {
            parent = commentRepository.findById(dto.getParentId()).orElseThrow(
                    () -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        }
        Comment comment = dto.toEntity(travel, user, dto.getContent(), parent);
        Comment savedComment = commentRepository.save(comment);
        ratingRepository.save(Rating.generateEntity(dto.getRatingValue(), user, comment));
        return convertToDTO(savedComment);

    }

    private CommentResponseDto convertToDTO(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setAuthor(comment.getUser().getUsername());
        dto.setModifiedDate(comment.getModifiedDate());
        dto.setReplies(comment.getReplies().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()));
        return dto;
    }
}
