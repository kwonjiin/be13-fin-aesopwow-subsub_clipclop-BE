package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.api.service.ApiService;
import com.aesopwow.subsubclipclop.domain.dbinfo.dto.DbInfoResponseDto;
import com.aesopwow.subsubclipclop.domain.dbinfo.service.DbInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dbInfo")
@RequiredArgsConstructor
public class DbInfoController {
    private final DbInfoService dbInfoService;
    private final ApiService apiService;

    @GetMapping("/{dbInfoNo}")
    public ResponseEntity<DbInfoResponseDto> getDbInfo(
            @PathVariable Long dbInfoNo) {
        DbInfoResponseDto dbInfoResponseDto = dbInfoService.getDbInfo(dbInfoNo);

        return ResponseEntity.ok(dbInfoResponseDto);
    }

    @GetMapping("/ext/{company_no}")
    public ResponseEntity<String> getExternalInfo_db(
            @PathVariable Long company_no) {
        String str = apiService.callExternalApi(company_no);

        return ResponseEntity.ok(str);
    }

//    @GetMapping("")
//    public ResponseEntity<Info_dbResponseDto> getInfo_dbs(
//            @RequestParam Info_dbRequestDto requestDto) {
//        List<Info_dbResponseDto> info_dbResponseDto = info_dbService.getInfo_dbs(requestDto.getInfo_db_no());
//
//        return ResponseEntity.ok(info_dbResponseDto);
//    }
}
