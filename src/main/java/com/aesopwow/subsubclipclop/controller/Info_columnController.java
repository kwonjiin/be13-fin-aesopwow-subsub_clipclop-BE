package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.api.service.ApiService;
import com.aesopwow.subsubclipclop.domain.info_column.dto.Info_columnResponseDto;
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
public class Info_columnController {
    private final ApiService apiService;

    @GetMapping("/ext")
    public ResponseEntity<List<Info_columnResponseDto>> getExternalInfo_columns(
            @RequestParam Long info_db_no,
            @RequestParam(required = false) String origin_table) {
        List<Info_columnResponseDto> info_columns = apiService.callExternalApiInfo_columns(info_db_no, origin_table);

        return ResponseEntity.ok(info_columns);
    }

}
