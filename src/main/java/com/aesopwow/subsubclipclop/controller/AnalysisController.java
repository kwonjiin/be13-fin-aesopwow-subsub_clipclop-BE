package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.analysis.dto.CohortAnalysisBehaviorPatternRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.CohortAnalysisBehaviorPatternResponseDto;
import com.aesopwow.subsubclipclop.domain.analysis.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cohorts")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping("/behavior-pattern")
    public CohortAnalysisBehaviorPatternResponseDto fetchBehaviorPattern(
            @RequestBody CohortAnalysisBehaviorPatternRequestDto requestDto
    ) {
        return analysisService.fetchBehaviorPattern(requestDto);
    }
}