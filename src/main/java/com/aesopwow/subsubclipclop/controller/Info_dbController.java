package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.api.service.ApiService;
import com.aesopwow.subsubclipclop.domain.info_db.dto.Info_dbRequestDto;
import com.aesopwow.subsubclipclop.domain.info_db.dto.Info_dbResponseDto;
import com.aesopwow.subsubclipclop.domain.info_db.service.Info_dbService;
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

    private final Info_dbService info_dbService;
    private final ApiService apiService;

    @GetMapping("/{info_db_no}")
    public ResponseEntity<Info_dbResponseDto> getInfo_db(
            @PathVariable Long info_db_no) {
        Info_dbResponseDto info_dbResponseDto = info_dbService.getInfo_db(info_db_no);

        return ResponseEntity.ok(info_dbResponseDto);
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
