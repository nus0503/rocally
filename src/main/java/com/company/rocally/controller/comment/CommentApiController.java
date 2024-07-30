package com.company.rocally.controller.comment;

import com.company.rocally.config.auth.LoginUser;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.comment.dto.CommentRequestDto;
import com.company.rocally.controller.travel.dto.CommentResponseDto;
import com.company.rocally.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@RequestParam Long travelId) {
        return ResponseEntity.ok(commentService.getCommentsWithReplies(travelId));
    }
    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto dto, @LoginUser SessionUser user) {
        return ResponseEntity.ok(commentService.addComment(dto, user));
    }


}
