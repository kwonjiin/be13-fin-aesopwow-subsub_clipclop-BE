package com.aesopwow.subsubclipclop.domain.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyUpdateRequestDTO {
    private Long companyNo;
    private String companyName;
    private String departmentName; // 부서명
    private Long payment; // 결제정보
    private String InfoDb; // 제공 DB 정보 // DB정보 받아오는거 어떻게 하는지 모르겠음..
}
