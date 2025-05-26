package com.aesopwow.subsubclipclop.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PostResponseDto {
    private final Long qnaPostNo;
    private final String title;
    private final String content;
    private final String createdAt;
}