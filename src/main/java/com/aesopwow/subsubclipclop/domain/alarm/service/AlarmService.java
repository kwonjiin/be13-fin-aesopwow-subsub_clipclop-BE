package com.aesopwow.subsubclipclop.domain.alarm.service;

import com.aesopwow.subsubclipclop.domain.alarm.dto.AlarmMessageDto;
import com.aesopwow.subsubclipclop.domain.alarm.dto.AlarmResponseDto;
import com.aesopwow.subsubclipclop.domain.alarm.message.RedisPublisher;
import com.aesopwow.subsubclipclop.domain.alarm.repository.AlarmRepository;
import com.aesopwow.subsubclipclop.domain.user.repository.UserRepository;
import com.aesopwow.subsubclipclop.entity.Alarm;
import com.aesopwow.subsubclipclop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final RedisPublisher redisPublisher;
    private final ChannelTopic topic;
    private final UserRepository userRepository; // 알림대상 유저 조회

    public void sendAlarm(Long userNo, String content) {
        User user = userRepository.findById(userNo).orElseThrow();
        Alarm alarm = Alarm.builder()
                .user(user)
                .content(content)
                .build();
        alarmRepository.save(alarm);

        AlarmMessageDto message = new AlarmMessageDto(userNo, content);
        redisPublisher.publish(topic.getTopic(), message);
    }

    // 알림 읽음처리
    public void markAsRead(Long alarmNo) {
        Alarm alarm = alarmRepository.findById(alarmNo).orElseThrow();
        alarm.markAsRead();
        alarmRepository.save(alarm);
    }

    public boolean hasUnread(Long userNo) {
        return alarmRepository.existsByUserUserNoAndIsReadFalse(userNo);
    }

    public List<AlarmResponseDto> getAlarmsByUser(Long userNo) {
        List<Alarm> alarms = alarmRepository.findByUserUserNoOrderByCreatedAtDesc(userNo);
        return alarms.stream()
                .map(AlarmResponseDto::from)
                .collect(Collectors.toList());
    }

    public void deleteAlarm(Long alarmNo) {
        Alarm alarm = alarmRepository.findById(alarmNo).orElseThrow();
        alarmRepository.delete(alarm);
    }
}
