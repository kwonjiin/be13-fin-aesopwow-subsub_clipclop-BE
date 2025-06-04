package com.aesopwow.subsubclipclop.domain.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiCohortRequestDto {
    private Long infoDbNo;
    private Long analysisNo;
    private String targetTableUser;
    private String targetTableSub;
    private LocalDate targetDate;
    private String filename;
}
