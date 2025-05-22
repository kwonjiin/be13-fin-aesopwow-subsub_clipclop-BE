package com.aesopwow.subsubclipclop.domain.api.service;

import com.aesopwow.subsubclipclop.domain.api.dto.ApiRequestDto;
import com.aesopwow.subsubclipclop.domain.api.dto.ApiResponseDto;
import com.aesopwow.subsubclipclop.domain.info_column.dto.InfoColumnResponseDto;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    private final WebClient webClient = WebClient.create("http://127.0.0.1:5000");
    private static final Logger log = LoggerFactory.getLogger(ApiServiceImpl.class);

    @Override
    public String callExternalApi(Long companyNo) {
        return webClient.get()
                .uri("/python-api/info_db")
                .attribute("company_no", companyNo)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 동기 방식으로 결과 대기
    }

    @Override
    public List<InfoColumnResponseDto> callExternalApiInfoColumns(Long infoDbNo, String originTable) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/python-api/info_column")
                        .queryParam("info_db_no", infoDbNo)
                        .queryParam("origin_table", originTable)
                        .build()
                )
                .retrieve()
                .bodyToFlux(InfoColumnResponseDto.class) // JSON 배열 -> List
                .collectList()
                .block(); // 동기 방식으로 결과 대기
    }

    @Override
    public ApiResponseDto requestAnalysis(ApiRequestDto apiRequestDto) {
        return webClient.post()
                .uri("/python-api/analysis")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(apiRequestDto)
                .retrieve()
                .bodyToMono(ApiResponseDto.class)
                .block();
    }

//    @Override
//    public byte[] getAnalysisResult(String filename) {
//        return webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/python-api/analysis")
//                        .queryParam("file_name", filename)
//                        .build()
//                )
//                .accept(MediaType.APPLICATION_OCTET_STREAM)
//                .retrieve()
//                .bodyToMono(byte[].class)
//                .block(); // 동기 방식으로 대기
//    }

    private static final int DASHBOARD_API_TIMEOUT_SECONDS = 10;
    
    @Override
    public byte[] getAnalysisResult(String infoDbNo, String originTable) {

        if (infoDbNo == null || infoDbNo.isBlank() || originTable == null || originTable.isBlank()) {
            throw new IllegalArgumentException("필수 파라미터가 누락되었습니다.");
        }

        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/python-api/dashboard")
                            .queryParam("info_db_no", infoDbNo)
                            .queryParam("origin_table", originTable)
                            .build()
                    )
                    .accept(MediaType.APPLICATION_OCTET_STREAM)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .timeout(Duration.ofSeconds(DASHBOARD_API_TIMEOUT_SECONDS))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("대시보드 데이터 요청 실패: {}, 상태 코드: {}", e.getMessage(), e.getStatusCode());
            throw new CustomException(ErrorCode.DASHBOARD_API_FAILED, e);
        } catch (Exception e) {
            log.error("대시보드 데이터 요청 중 예외 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.DASHBOARD_UNKNOWN_ERROR, e);
        }
    }
}
