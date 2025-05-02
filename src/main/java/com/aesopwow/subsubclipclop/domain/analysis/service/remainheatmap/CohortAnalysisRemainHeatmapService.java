package com.aesopwow.subsubclipclop.domain.analysis.service.remainheatmap;

import com.aesopwow.subsubclipclop.domain.analysis.dto.remainheatmap.CohortAnalysisRemainHeatmapRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.remainheatmap.CohortAnalysisRemainHeatmapResponseDto;

public interface CohortAnalysisRemainHeatmapService {
    CohortAnalysisRemainHeatmapResponseDto fetchRemainHeatmap(CohortAnalysisRemainHeatmapRequestDto requestDto);
}
