package com.aesopwow.subsubclipclop.domain.analysis.service.insight;

import com.aesopwow.subsubclipclop.domain.analysis.dto.insight.CohortAnalysisInsightRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.insight.CohortAnalysisInsightResponseDto;

public interface CohortAnalysisInsightService {
    CohortAnalysisInsightResponseDto fetchInsight(CohortAnalysisInsightRequestDto requestDto);
}