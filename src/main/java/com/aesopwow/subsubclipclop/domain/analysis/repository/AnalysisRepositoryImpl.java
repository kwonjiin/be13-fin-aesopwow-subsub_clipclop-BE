// domain/analysis/repository/AnalysisRepositoryImpl.java
package com.aesopwow.subsubclipclop.domain.analysis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Repository
@RequiredArgsConstructor
public class AnalysisRepositoryImpl implements AnalysisRepository {

    @Override
    public Integer requestBehaviorPattern() {
        try {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream("mock/behavior-pattern.csv");

            if (inputStream == null) {
                throw new IllegalArgumentException("CSV 파일을 찾을 수 없습니다.");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.readLine(); // 헤더 건너뛰기
            String line = reader.readLine(); // 첫 번째 데이터

            return Integer.parseInt(line.trim());

        } catch (Exception e) {
            throw new RuntimeException("CSV 파일 읽기 실패", e);
        }
    }
}