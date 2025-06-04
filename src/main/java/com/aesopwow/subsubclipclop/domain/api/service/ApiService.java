package com.aesopwow.subsubclipclop.domain.api.service;

import com.aesopwow.subsubclipclop.domain.api.dto.ApiCohortRequestDto;
import com.aesopwow.subsubclipclop.domain.api.dto.ApiRequestDto;
import com.aesopwow.subsubclipclop.domain.api.dto.ApiResponseDto;
import com.aesopwow.subsubclipclop.domain.info_column.dto.InfoColumnResponseDto;
import com.aesopwow.subsubclipclop.entity.Analysis;

import java.util.List;
import java.util.Map;

public interface ApiService {
    public String callExternalApi(Long company_no);

    public List<InfoColumnResponseDto> callExternalApiInfoColumns(Long infoDbNo, String originTable);

    public ApiResponseDto requestAnalysis(ApiRequestDto apiRequestDto);

//    public byte[] getAnalysisResult(String filename);

    public byte[] getAnalysisResult(String infoDbNo, String originTable);

    public byte[] getSingleAnalysisResult(String infoDbNo, String originTable, String clusterType);

    public byte[] getDoubleAnalysisResult(String infoDbNo, String originTable, String firstClusterType, String secondClusterType);

    public byte[] getFullShapResult(String infoDbNo, String originTable);

    public byte[] getFilteredShapResult(String infoDbNo, String originTable, String keyword, Map<String, Object> filters);

    public byte[] getCohortOneAnalysis(Long infoDbNo, Analysis analysis, String filename);

    public Map<String, Object> getCohortListAnalysis(Long infoDbNo, Analysis analysis);

    public byte[] requestCohortAnalysis(ApiCohortRequestDto apiCohortRequestDto, Analysis analysis);
}
