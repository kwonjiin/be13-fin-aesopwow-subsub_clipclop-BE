package com.aesopwow.subsubclipclop.domain.requirelist.dto;

import com.aesopwow.subsubclipclop.entity.RequireList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequireListResponseDto {
    private Long requestListNo;
    private Byte analysisNo;
    private Long companyNo;
    private Long dbInfoNo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RequireListResponseDto(RequireList requireList) {
        this.requestListNo = requireList.getRequestListNo();
        this.analysisNo = requireList.getAnalysis().getAnalysisNo();
        this.companyNo = requireList.getCompany().getCompanyNo();
        this.dbInfoNo = requireList.getDbInfo().getDbInfoNo();
        this.createdAt = requireList.getCreatedAt();
        this.updatedAt = requireList.getUpdatedAt();
    }
}
