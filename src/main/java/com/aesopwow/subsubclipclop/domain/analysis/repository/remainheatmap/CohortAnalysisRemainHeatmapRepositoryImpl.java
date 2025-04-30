package com.aesopwow.subsubclipclop.domain.analysis.repository.remainheatmap;

import com.aesopwow.subsubclipclop.domain.analysis.dto.remainheatmap.CohortAnalysisRemainHeatmapAnalysisResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Base64;

@Repository
@RequiredArgsConstructor
public class CohortAnalysisRemainHeatmapRepositoryImpl implements CohortAnalysisRemainHeatmapRepository {

    @Override
    public CohortAnalysisRemainHeatmapAnalysisResultDto requestHeatmapImage() {
        try {
            // ✅ CSV 파일 열기
            InputStream csvStream = getClass().getClassLoader()
                    .getResourceAsStream("mock/remain-heatmap.csv");

            if (csvStream == null) {
                throw new IllegalArgumentException("remain-heatmap.csv 파일을 찾을 수 없습니다.");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(csvStream));
            reader.readLine(); // 헤더 건너뜀
            String line = reader.readLine();

            // ✅ CSV 파싱 (score, imagePath)
            String[] parts = line.split(",");
            int score = Integer.parseInt(parts[0].trim());
            String imagePath = parts[1].trim();

            // ✅ 이미지 파일 Base64 인코딩
            InputStream imageStream = getClass().getClassLoader()
                    .getResourceAsStream(imagePath); // CSV에서 읽은 경로 사용

            if (imageStream == null) {
                throw new IllegalArgumentException(imagePath + " 파일을 찾을 수 없습니다.");
            }

            byte[] imageBytes = imageStream.readAllBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // ✅ DTO 반환
            return new CohortAnalysisRemainHeatmapAnalysisResultDto(score, base64Image);

        } catch (Exception e) {
            throw new RuntimeException("히트맵 분석 데이터 읽기 실패", e);
        }
    }
}
