package com.aesopwow.subsubclipclop.domain.info_column.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoColumnResponseDto {
    private Long infoColumnNo;
    private Long infoDbNo;
    private String analysisColumn;
    private String originColumn;
    private String originTable;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
