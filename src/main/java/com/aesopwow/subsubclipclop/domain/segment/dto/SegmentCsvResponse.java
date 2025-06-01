package com.aesopwow.subsubclipclop.domain.segment.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SegmentCsvResponse {
    private final byte[] content;
    private final String filename;

    public SegmentCsvResponse(byte[] content, String filename) {
        this.content = content;
        this.filename = filename;
    }
}
