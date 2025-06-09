package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.segment.dto.SegmentFileListResponseDto;
import com.aesopwow.subsubclipclop.domain.segment.dto.SegmentDto;
import com.aesopwow.subsubclipclop.domain.segment.service.SegmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/segment")
@RequiredArgsConstructor
@Tag(name = "세그먼트 관련 API", description = "세그먼트 관련 API 엔드포인트 모음")
public class SegmentController {

    private final SegmentService segmentService;

    /*
        지금 엔드 포인트 수정했고 해당 엔드포인트와 이름 별로 subscription 추가해함
     */
    @GetMapping("/subscription")
    @Operation(summary = "구독 유형", description = "구독 유형 세그먼트 분석")
    public ResponseEntity<SegmentDto> segmentSubscription(
            @RequestParam int info_db_no,
            @RequestParam(defaultValue = "user_info") String user_info,
            @RequestParam(defaultValue = "user_sub_info") String user_sub_info
    ) {
        SegmentDto response = segmentService.segmentSubscription(info_db_no, user_info, user_sub_info);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/watch-time")
    @Operation(summary = "누적 시청시간", description = "누적 시청시간 세그먼트 분석")
    public ResponseEntity<SegmentDto> segmentWatchTime(
            @RequestParam int info_db_no,
            @RequestParam(defaultValue = "user_info") String user_info,
            @RequestParam(defaultValue = "user_sub_info") String user_sub_info
    ) {
        SegmentDto response = segmentService.segmentWatchTime(info_db_no, user_info, user_sub_info);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/last-login")
    @Operation(summary = "마지막 접속일", description = "마지막 접속일 세그먼트 분석")
    public ResponseEntity<SegmentDto> lastLoginSegment(
            @RequestParam int info_db_no,
            @RequestParam(defaultValue = "user_info") String user_info,
            @RequestParam(defaultValue = "user_sub_info") String user_sub_info
    ) {
        SegmentDto response = segmentService.lastLoginSegment(info_db_no, user_info, user_sub_info);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/genre")
    @Operation(summary = "선호 장르", description = "선호 장르 세그먼트 분석")
    public ResponseEntity<SegmentDto> genreSegment(
            @RequestParam int info_db_no,
            @RequestParam(defaultValue = "user_info") String user_info,
            @RequestParam(defaultValue = "user_sub_info") String user_sub_info
    ) {
        SegmentDto response = segmentService.genreSegment(info_db_no, user_info, user_sub_info);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // 리스트 조회
    @GetMapping("/list")
    @Operation(summary = "리스트 조회", description = "원하는 유형의 리스트 조회")
    public SegmentFileListResponseDto getSegmentFileList(
            @RequestParam int infoDbNo,
            @RequestParam String targetColumn
    ) {
        return segmentService.getSegmentFileList(infoDbNo, targetColumn);
    }

    // 리스트에서 하나의 csv 파일 선택 (Query Param 방식)
    @GetMapping("/list/file")
    @Operation(summary = "csv 파일 다운", description = "원하는 csv 파일 다운")
    public ResponseEntity<byte[]> getSegmentCsvFile(
            @RequestParam("s3Key") String s3Key
    ) {
        try {
            // Flask 서버에 요청해서 파일 데이터(byte[]) 받기
            byte[] csvBytes = (byte[]) segmentService.getSegmentCsvFile(s3Key);

            // 파일명 추출 (s3Key에서 마지막 / 뒤의 값)
            String filename = s3Key.substring(s3Key.lastIndexOf('/') + 1);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            headers.setContentDisposition(
                    ContentDisposition.attachment()
                            .filename(filename)
                            .build()
            );

            return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // 에러 시 간단한 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

//    // 해당 csv 파일 삭제하는 컨트롤러
//    @DeleteMapping("/list/delete")


}
