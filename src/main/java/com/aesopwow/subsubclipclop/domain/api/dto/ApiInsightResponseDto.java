package com.aesopwow.subsubclipclop.domain.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiInsightResponseDto {
    private String summary;
    private List<String> recommendations;
    private String prediction;
}
