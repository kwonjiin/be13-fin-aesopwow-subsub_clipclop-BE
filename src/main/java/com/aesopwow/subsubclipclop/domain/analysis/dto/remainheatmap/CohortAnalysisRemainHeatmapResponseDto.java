package com.aesopwow.subsubclipclop.domain.analysis.dto.remainheatmap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CohortAnalysisRemainHeatmapResponseDto {
    private String title;
    private String content;
    private String heatmapImageBase64;  // ✅ 이미지 Base64 필드 추가

}