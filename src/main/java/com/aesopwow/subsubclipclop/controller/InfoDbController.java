package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.api.service.ApiService;
import com.aesopwow.subsubclipclop.domain.info_db.dto.InfoDbResponseDto;
import com.aesopwow.subsubclipclop.domain.info_db.service.InfoDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info-db")
@RequiredArgsConstructor
public class InfoDbController {

    private final InfoDbService infoDbService;
    private final ApiService apiService;

    @GetMapping("/{infoDbNo}")
    public ResponseEntity<InfoDbResponseDto> getInfoDb(
            @PathVariable Long infoDbNo) {
        InfoDbResponseDto infoDbResponseDto = infoDbService.getInfoDb(infoDbNo);

        return ResponseEntity.ok(infoDbResponseDto);
    }

    @GetMapping("/ext/{companyNo}")
    public ResponseEntity<String> getExternalInfoDb(
            @PathVariable Long companyNo) {
        String str = apiService.callExternalApi(companyNo);

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
