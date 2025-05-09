package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.api.dto.ApiAnalysisRequestDto;
import com.aesopwow.subsubclipclop.domain.api.service.ApiService;
import com.aesopwow.subsubclipclop.domain.requirelist.dto.RequireListRequestDto;
import com.aesopwow.subsubclipclop.domain.requirelist.dto.RequireListResponseDto;
import com.aesopwow.subsubclipclop.domain.requirelist.service.RequireListService;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
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
public class RequireListController {
    private final RequireListService requireListService;
    private final ApiService apiService;

    @GetMapping("/{requireListNo}")
    public ResponseEntity<RequireListResponseDto> getRequireList(
            @PathVariable Long requireListNo
    ) {
        RequireListResponseDto requireListResponseDto
                = requireListService.getRequireList(requireListNo);

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

        try {
            apiService.requestAnalysis(apiAnalysisRequestDto);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.ANALYSIS_API_CALL_FAILURE, e);
        }

        return ResponseEntity.ok(requireListResponseDto);
    }
}
