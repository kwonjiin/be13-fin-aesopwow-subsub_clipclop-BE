package com.aesopwow.subsubclipclop.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CommentResponseDto {
    private final Long commentNo;
    private final String content;
    private final String createdAt;
}