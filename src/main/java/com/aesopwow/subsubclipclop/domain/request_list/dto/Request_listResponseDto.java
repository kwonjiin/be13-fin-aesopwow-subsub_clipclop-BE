package com.aesopwow.subsubclipclop.domain.request_list.dto;

import com.aesopwow.subsubclipclop.entity.RequestList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request_listResponseDto {
    private Long request_list_no;
    private Byte analysis_no;
    private Long company_no;
    private Long info_db_no;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Request_listResponseDto(RequestList requestList) {
        this.request_list_no = requestList.getRequestListNo();
        this.analysis_no = requestList.getAnalysis().getAnalysisNo();
        this.company_no = requestList.getCompany().getCompanyNo();
        this.info_db_no = requestList.getInfoDb().getInfoDbNo();
        this.created_at = requestList.getCreatedAt();
        this.updated_at = requestList.getUpdatedAt();
    }

}
