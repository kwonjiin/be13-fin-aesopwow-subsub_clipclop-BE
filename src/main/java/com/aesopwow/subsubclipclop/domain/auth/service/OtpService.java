package com.aesopwow.subsubclipclop.domain.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import java.util.Random;

@Service
public class OtpService {

    private final RedisService redisService;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public OtpService(RedisService redisService, EmailService emailService) {
        this.redisService = redisService;
        this.emailService = emailService;
    }

    // OTP 생성 및 이메일 전송
    public void sendOtpToEmail(String email) throws MessagingException {
        String otp = generateOtp(); // 6자리 OTP 생성
        redisService.saveOtp(email, otp); // Redis에 OTP 저장 (3분 유효)

        // 이메일 전송
        String subject = "OTP 인증번호";
        String text = "귀하의 OTP 인증번호는 " + otp + "입니다. 3분 이내에 입력해주세요.";

        emailService.sendEmail(email, subject, text); // 이메일 발송
    }

    // 6자리 OTP 생성
    private String generateOtp() {
        Random rand = new Random();
        String otp = String.format("%06d", rand.nextInt(1000000)); // 6자리 OTP
        return otp;
    }
}
