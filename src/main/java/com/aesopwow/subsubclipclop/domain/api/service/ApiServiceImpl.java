package com.aesopwow.subsubclipclop.domain.api.service;

import com.aesopwow.subsubclipclop.domain.api.dto.ApiAnalysisRequestDto;
import com.aesopwow.subsubclipclop.domain.api.dto.ApiAnalysisResponseDto;
import com.aesopwow.subsubclipclop.domain.info_column.dto.Info_columnResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    private final WebClient webClient = WebClient.create("http://127.0.0.1:5000");

    @Override
    public String callExternalApi(Long company_no) {
        return webClient.get()
                .uri("/python-api/info_db")
                .attribute("company_no", company_no)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 동기 방식으로 결과 대기
    }

    @Override
    public List<Info_columnResponseDto> callExternalApiInfo_columns(Long info_db_no, String origin_table) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/python-api/info_column")
                        .queryParam("info_db_no", info_db_no)
                        .queryParam("origin_table", origin_table)
                        .build()
                )
                .retrieve()
                .bodyToFlux(Info_columnResponseDto.class) // JSON 배열 -> List
                .collectList()
                .block(); // 동기 방식으로 결과 대기
    }

    @Override
    public byte[] getAnalysisResult(String filename) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/python-api/analysis")
                        .queryParam("file_name", filename)
                        .build()
                )
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .bodyToMono(byte[].class)
                .block(); // 동기 방식으로 대기
    }

    @Override
    public ApiAnalysisResponseDto requestAnalysis(ApiAnalysisRequestDto apiAnalysisRequestDto) {
        return webClient.post()
                .uri("/python-api/analysis")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(apiAnalysisRequestDto)
                .retrieve()
                .bodyToMono(ApiAnalysisResponseDto.class)
                .block();
    }

}
