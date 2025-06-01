package com.aesopwow.subsubclipclop.domain.segment.service;

import com.aesopwow.subsubclipclop.domain.segment.dto.SegmentCsvResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SegmentService {

    private static final String PYTHON_SERVER_URL = "http://127.0.0.1:5001/api/segment/download";

    public SegmentCsvResponse getSegmentCsv(
            int infoDbNo,
            String userInfo,
            String userSubInfo,
            String targetColumn
    ) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PYTHON_SERVER_URL)
                .queryParam("info_db_no", infoDbNo)
                .queryParam("user_info", userInfo)
                .queryParam("user_sub_info", userSubInfo)
                .queryParam("target_column", targetColumn);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.ALL)); // 모든 타입 허용
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                byte[].class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String filename = "segment.csv"; // 기본값

            List<String> contentDisposition = response.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION);
            if (contentDisposition != null && !contentDisposition.isEmpty()) {
                String header = contentDisposition.get(0);
                int index = header.indexOf("filename=");
                if (index != -1) {
                    filename = header.substring(index + 9).replace("\"", "");
                }
            }

            return new SegmentCsvResponse(response.getBody(), filename);
        } else {
            throw new RuntimeException("CSV 파일 다운로드 실패: " + response.getStatusCode());
        }
    }
}
