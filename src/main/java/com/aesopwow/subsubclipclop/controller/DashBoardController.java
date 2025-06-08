package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.api.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/dash-board")
@RequiredArgsConstructor
public class DashBoardController {
    private final ApiService apiService;

//    @ExceptionHandler
//    @GetMapping("/{infoDbNo}")
//    public ResponseEntity<ApiResponseDto> getDashBoardCSV(
//            @PathVariable(required = true) String infoDbNo) {
//
//        byte[] statCardCSV = apiService.getAnalysisResult(infoDbNo);
//        ApiResponseDto apiResponseDto = new ApiResponseDto(statCardCSV);
//
//        return ResponseEntity.ok(apiResponseDto);
//    }

    @GetMapping("")
    public Mono<ResponseEntity<byte[]>> getDashBoardCSV(
            @RequestParam int infoDbNo,
            @RequestParam String user_info,
            @RequestParam String user_sub_info) {

        Mono<byte[]> response = apiService.getAnalysisResult(infoDbNo, user_info, user_sub_info);

        return response.map(body -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.parseMediaType("text/csv"));
                    headers.setContentDisposition(ContentDisposition.attachment().filename("default").build());
                    return ResponseEntity.ok().headers(headers).body(body);
                });
    }
}
