package com.aesopwow.subsubclipclop.domain.analysis.dto.remainheatmap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CohortAnalysisRemainHeatmapAnalysisResultDto {
    private final int score;
    private final String heatmapBase64;
}