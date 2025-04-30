// domain/analysis/service/AnalysisService.java
package com.aesopwow.subsubclipclop.domain.analysis.service.behaviorpattern;

import com.aesopwow.subsubclipclop.domain.analysis.dto.behaviorpattern.CohortAnalysisBehaviorPatternRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.behaviorpattern.CohortAnalysisBehaviorPatternResponseDto;

public interface CohortAnalysisBehaviorPatternService {
    CohortAnalysisBehaviorPatternResponseDto fetchBehaviorPattern(CohortAnalysisBehaviorPatternRequestDto requestDto);
}
