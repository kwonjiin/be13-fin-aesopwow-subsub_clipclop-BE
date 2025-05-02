package com.aesopwow.subsubclipclop.domain.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    // 이메일 발송 관련 설정을 application.properties에서 가져옴
    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 이메일 보내기
    public void sendEmail(String recipientEmail, String subject, String text) throws MailException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // UTF-8 인코딩 설정

        helper.setFrom(senderEmail);
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(text, true); // HTML 형식으로 이메일 본문을 보낼 수 있음

        mailSender.send(message); // 이메일 전송
    }
}
