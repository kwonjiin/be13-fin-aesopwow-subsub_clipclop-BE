package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.api.service.ApiService;
import com.aesopwow.subsubclipclop.domain.info_column.dto.InfoColumnResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/info-column")
@RequiredArgsConstructor
public class InfoColumnController {
    private final ApiService apiService;

    @GetMapping("/ext")
    public ResponseEntity<List<InfoColumnResponseDto>> getExternalInfoColumns(
            @RequestParam Long infoDbNo,
            @RequestParam(required = false) String originTable) {
        List<InfoColumnResponseDto> infoColumns = apiService.callExternalApiInfoColumns(infoDbNo, originTable);

        return ResponseEntity.ok(infoColumns);
    }

}
