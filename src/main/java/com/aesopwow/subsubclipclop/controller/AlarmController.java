package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.alarm.dto.AlarmResponseDto;
import com.aesopwow.subsubclipclop.domain.alarm.repository.SseEmitterRepository;
import com.aesopwow.subsubclipclop.domain.alarm.service.AlarmService;
import com.aesopwow.subsubclipclop.entity.Alarm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/alarms")
public class AlarmController {
    private final AlarmService alarmService;
    private final SseEmitterRepository sseEmitterRepository;

    // 클라이언트에서 sse연결
    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam Long userNo) {
        SseEmitter emitter = new SseEmitter(60 * 1000L * 30); // 30분 타임아웃
        sseEmitterRepository.save(userNo, emitter);

        emitter.onCompletion(() -> sseEmitterRepository.remove(userNo));
        emitter.onTimeout(() -> sseEmitterRepository.remove(userNo));

        try{
            emitter.send(SseEmitter.event().name("connect").data("connected"));
        } catch (IOException e){
            emitter.completeWithError(e);
        }
        return emitter;
    }

    // 관리자나 시스템이 유저에게 알림 전송
    @PostMapping("/send")
    public ResponseEntity<Void> sendAlarm(@RequestParam Long userNo, @RequestParam String content) {
        alarmService.sendAlarm(userNo, content);
        return ResponseEntity.ok().build();
    }

    // 알림 읽음 처리
    @PatchMapping("/{alarmNo}/read")
    public ResponseEntity<Void> read(@PathVariable Long alarmNo) {
        alarmService.markAsRead(alarmNo);
        return ResponseEntity.ok().build();
    }

    // 알림 전체 조회
    @GetMapping
    public ResponseEntity<List<AlarmResponseDto>> getAllAlarms(@RequestParam Long userNo) {
        List<AlarmResponseDto> alarms = alarmService.getAlarmsByUser(userNo);
        return ResponseEntity.ok().body(alarms);
    }

    // 안읽은 알림 있나 확인
    @GetMapping("/unread-exists")
    public ResponseEntity<Boolean> hasUnread(@RequestParam Long userNo) {
        return ResponseEntity.ok(alarmService.hasUnread(userNo));
    }

    // 알림 삭제
    @DeleteMapping("/{userNo}/delete")
    public ResponseEntity<Void> deleteAlarm(@PathVariable Long userNo, @RequestParam Long alarmNo ) {
        alarmService.deleteAlarm(alarmNo);
        return ResponseEntity.ok().build();
    }
}
