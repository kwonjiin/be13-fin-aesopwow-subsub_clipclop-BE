package com.aesopwow.subsubclipclop.domain.alarm.repository;

import com.aesopwow.subsubclipclop.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    boolean existsByUserUserNoAndIsReadFalse(Long userNo); // 안 읽은 알림 존재 여부
    List<Alarm> findByUserUserNoOrderByCreatedAtDesc(Long userNo); //알림 전체 오름차순
}
