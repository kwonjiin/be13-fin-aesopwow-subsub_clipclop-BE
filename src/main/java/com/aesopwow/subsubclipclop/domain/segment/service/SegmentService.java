package com.aesopwow.subsubclipclop.domain.segment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SegmentService {

    private static final String PYTHON_SERVER_URL = "http://127.0.0.1:5001/csv";

    public Resource getSegmentCsv(
            int infoDbNo,
            String userInfo,
            String userSubInfo,
            String targetColumn
    ) {
        RestTemplate restTemplate = new RestTemplate();

        // 쿼리스트링 생성
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PYTHON_SERVER_URL)
                .queryParam("info_db_no", infoDbNo)
                .queryParam("user_info", userInfo)
                .queryParam("user_sub_info", userSubInfo)
                .queryParam("target_column", targetColumn);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(
                MediaType.TEXT_PLAIN,
                MediaType.valueOf("text/csv"),
                MediaType.APPLICATION_OCTET_STREAM
        ));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                byte[].class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return new ByteArrayResource(response.getBody());
        } else {
            throw new RuntimeException("CSV 파일 다운로드 실패: " + response.getStatusCode());
        }
    }
}
