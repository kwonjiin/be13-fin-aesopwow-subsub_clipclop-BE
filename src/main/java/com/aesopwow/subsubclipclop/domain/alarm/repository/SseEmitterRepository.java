package com.aesopwow.subsubclipclop.domain.alarm.repository;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEmitterRepository {
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void save(Long userNo, SseEmitter emitter) {
        emitters.put(userNo, emitter);
    }

    // 유저 있으면 SseEmitter 객체 반환
    public Optional<SseEmitter> get(Long userNo) {
        return Optional.ofNullable(emitters.get(userNo));
    }

    public void remove(Long userNo) {
        emitters.remove(userNo);
    }
}
