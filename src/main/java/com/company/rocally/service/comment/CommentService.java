package com.company.rocally.service.comment;

import com.company.rocally.controller.travel.dto.CommentResponseDto;
import com.company.rocally.domain.travel.Comment;
import com.company.rocally.domain.travel.CommentRepository;
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

    public List<CommentResponseDto> getCommentsWithReplies(Long travelId) {
        List<Comment> topLevelComments = commentRepository.findByTravelIdAndParentIdIsNullOrderByCreatedDateDesc(travelId);
        if (topLevelComments == null) {
            return new ArrayList<>();
        }
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
