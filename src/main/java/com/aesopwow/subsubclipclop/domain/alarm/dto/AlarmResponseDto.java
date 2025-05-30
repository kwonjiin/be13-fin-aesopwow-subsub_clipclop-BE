package com.aesopwow.subsubclipclop.domain.alarm.dto;

import com.aesopwow.subsubclipclop.entity.Alarm;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AlarmResponseDto {
    private Long alarmNo;
    private String content;
    private boolean isRead;
    private LocalDateTime createdAt;

    public static AlarmResponseDto from(Alarm alarm) {
        return new AlarmResponseDto(
                alarm.getAlarmNo(),
                alarm.getContent(),
                alarm.getIsRead(),
                alarm.getCreatedAt()
        );
    }
}
