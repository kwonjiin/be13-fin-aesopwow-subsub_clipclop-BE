package com.aesopwow.subsubclipclop.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordOtpVerificationDto {
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유요한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "OTP는 필수입니다.")
    @Pattern(regexp = "^\\d{6}$", message = "OTP는 6자리 숫자여야 합니다")
    private String otp;
}
