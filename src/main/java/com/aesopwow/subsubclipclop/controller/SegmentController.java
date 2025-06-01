package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.segment.service.SegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/segment")
@RequiredArgsConstructor
public class SegmentController {

    private final SegmentService segmentService;

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadSegmentCsv(
            @RequestParam int info_db_no,
            @RequestParam(defaultValue = "user_info") String user_info,
            @RequestParam(defaultValue = "user_sub_info") String user_sub_info,
            @RequestParam(defaultValue = "subscription") String target_column
    ) {
        Resource csvResource = segmentService.getSegmentCsv(
                info_db_no, user_info, user_sub_info, target_column
        );

        String filename = String.format("%s_%s_segments.csv", target_column.replaceAll("[^\\w\\-_]", "_"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvResource);
    }

}

