package com.aesopwow.subsubclipclop.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    // 데이터 저장 (일반적인 set 메서드)
    public void setData(String key, String value, long duration, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, duration, unit);
    }

    // 데이터 조회 (일반적인 get 메서드)
    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 데이터 삭제
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    // OTP 저장 (key: OTP:이메일, value: OTP값, 3분 유효)
    public void saveOtp(String email, String otp) {
        String key = "OTP:" + email; // Redis에 저장할 키
        long expirationTime = 3; // 3분
        redisTemplate.opsForValue().set(key, otp, expirationTime, TimeUnit.MINUTES);
    }

    // OTP 검증 (입력된 OTP와 Redis에서 저장된 OTP 비교)
    public boolean verifyOtp(String email, String otp) {
        String key = "OTP:" + email;
        String savedOtp = redisTemplate.opsForValue().get(key);

        // Redis에 저장된 OTP가 null이 아니고 입력된 OTP와 일치하는지 확인
        return savedOtp != null && savedOtp.equals(otp);
    }
}
