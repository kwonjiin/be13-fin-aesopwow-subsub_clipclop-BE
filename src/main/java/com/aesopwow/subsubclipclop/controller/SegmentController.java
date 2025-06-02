package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.segment.dto.SegmentFileListResponseDto;
import com.aesopwow.subsubclipclop.domain.segment.dto.SegmentSaveResponseDto;
import com.aesopwow.subsubclipclop.domain.segment.service.SegmentService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/segment")
@RequiredArgsConstructor
public class SegmentController {

    private final SegmentService segmentService;

    @GetMapping("/download")
    public ResponseEntity<SegmentSaveResponseDto> downloadSegmentCsv(
            @RequestParam int info_db_no,
            @RequestParam(defaultValue = "user_info") String user_info,
            @RequestParam(defaultValue = "user_sub_info") String user_sub_info,
            @Parameter(description = "watch_time, subscription, favorite_genre, last_login") String target_column
    ) {
        SegmentSaveResponseDto response = segmentService.getSegmentCsv(info_db_no, user_info, user_sub_info, target_column);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // 리스트 조회
    @GetMapping("/list")
    public SegmentFileListResponseDto getSegmentFileList(
            @RequestParam int infoDbNo,
            @RequestParam String targetColumn
    ) {
        return segmentService.getSegmentFileList(infoDbNo, targetColumn);
    }

    // 리스트에서 하나의 csv 파일 선택
    @GetMapping("list/{csvFile}")


    // 해당 csv 파일 삭제하는 컨트롤러
    @DeleteMapping("/list/delete")
    public ResponseEntity<List<Objects>> deleteSegmentCsv() {
        return null;
    }

}
