package com.aesopwow.subsubclipclop.domain.analysis.repository.insight;

import com.aesopwow.subsubclipclop.domain.analysis.dto.insight.CohortAnalysisInsightAnalysisResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Repository
@RequiredArgsConstructor
public class CohortAnalysisInsightRepositoryImpl implements CohortAnalysisInsightRepository {

    @Override
    public CohortAnalysisInsightAnalysisResultDto requestInsight() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mock/insight.csv");

            if (inputStream == null) {
                throw new IllegalArgumentException("CSV 파일을 찾을 수 없습니다.");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.readLine(); // 헤더 스킵
            String line = reader.readLine();

            int score = Integer.parseInt(line.trim());
            return new CohortAnalysisInsightAnalysisResultDto(score);
        } catch (Exception e) {
            throw new RuntimeException("CSV 파일 읽기 실패", e);
        }
    }
}