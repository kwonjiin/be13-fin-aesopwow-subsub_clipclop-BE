package com.aesopwow.subsubclipclop.domain.request_list.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request_listRequestDto {
    private Long analysis_no;
    private Long company_no;
    private Long info_db_no;
}
