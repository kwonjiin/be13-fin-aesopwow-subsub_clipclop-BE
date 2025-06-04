package com.aesopwow.subsubclipclop.domain.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiFileInfoResponseDto {
    private String key;
    private String lastModified;
    private long size;

    public ApiFileInfoResponseDto(String key, String lastModified, long size) {
        this.key = key;
        this.lastModified = lastModified;
        this.size = size;
    }
}
