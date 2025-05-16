package com.aesopwow.subsubclipclop.domain.api.service;

import com.aesopwow.subsubclipclop.domain.api.dto.ApiRequestDto;
import com.aesopwow.subsubclipclop.domain.api.dto.ApiResponseDto;
import com.aesopwow.subsubclipclop.domain.info_column.dto.InfoColumnResponseDto;

import java.util.List;

public interface ApiService {
    public String callExternalApi(Long company_no);

    public List<InfoColumnResponseDto> callExternalApiInfoColumns(Long infoDbNo, String originTable);

    public ApiResponseDto requestAnalysis(ApiRequestDto apiRequestDto);

    public byte[] getAnalysisResult(String filename);
}
