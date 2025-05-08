package com.aesopwow.subsubclipclop.domain.dbinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DbInfoResponseDto {
    private Long dbInfoNo;
    private Long companyNo;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
