package com.company.rocally.controller.comment.dto;

import com.company.rocally.domain.travel.Comment;
import com.company.rocally.domain.travel.Travel;
import com.company.rocally.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    private Long travelId;

    private String content;

    private Long parentId;

    public Comment toEntity(Travel travel, User user, String content, Comment parent) {
        return Comment.generateComment(travel, user, content, parent);
    }
}
