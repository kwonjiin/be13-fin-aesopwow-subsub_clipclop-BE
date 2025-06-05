package com.aesopwow.subsubclipclop.domain.api.service;

import com.aesopwow.subsubclipclop.domain.api.dto.ApiCohortRequestDto;
import com.aesopwow.subsubclipclop.domain.api.dto.ApiInsightResponseDto;
import com.aesopwow.subsubclipclop.domain.api.dto.ApiRequestDto;
import com.aesopwow.subsubclipclop.domain.api.dto.ApiResponseDto;
import com.aesopwow.subsubclipclop.domain.info_column.dto.InfoColumnResponseDto;
import com.aesopwow.subsubclipclop.entity.Analysis;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    private final WebClient webClient = WebClient.create("http://127.0.0.1:5001");
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

    @Override
    public byte[] getSingleAnalysisResult(String infoDbNo, String originTable, String clusterType) {
        if (infoDbNo == null || infoDbNo.isBlank() ||
                originTable == null || originTable.isBlank() ||
                clusterType == null || clusterType.isBlank()) {
            throw new IllegalArgumentException("단일 Cohort 분석에 필요한 파라미터가 누락되었습니다.");
        }

        try {
            return webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/python-api/analysis")
                            .queryParam("info_db_no", infoDbNo)
                            .queryParam("origin_table", originTable)
                            .queryParam("clusterType", clusterType)
                            .build())
                    .accept(MediaType.APPLICATION_OCTET_STREAM)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .timeout(Duration.ofSeconds(DASHBOARD_API_TIMEOUT_SECONDS))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("단일 Cohort 분석 요청 실패: {}, 상태 코드: {}", e.getMessage(), e.getStatusCode());
            throw new CustomException(ErrorCode.DASHBOARD_API_FAILED, e);
        } catch (Exception e) {
            log.error("단일 Cohort 분석 요청 중 예외 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.DASHBOARD_UNKNOWN_ERROR, e);
        }
    }

    @Override
    public byte[] getDoubleAnalysisResult(String infoDbNo, String originTable, String firstClusterType, String secondClusterType) {
        if (infoDbNo == null || infoDbNo.isBlank() ||
                originTable == null || originTable.isBlank() ||
                firstClusterType == null || firstClusterType.isBlank() ||
                secondClusterType == null || secondClusterType.isBlank()) {
            throw new IllegalArgumentException("이중 Cohort 분석에 필요한 파라미터가 누락되었습니다.");
        }

        try {
            return webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/python-api/analysis")
                            .queryParam("info_db_no", infoDbNo)
                            .queryParam("origin_table", originTable)
                            .queryParam("firstClusterType", firstClusterType)
                            .queryParam("secondClusterType", secondClusterType)
                            .build())
                    .accept(MediaType.APPLICATION_OCTET_STREAM)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .timeout(Duration.ofSeconds(DASHBOARD_API_TIMEOUT_SECONDS))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("이중 Cohort 분석 요청 실패: {}, 상태 코드: {}", e.getMessage(), e.getStatusCode());
            throw new CustomException(ErrorCode.DASHBOARD_API_FAILED, e);
        } catch (Exception e) {
            log.error("이중 Cohort 분석 요청 중 예외 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.DASHBOARD_UNKNOWN_ERROR, e);
        }
    }

    @Override
    public byte[] getFullShapResult(String infoDbNo, String originTable) {
        try {
            return webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/python-api/shap")
                            .queryParam("info_db_no", infoDbNo)
                            .queryParam("origin_table", originTable)
                            .build())
                    .accept(MediaType.APPLICATION_OCTET_STREAM)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .timeout(Duration.ofSeconds(DASHBOARD_API_TIMEOUT_SECONDS))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("SHAP 전체 분석 실패: {}", e.getMessage());
            throw new CustomException(ErrorCode.DASHBOARD_API_FAILED, e);
        } catch (Exception e) {
            log.error("SHAP 전체 분석 예외 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.DASHBOARD_UNKNOWN_ERROR, e);
        }
    }

    @Override
    public byte[] getFilteredShapResult(String infoDbNo, String originTable, String keyword, Map<String, Object> filters) {
        try {
            return webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/python-api/shap")
                            .queryParam("info_db_no", infoDbNo)
                            .queryParam("origin_table", originTable)
                            .queryParamIfPresent("keyword", Optional.ofNullable(keyword))
                            .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(filters)
                    .accept(MediaType.APPLICATION_OCTET_STREAM)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .timeout(Duration.ofSeconds(DASHBOARD_API_TIMEOUT_SECONDS))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("SHAP 필터 분석 실패: {}", e.getMessage());
            throw new CustomException(ErrorCode.DASHBOARD_API_FAILED, e);
        } catch (Exception e) {
            log.error("SHAP 필터 분석 예외 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.DASHBOARD_UNKNOWN_ERROR, e);
        }
    }

    @Override
    public byte[] getCohortOneAnalysis(Long infoDbNo, Analysis analysis, String filename) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/python-api/analysis/cohort")
                            .queryParam("info_db_no", infoDbNo)
                            .queryParam("analysis_type", analysis.getName().split("-")[1])
                            .queryParam("filename", filename)
                            .build())
                    .accept(MediaType.APPLICATION_OCTET_STREAM)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .timeout(Duration.ofSeconds(DASHBOARD_API_TIMEOUT_SECONDS))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("단일 Cohort 분석 요청 실패: {}, 상태 코드: {}", e.getMessage(), e.getStatusCode());
            throw new CustomException(ErrorCode.DASHBOARD_API_FAILED, e);
        } catch (Exception e) {
            log.error("단일 Cohort 분석 요청 중 예외 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.DASHBOARD_UNKNOWN_ERROR, e);
        }
    }

    @Override
    public Map<String, Object> getCohortListAnalysis(Long infoDbNo, Analysis analysis) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/python-api/analysis/cohort/list")
                            .queryParam("info_db_no", infoDbNo)
                            .queryParam("analysis_type", analysis.getName().split("-")[1])
                            .build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

        } catch (WebClientResponseException e) {
            log.error("Cohort 분석 요청 실패: {}, 상태 코드: {}", e.getMessage(), e.getStatusCode());
            throw new CustomException(ErrorCode.DASHBOARD_API_FAILED, e);
        } catch (Exception e) {
            log.error("Cohort 분석 요청 중 예외 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.DASHBOARD_UNKNOWN_ERROR, e);
        }
    }

    @Override
    public byte[] requestCohortAnalysis(ApiCohortRequestDto apiCohortRequestDto, Analysis analysis) {
        try {
            return webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/python-api/analysis/cohort")
                            .queryParam("info_db_no", apiCohortRequestDto.getInfoDbNo())
                            .queryParam("target_table_user", apiCohortRequestDto.getTargetTableUser())
                            .queryParam("target_table_sub", apiCohortRequestDto.getTargetTableSub())
                            .queryParam("analysis_type", analysis.getName().split("-")[1])
                            .queryParam("target_date", apiCohortRequestDto.getTargetDate())
                            .build())
                    .accept(MediaType.APPLICATION_OCTET_STREAM)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .timeout(Duration.ofSeconds(DASHBOARD_API_TIMEOUT_SECONDS))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Cohort 분석 요청 실패: {}, 상태 코드: {}", e.getMessage(), e.getStatusCode());
            throw new CustomException(ErrorCode.DASHBOARD_API_FAILED, e);
        } catch (Exception e) {
            log.error("Cohort 분석 요청 중 예외 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.DASHBOARD_UNKNOWN_ERROR, e);
        }
    }

    @Override
    public ApiInsightResponseDto getInsightByFilename(String filename) {
        try {
            return webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/python-api/openai/analyze")
                            .queryParam("filename", filename)
                            .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ApiInsightResponseDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Cohort 인사이트 분석 요청 실패: {}, 상태 코드: {}", e.getMessage(), e.getStatusCode());
            throw new CustomException(ErrorCode.DASHBOARD_API_FAILED, e);
        } catch (Exception e) {
            log.error("Cohort 인사이트 분석 요청 중 예외 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.DASHBOARD_UNKNOWN_ERROR, e);
        }
    }
}
