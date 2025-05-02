// ✅ 구현체
package com.aesopwow.subsubclipclop.domain.analysis.repository.remainheatmap;

import com.aesopwow.subsubclipclop.domain.analysis.dto.remainheatmap.CohortAnalysisRemainHeatmapAnalysisResultDto;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Base64;

@Repository // ✅ 반드시 붙어 있어야 함
public class CohortAnalysisRemainHeatmapRepositoryImpl implements CohortAnalysisRemainHeatmapRepository {
    @Override
    public CohortAnalysisRemainHeatmapAnalysisResultDto requestHeatmapImage() {
        try {
            InputStream csvStream = getClass().getClassLoader().getResourceAsStream("mock/remain-heatmap.csv");
            if (csvStream == null) throw new IllegalArgumentException("CSV 파일 없음");

            BufferedReader reader = new BufferedReader(new InputStreamReader(csvStream));
            reader.readLine(); // 헤더 skip
            String line = reader.readLine();
            String[] parts = line.split(",");
            int score = Integer.parseInt(parts[0]);
            String imagePath = parts[1];

            InputStream imageStream = getClass().getClassLoader().getResourceAsStream(imagePath.trim());
            if (imageStream == null) throw new IllegalArgumentException("히트맵 이미지 파일 없음");

            byte[] imageBytes = imageStream.readAllBytes();
            String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);

            return new CohortAnalysisRemainHeatmapAnalysisResultDto(score, base64Image);
        } catch (Exception e) {
            throw new RuntimeException("히트맵 분석 데이터 읽기 실패", e);
        }
    }
}
