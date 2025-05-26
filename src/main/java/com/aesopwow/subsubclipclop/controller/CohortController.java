package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.analysis.dto.CohortRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.service.CohortService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cohort")
@RequiredArgsConstructor
public class CohortController {

    private final CohortService cohortService;

    @PostMapping("/analyze")
    public ResponseEntity<Resource> analyzeCohort(@RequestBody CohortRequestDto requestDto) {
        Resource csvResource = cohortService.getCohortAnalysisCsv(requestDto);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=result.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvResource);
    }
}
