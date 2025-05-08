package com.aesopwow.subsubclipclop.domain.analysis.repository.insight;

import com.aesopwow.subsubclipclop.domain.analysis.dto.insight.CohortAnalysisInsightAnalysisResultDto;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
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
        int score;

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mock/insight.csv")) {
            if (inputStream == null) {
                throw new CustomException(ErrorCode.CSV_NOT_FOUND);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                reader.readLine();
                String line = reader.readLine();
                if (line == null || line.trim().isEmpty()) {
                    throw new CustomException(ErrorCode.EMPTY_CSV);
                }

                try {
                    score = Integer.parseInt(line.trim());
                } catch (NumberFormatException e) {
                    throw new CustomException(ErrorCode.INVALID_CSV_FORMAT, e);
                }

                return new CohortAnalysisInsightAnalysisResultDto(score);
            }
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INSIGHT_READ_FAILURE, e);
        }
    }
}
