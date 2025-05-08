package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.api.dto.ApiAnalysisRequestDto;
import com.aesopwow.subsubclipclop.domain.api.service.ApiService;
import com.aesopwow.subsubclipclop.domain.request_list.dto.RequireListRequestDto;
import com.aesopwow.subsubclipclop.domain.request_list.dto.RequireListResponseDto;
import com.aesopwow.subsubclipclop.domain.request_list.service.RequireListService;
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
    private final RequireListService requireListService;
    private final ApiService apiService;

    @GetMapping("/{request_list_no}")
    public ResponseEntity<RequireListResponseDto> getRequireList(
            @PathVariable Long request_list_no
    ) {
        RequireListResponseDto requireListResponseDto
                = requireListService.getRequireList(request_list_no);

        return ResponseEntity.ok(requireListResponseDto);
    }

    @PostMapping("")
    public ResponseEntity<RequireListResponseDto> createRequireList(
            @RequestBody RequireListRequestDto requestDto
    ) {
        RequireListResponseDto requireListResponseDto =
                requireListService.createRequireList(requestDto);

        ApiAnalysisRequestDto apiAnalysisRequestDto
                = new ApiAnalysisRequestDto(requestDto.getDbInfoNo());

        apiService.requestAnalysis(apiAnalysisRequestDto);

        return ResponseEntity.ok(requireListResponseDto);
    }
}
