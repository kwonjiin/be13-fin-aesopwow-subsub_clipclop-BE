// domain/analysis/service/AnalysisService.java
package com.aesopwow.subsubclipclop.domain.analysis.service;

import com.aesopwow.subsubclipclop.domain.analysis.dto.CohortAnalysisBehaviorPatternRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.CohortAnalysisBehaviorPatternResponseDto;

public interface AnalysisService {
    CohortAnalysisBehaviorPatternResponseDto fetchBehaviorPattern(CohortAnalysisBehaviorPatternRequestDto requestDto);
}
