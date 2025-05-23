package com.aesopwow.subsubclipclop.domain.analysis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CohortRequestDto {
    // 외부 데이터베이스 no
    @NotBlank
    private int info_db_no;
    // 기존 users 테이블 명
    @NotBlank
    private String user_info;
    // 유저 구독 리스트 정보
    @NotBlank
    private String user_sub_info;
    // 2024 or 2025 선택
    @NotNull
    private int year;
    // 선택
    @NotBlank
    private String target_column;

    /*
    수정 되면 여기 dto 사용할 예정

    @NotBlank
    private String user_info;
    @NotBlank
    private String user_sub_info;
    */
}
