package com.aesopwow.subsubclipclop.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MyPageResponseDTO {
    private final Long userNo;
    private final String membershipName;
//    private final int remainingDays;
    private LocalDateTime membershipExpiredAt; // 프론트 단에서 계산하기로 해서 추가함.
    private final String username;
    private final String departmentName;
}