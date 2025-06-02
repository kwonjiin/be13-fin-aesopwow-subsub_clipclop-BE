package com.aesopwow.subsubclipclop.domain.user.service;

import com.aesopwow.subsubclipclop.domain.company.dto.CompanyUpdateRequestDTO;
import com.aesopwow.subsubclipclop.domain.user.dto.MyPageResponseDTO;
import com.aesopwow.subsubclipclop.domain.user.dto.MyPageUpdateRequestDTO;
import jakarta.validation.Valid;

public interface MyPageService {
    MyPageResponseDTO getMyPageInfo(Long userNo);

    void updateCompanyInfo(CompanyUpdateRequestDTO companyUpdateRequestDTO);

    MyPageResponseDTO updateMyPageInfo(@Valid MyPageUpdateRequestDTO myPageUpdateRequestDTO);
}
