package com.company.rocally.controller.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    private Long travelId;

    private String content;

    private Long parentId;
}
