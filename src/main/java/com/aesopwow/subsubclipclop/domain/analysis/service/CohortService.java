package com.aesopwow.subsubclipclop.domain.analysis.service;

import com.aesopwow.subsubclipclop.domain.analysis.dto.CohortRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;


@Service
public class CohortService {

    private final String FLASK_BASE_URL = "http://127.0.0.1:5001/api/cohort/analyze";

    public Resource getCohortAnalysisCsv(CohortRequestDto dto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CohortRequestDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<byte[]> response = restTemplate.postForEntity(
                FLASK_BASE_URL, request, byte[].class);

        return new ByteArrayResource(response.getBody());
    }
}


