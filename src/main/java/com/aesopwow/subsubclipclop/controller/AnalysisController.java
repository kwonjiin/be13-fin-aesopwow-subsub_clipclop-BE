package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.analysis.service.AnalysisService;
import com.aesopwow.subsubclipclop.domain.api.dto.ApiCohortRequestDto;
import com.aesopwow.subsubclipclop.domain.api.dto.ApiFileInfoResponseDto;
import com.aesopwow.subsubclipclop.domain.api.dto.ApiInsightResponseDto;
import com.aesopwow.subsubclipclop.domain.api.service.ApiService;
import com.aesopwow.subsubclipclop.entity.Analysis;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {
    private final ApiService apiService;
    private final AnalysisService analysisService;

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
    public ResponseEntity<byte[]> getAnalysisCohortOneResult(
            @RequestParam Long infoDbNo,
            @RequestParam Long analysisNo,
            @RequestParam String filename) {
        Analysis analysis = analysisService.getAnalysisByNo(analysisNo);

        if(analysis == null)
            throw new IllegalArgumentException("잘못된 analysis 번호입니다.");

        byte[] fileBytes = apiService.getCohortOneAnalysis(infoDbNo, analysis, filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/csv"));
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/cohort/list")
    public ResponseEntity<List<ApiFileInfoResponseDto>> getAnalysisCohortListResult(
            @RequestParam Long infoDbNo,
            @RequestParam Long analysisNo) {
        Analysis analysis = analysisService.getAnalysisByNo(analysisNo);

        if(analysis == null)
            throw new IllegalArgumentException("잘못된 analysis 번호입니다.");

        Map<String, Object> response = apiService.getCohortListAnalysis(infoDbNo, analysis);

        List<Map<String, Object>> contents = (List<Map<String, Object>>) response.get("Contents");

        List<ApiFileInfoResponseDto> fileInfos = contents.stream()
                .map(item -> new ApiFileInfoResponseDto(
                        (String) item.get("Key"),
                        (String) item.get("LastModified"),
                        ((Number) item.get("Size")).longValue()
                ))
                .collect(Collectors.toList());

        return new ResponseEntity<>(fileInfos, HttpStatus.OK);
    }

    @PostMapping("/cohort")
    public ResponseEntity<byte[]> requestAnalysisCohort(
            @RequestBody ApiCohortRequestDto apiCohortRequestDto
    ) {

        Analysis analysis = analysisService.getAnalysisByNo(apiCohortRequestDto.getAnalysisNo());

        if(analysis == null)
            throw new IllegalArgumentException("잘못된 analysis 번호입니다.");

        byte[] fileBytes = apiService.requestCohortAnalysis(apiCohortRequestDto, analysis);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("analysis_result.csv")
                .build());

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/cohort/insight")
    public ResponseEntity<ApiInsightResponseDto> getAnalysisCohortOneInsight(
            @RequestParam String filename) {

        ApiInsightResponseDto apiInsightResponseDto
                = apiService.getInsightByFilename(filename);

        return new ResponseEntity<>(apiInsightResponseDto, HttpStatus.OK);
    }
}