package com.aesopwow.subsubclipclop.domain.api.service;

import com.aesopwow.subsubclipclop.domain.api.dto.ApiAnalysisRequestDto;
import com.aesopwow.subsubclipclop.domain.api.dto.ApiAnalysisResponseDto;
import com.aesopwow.subsubclipclop.domain.info_column.dto.Info_columnResponseDto;

import java.util.List;

public interface ApiService {
    public String callExternalApi(Long company_no);

    public List<Info_columnResponseDto> callExternalApiInfo_columns(Long info_db_no, String origin_table);

    public ApiAnalysisResponseDto requestAnalysis(ApiAnalysisRequestDto apiAnalysisRequestDto);

    public byte[] getAnalysisResult(String filename);
}
