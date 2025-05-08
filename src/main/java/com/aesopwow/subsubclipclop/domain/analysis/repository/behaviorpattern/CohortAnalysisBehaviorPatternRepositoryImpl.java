package com.aesopwow.subsubclipclop.domain.analysis.repository.behaviorpattern;

import com.aesopwow.subsubclipclop.domain.analysis.dto.behaviorpattern.CohortAnalysisBehaviorPatternAnalysisResultDto;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Repository
public class CohortAnalysisBehaviorPatternRepositoryImpl implements CohortAnalysisBehaviorPatternRepository {
    @Override
    public CohortAnalysisBehaviorPatternAnalysisResultDto requestBehaviorPattern() {
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("mock/behavior-pattern.csv")) {

            // 1. CSV 파일 존재 여부 확인
            if (inputStream == null) {
                throw new CustomException(ErrorCode.CSV_NOT_FOUND);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                reader.readLine();
                String line = reader.readLine();

                // 2. 데이터 존재 여부 확인
                if (line == null || line.trim().isEmpty()) {
                    throw new CustomException(ErrorCode.EMPTY_CSV);
                }

                String[] parts = line.split(",");
                // 3. CSV 포맷 유효성 검사
                if (parts.length < 1) {
                    throw new CustomException(ErrorCode.INVALID_CSV_FORMAT);
                }

                // 4. 정수 파싱
                int feature;
                try {
                    feature = Integer.parseInt(parts[0].trim());
                } catch (NumberFormatException e) {
                    throw new CustomException(ErrorCode.INVALID_CSV_FORMAT, e);
                }

                return new CohortAnalysisBehaviorPatternAnalysisResultDto(feature);
            }
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BEHAVIOR_PATTERN_READ_FAILURE, e);
        }
    }
}
