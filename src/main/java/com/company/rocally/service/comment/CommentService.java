package com.company.rocally.service.comment;

import com.company.rocally.controller.travel.dto.CommentResponseDto;
import com.company.rocally.domain.travel.Comment;
import com.company.rocally.domain.travel.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private CommentRepository commentRepository;

    public List<CommentResponseDto> getCommentsForTravel(Long travelId) {
        List<Comment> topLevelComments = commentRepository.findByTravelIdAndParentIdIsNullOrderByCreatedDateDesc(travelId);
        return topLevelComments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CommentResponseDto convertToDTO(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setReplies(comment.getReplies().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()));
        return dto;
    }
}
