// ✅ 구현체
package com.aesopwow.subsubclipclop.domain.analysis.repository.remainheatmap;

import com.aesopwow.subsubclipclop.domain.analysis.dto.remainheatmap.CohortAnalysisRemainHeatmapAnalysisResultDto;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

@Repository
@RequiredArgsConstructor
public class CohortAnalysisRemainHeatmapRepositoryImpl implements CohortAnalysisRemainHeatmapRepository {
    private final ResourceLoader resourceLoader;

    @Override
    public CohortAnalysisRemainHeatmapAnalysisResultDto requestHeatmapImage() {
        try (InputStream csvStream = resourceLoader.getResource("classpath:mock/remain-heatmap.csv").getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(csvStream))) {

            reader.readLine(); // 헤더 skip
            String line = reader.readLine();
            if (line == null) throw new CustomException(ErrorCode.EMPTY_CSV);

            String[] parts = line.split(",");
            if (parts.length < 2) throw new CustomException(ErrorCode.INVALID_CSV_FORMAT);

            int score = Integer.parseInt(parts[0]);
            String imagePath = parts[1].trim();

            try (InputStream imageStream = resourceLoader.getResource("classpath:" + imagePath).getInputStream()) {
                byte[] imageBytes = imageStream.readAllBytes();
                String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
                return new CohortAnalysisRemainHeatmapAnalysisResultDto(score, base64Image);
            }
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.HEATMAP_READ_FAILURE, e);
        }
    }
}
