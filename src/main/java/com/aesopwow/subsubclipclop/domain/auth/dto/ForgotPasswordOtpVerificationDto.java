package com.aesopwow.subsubclipclop.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordOtpVerificationDto {
    private String email;
    private String otp;
}
