package com.aesopwow.subsubclipclop.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DB_INFO_NOT_FOUND("E001", "DB 정보 없음", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("E002", "사용자 없음", HttpStatus.NOT_FOUND),
    ANALYSIS_NOT_FOUND("E003", "분석 정보 없음", HttpStatus.NOT_FOUND),
    COMPANY_NOT_FOUND("E004", "회사 정보 없음", HttpStatus.NOT_FOUND),
    CSV_NOT_FOUND("E005", "CSV 파일 없음", HttpStatus.NOT_FOUND),
    IMAGE_NOT_FOUND("E006", "이미지 파일 없음", HttpStatus.NOT_FOUND),
    HEATMAP_READ_FAILURE("E007", "히트맵 분석 데이터 읽기 실패", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPTY_CSV("E008", "CSV 파일이 비어 있습니다.", HttpStatus.BAD_REQUEST),
    INVALID_CSV_FORMAT("E009", "CSV 파일 포맷이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INSIGHT_READ_FAILURE("E010", "인사이트 분석 데이터 읽기 실패", HttpStatus.INTERNAL_SERVER_ERROR),
    BEHAVIOR_PATTERN_READ_FAILURE("E011", "행동 패턴 분석 데이터 읽기 실패", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}