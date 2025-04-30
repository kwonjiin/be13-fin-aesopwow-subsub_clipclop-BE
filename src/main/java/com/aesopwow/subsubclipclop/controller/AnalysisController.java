package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.analysis.dto.behaviorpattern.CohortAnalysisBehaviorPatternRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.behaviorpattern.CohortAnalysisBehaviorPatternResponseDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.insight.CohortAnalysisInsightRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.insight.CohortAnalysisInsightResponseDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.remainheatmap.CohortAnalysisRemainHeatmapRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.remainheatmap.CohortAnalysisRemainHeatmapResponseDto;
import com.aesopwow.subsubclipclop.domain.analysis.service.behaviorpattern.CohortAnalysisBehaviorPatternService;
import com.aesopwow.subsubclipclop.domain.analysis.service.insight.CohortAnalysisInsightService;
import com.aesopwow.subsubclipclop.domain.analysis.service.remainheatmap.CohortAnalysisRemainHeatmapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cohorts")
@RequiredArgsConstructor
public class AnalysisController {

    private final CohortAnalysisBehaviorPatternService cohortAnalysisBehaviorPatternService;
    private final CohortAnalysisInsightService cohortAnalysisInsightService;
    private final CohortAnalysisRemainHeatmapService cohortAnalysisRemainHeatmapService;

    @PostMapping("/behavior-pattern")
    public CohortAnalysisBehaviorPatternResponseDto fetchBehaviorPattern(
            @RequestBody CohortAnalysisBehaviorPatternRequestDto requestDto
    ) {
        return cohortAnalysisBehaviorPatternService.fetchBehaviorPattern(requestDto);
    }

    @PostMapping("/insight")
    public CohortAnalysisInsightResponseDto fetchInsight(
            @RequestBody CohortAnalysisInsightRequestDto requestDto
    ) {
        return cohortAnalysisInsightService.fetchInsight(requestDto);
    }

    @PostMapping("/remain-heatmap")
    public CohortAnalysisRemainHeatmapResponseDto fetchRemainHeatmap(
            @RequestBody CohortAnalysisRemainHeatmapRequestDto requestDto
    ) {
        return cohortAnalysisRemainHeatmapService.fetchRemainHeatmap(requestDto);
    }
}