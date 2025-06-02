package com.aesopwow.subsubclipclop.domain.info_db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoDbResponseDto {
    private Long infoDbNo;
    private Long companyNo;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
