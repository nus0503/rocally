package com.company.rocally.controller.travel.dto;

import com.company.rocally.domain.travel.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentResponseDto {

    private Long id;

    private String author;
    private String content;
    private List<CommentResponseDto> replies;

    private String modifiedDate;

}
