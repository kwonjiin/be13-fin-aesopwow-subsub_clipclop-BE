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
    private Long requireListNo;
    private Byte analysisNo;
    private Long companyNo;
    private Long infoDbNo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RequireListResponseDto(RequireList requireList) {
        this.requireListNo = requireList.getRequireListNo();
        this.analysisNo = requireList.getAnalysis().getAnalysisNo();
        this.companyNo = requireList.getCompany().getCompanyNo();
        this.infoDbNo = requireList.getInfoDb().getInfoDbNo();
        this.createdAt = requireList.getCreatedAt();
        this.updatedAt = requireList.getUpdatedAt();
    }
}
