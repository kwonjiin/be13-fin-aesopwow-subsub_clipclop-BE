// domain/analysis/repository/AnalysisRepositoryImpl.java
package com.aesopwow.subsubclipclop.domain.analysis.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AnalysisRepositoryImpl implements AnalysisRepository {

//    private final WebClient webClient; // WebClient를 통한 비동기 HTTP 요청

//    @Override
//    public Integer requestBehaviorPattern() {
//        try {
//            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mock/behavior-pattern.json");
//            if (inputStream == null) {
//                throw new IllegalArgumentException("테스트용 JSON 파일을 찾을 수 없습니다.");
//            }
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> responseMap = objectMapper.readValue(inputStream, Map.class);
//
//            return (Integer) responseMap.get("features");
//
//        } catch (IOException e) {
//            throw new RuntimeException("테스트용 JSON 파일 읽기 실패", e);
//        }
//    }

    // 테스트용
    @Override
    public Integer requestBehaviorPattern() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mock/behavior-pattern.json");
            if (inputStream == null) {
                throw new IllegalArgumentException("테스트용 JSON 파일을 찾을 수 없습니다.");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(inputStream, Map.class);

            return (Integer) responseMap.get("features");

        } catch (IOException e) {
            throw new RuntimeException("테스트용 JSON 파일 읽기 실패", e);
        }
    }
}