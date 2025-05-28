package com.aesopwow.subsubclipclop.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "내용은 필수입니다.")
    private final String content;

    @NotNull(message = "작성자(관리자) 번호는 필수입니다.")
    private final Long userNo;
}