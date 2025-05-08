package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.api.dto.ApiAnalysisRequestDto;
import com.aesopwow.subsubclipclop.domain.api.service.ApiService;
import com.aesopwow.subsubclipclop.domain.request_list.dto.Request_listRequestDto;
import com.aesopwow.subsubclipclop.domain.request_list.dto.Request_listResponseDto;
import com.aesopwow.subsubclipclop.domain.request_list.service.Request_listService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/request-list")
@RequiredArgsConstructor
public class Request_listController {
    private final Request_listService request_listService;
    private final ApiService apiService;

    @GetMapping("/{request_list_no}")
    public ResponseEntity<Request_listResponseDto> getRequest_list(
            @PathVariable Long request_list_no
    ) {
        Request_listResponseDto request_listResponseDto
                = request_listService.getRequest_list(request_list_no);

        return ResponseEntity.ok(request_listResponseDto);
    }

    @PostMapping("")
    public ResponseEntity<Request_listResponseDto> createRequest_list(
            @RequestBody Request_listRequestDto requestDto
    ) {
        Request_listResponseDto request_listResponseDto =
                request_listService.createRequest_list(requestDto);

        ApiAnalysisRequestDto apiAnalysisRequestDto
                = new ApiAnalysisRequestDto(requestDto.getInfo_db_no());

        apiService.requestAnalysis(apiAnalysisRequestDto);

        return ResponseEntity.ok(request_listResponseDto);
    }
}
