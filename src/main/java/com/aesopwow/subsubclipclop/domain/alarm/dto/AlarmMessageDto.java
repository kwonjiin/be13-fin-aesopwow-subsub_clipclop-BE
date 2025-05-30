package com.aesopwow.subsubclipclop.domain.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmMessageDto {
    private Long userNo;
    private String content;
}
