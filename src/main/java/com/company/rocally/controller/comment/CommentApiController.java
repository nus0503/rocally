package com.company.rocally.controller.comment;

import com.company.rocally.config.auth.LoginUser;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.comment.dto.CommentRequestDto;
import com.company.rocally.controller.travel.dto.CommentResponseDto;
import com.company.rocally.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto dto, @LoginUser SessionUser user) {

    }


}
