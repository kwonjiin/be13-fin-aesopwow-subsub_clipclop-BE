package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.api.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {
    private final ApiService apiService;

    @GetMapping("")
    public ResponseEntity<byte[]> getAnalysisResult(
            @RequestParam String infoDbNo,
            @RequestParam String originTable) {

        byte[] fileBytes = apiService.getAnalysisResult(infoDbNo, originTable);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("dashboard_" + infoDbNo + ".csv") // ✅ 파일 이름 명시
                .build());

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }
}