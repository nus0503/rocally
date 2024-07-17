package com.company.rocally.controller.comment;

import com.company.rocally.controller.travel.dto.CommentResponseDto;
import com.company.rocally.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

//    @PostMapping("/comment")
//    public ResponseEntity<CommentResponseDto> getComment()
}
