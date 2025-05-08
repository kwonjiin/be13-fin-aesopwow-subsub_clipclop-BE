package com.aesopwow.subsubclipclop.global.enums;

import com.aesopwow.subsubclipclop.domain.analysis.service.AnalysisService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DB_INFO_NOT_FOUND("E001", "DB 정보 없음", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("E002", "사용자 없음", HttpStatus.NOT_FOUND),
    ANALYSIS_NOT_FOUND("E003", "분석 정보 없음", HttpStatus.NOT_FOUND),
    COMPANY_NOT_FOUND("E004", "회사 정보 없음", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}