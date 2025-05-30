package com.aesopwow.subsubclipclop.domain.alarm.message;

import com.aesopwow.subsubclipclop.domain.alarm.dto.AlarmMessageDto;
import com.aesopwow.subsubclipclop.domain.alarm.repository.SseEmitterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(RedisSubscriber.class);
    private final ObjectMapper objectMapper;
    private final SseEmitterRepository sseEmitterRepository;

    // Redis에서 수신한 메시지 SseEmitter로 전송
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String data = new String(message.getBody());
//            System.out.println("[RedisSubscriber] 수신된 메시지: " + data);
            log.debug("수신된 메시지: {}", data);
            AlarmMessageDto alarmMessage = objectMapper.readValue(data, AlarmMessageDto.class);
//            System.out.println("[RedisSubscriber] 파싱된 메시지 - userNo: " + alarmMessage.getUserNo() + ", content: " + alarmMessage.getContent());
            log.debug("파싱된 메시지 - userNo: {}, content: {}", alarmMessage.getUserNo(), alarmMessage.getContent());
            sseEmitterRepository.get(alarmMessage.getUserNo()).ifPresent(emitter -> {
                try {
                    emitter.send(SseEmitter.event().name("alarm").data(alarmMessage.getContent()));
                } catch (Exception e) {
                    emitter.completeWithError(e);
                    sseEmitterRepository.remove(alarmMessage.getUserNo());
                }
            });
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("메시지 처리 중 오류 발생", e);
        }
    }
}
