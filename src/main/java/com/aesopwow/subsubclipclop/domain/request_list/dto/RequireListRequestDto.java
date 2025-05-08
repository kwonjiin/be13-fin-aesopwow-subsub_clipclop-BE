package com.aesopwow.subsubclipclop.domain.request_list.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequireListRequestDto {
    private Long analysis_no;
    private Long company_no;
    private Long dbInfoNo;
}
