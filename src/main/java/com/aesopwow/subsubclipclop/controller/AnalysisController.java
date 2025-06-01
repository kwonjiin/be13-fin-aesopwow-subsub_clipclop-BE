package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.api.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {
    private final ApiService apiService;

    @GetMapping("")
    public ResponseEntity<byte[]> getAnalysisResult(
            @RequestParam String infoDbNo,
            @RequestParam String originTable) {

        // 파라미터 유효성 검사
        if (infoDbNo == null || infoDbNo.isBlank() || originTable == null || originTable.isBlank()) {
            throw new IllegalArgumentException("필수 파라미터가 누락되었습니다.");
        }

        byte[] fileBytes = apiService.getAnalysisResult(infoDbNo, originTable);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("dashboard_" + infoDbNo + ".csv") // ✅ 파일 이름 명시
                .build());

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/cohort")
    public ResponseEntity<byte[]> getAnalysisCohortResult(
            @RequestParam String infoDbNo,
            @RequestParam String originTable,
            @RequestParam(required = false) String clusterType,
            @RequestParam(required = false) String firstClusterType,
            @RequestParam(required = false) String secondClusterType
    ) {

        // 공통 파라미터 검사
        if (infoDbNo.isBlank() || originTable.isBlank()) {
            throw new IllegalArgumentException("필수 파라미터(infoDbNo, originTable)가 누락되었습니다.");
        }

        byte[] fileBytes;

        // 📌 단일 Cohort 분석
        if (clusterType != null && !clusterType.isBlank()) {
            fileBytes = apiService.getSingleAnalysisResult(infoDbNo, originTable, clusterType);
        }
        // 📌 이중 Cohort 분석
        else if (firstClusterType != null && !firstClusterType.isBlank()
                && secondClusterType != null && !secondClusterType.isBlank()) {
            fileBytes = apiService.getDoubleAnalysisResult(infoDbNo, originTable, firstClusterType, secondClusterType);
        }
        // 📌 파라미터 부족
        else {
            throw new IllegalArgumentException("분석 유형에 필요한 파라미터가 누락되었습니다.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("analysis_result.csv")
                .build());

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/shap")
    public ResponseEntity<byte[]> getShapAnalysisResult(
            @RequestParam String infoDbNo,
            @RequestParam String originTable,
            @RequestParam(required = false) String keyword,
            @RequestBody(required = false) Map<String, Object> filters
    ) {
        if (infoDbNo == null || infoDbNo.isBlank() || originTable == null || originTable.isBlank()) {
            throw new IllegalArgumentException("필수 파라미터(infoDbNo, originTable)가 누락되었습니다.");
        }

        byte[] fileBytes;

        // 1️⃣ 필터 기반 SHAP 분석
        if (filters != null && !filters.isEmpty()) {
            fileBytes = apiService.getFilteredShapResult(infoDbNo, originTable, keyword, filters);
        }
        // 2️⃣ 전체 SHAP 분석
        else {
            fileBytes = apiService.getFullShapResult(infoDbNo, originTable);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("shap_result.csv")
                .build());

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }
}