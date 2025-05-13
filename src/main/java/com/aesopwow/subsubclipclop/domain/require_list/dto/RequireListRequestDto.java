package com.aesopwow.subsubclipclop.domain.require_list.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequireListRequestDto {
    private Long analysisNo;
    private Long companyNo;
    private Long dbInfoNo;
}
