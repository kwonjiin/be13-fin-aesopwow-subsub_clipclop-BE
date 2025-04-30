package com.aesopwow.subsubclipclop.domain.analysis.repository.behaviorpattern;

import com.aesopwow.subsubclipclop.domain.analysis.dto.behaviorpattern.CohortAnalysisBehaviorPatternAnalysisResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Repository
@RequiredArgsConstructor
public class CohortAnalysisBehaviorPatternRepositoryImpl implements CohortAnalysisBehaviorPatternRepository {

    @Override
    public CohortAnalysisBehaviorPatternAnalysisResultDto requestBehaviorPattern() {
        try {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream("mock/behavior-pattern.csv");

            if (inputStream == null) {
                throw new IllegalArgumentException("CSV 파일을 찾을 수 없습니다.");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.readLine(); // 헤더 건너뛰기
            String line = reader.readLine(); // 첫 번째 데이터

            int featureValue = Integer.parseInt(line.trim());
            return new CohortAnalysisBehaviorPatternAnalysisResultDto(featureValue);

        } catch (Exception e) {
            throw new RuntimeException("CSV 파일 읽기 실패", e);
        }
    }
}