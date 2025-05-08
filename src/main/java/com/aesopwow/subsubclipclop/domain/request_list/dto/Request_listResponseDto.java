package com.aesopwow.subsubclipclop.domain.request_list.dto;

import com.aesopwow.subsubclipclop.entity.RequestList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request_listResponseDto {
    private Long request_list_no;
    private Byte analysis_no;
    private Long company_no;
    private Long dbInfoNo;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Request_listResponseDto(RequestList requestList) {
        this.request_list_no = requestList.getRequestListNo();
        this.analysis_no = requestList.getAnalysis().getAnalysisNo();
        this.company_no = requestList.getCompany().getCompanyNo();
        this.dbInfoNo = requestList.getDbInfo().getDbInfoNo();
        this.created_at = requestList.getCreatedAt();
        this.updated_at = requestList.getUpdatedAt();
    }
}
