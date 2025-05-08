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
@RequestMapping("/api/info-db")
@RequiredArgsConstructor
public class Info_dbController {

    private final DbInfoService dbInfo_Service;
    private final ApiService apiService;

    @GetMapping("/{info_db_no}")
    public ResponseEntity<DbInfoResponseDto> getInfo_db(
            @PathVariable Long info_db_no) {
        DbInfoResponseDto dbInfo_ResponseDto = dbInfo_Service.getDbInfo(info_db_no);

        return ResponseEntity.ok(dbInfo_ResponseDto);
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
