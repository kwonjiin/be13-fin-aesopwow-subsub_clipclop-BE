package com.aesopwow.subsubclipclop.domain.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class BaseResponseDto<T> {
    @Schema(description = "응답 코드", example = "200")
    protected final int code;

    @Schema(description = "응답 메시지", example = "OK")
    protected final String message;

    @Schema(description = "응답 데이터")
    protected final T data;

    public BaseResponseDto(HttpStatus status, T data) {
        this.code = status.value();
        this.message = status.getReasonPhrase();
        this.data = data;
    }
}
