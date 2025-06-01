package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.segment.dto.SegmentCsvResponse;
import com.aesopwow.subsubclipclop.domain.segment.service.SegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/segment")
@RequiredArgsConstructor
public class SegmentController {

    private final SegmentService segmentService;

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadSegmentCsv(
            @RequestParam int info_db_no,
            @RequestParam(defaultValue = "user_info") String user_info,
            @RequestParam(defaultValue = "user_sub_info") String user_sub_info,
            @RequestParam(defaultValue = "subscription") String target_column
    ) {
        // 서비스로부터 byte[]와 파일명 반환 받기
        SegmentCsvResponse response = segmentService.getSegmentCsv(info_db_no, user_info, user_sub_info, target_column);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDisposition(ContentDisposition.attachment().filename(response.getFilename()).build());
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(response.getContent(), headers, HttpStatus.OK);
    }

}
