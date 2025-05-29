package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.auth.dto.*;
import com.aesopwow.subsubclipclop.domain.auth.dto.request.SendOTPRequestDto;
import com.aesopwow.subsubclipclop.domain.auth.dto.request.SignUpRequestDto;
import com.aesopwow.subsubclipclop.domain.auth.dto.response.TokenResponseDto;
import com.aesopwow.subsubclipclop.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173")  // ✅ CORS 허용
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증 관련 API", description = "인증 관련 API 엔드포인트 모음")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 입력하여 로그인하고, JWT 토큰을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDTO request) {
        TokenResponseDto tokenResponseDto = authService.login(request);

        return ResponseEntity.ok(tokenResponseDto);
    }

    @Operation(summary = "로그아웃", description = "Redis에 담겨있는 정보들을 삭제한다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String bearerToken) {
        authService.logout(bearerToken);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원가입 - OTP 요청", description = "이메일과 비밀번호를 입력하여 OTP 인증을 요청합니다.")
    @PostMapping("/signup/otp")
    public ResponseEntity<String> requestOtp(@Valid @RequestBody SignUpRequestDto request) {
        try {
            authService.sendOtp(request.getEmail(), request.getPassword());
            return ResponseEntity.ok("OTP가 이메일로 전송되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다.");
        }
    }

    @Operation(summary = "OTP 인증", description = "이메일로 전송된 OTP를 입력하여 인증을 진행합니다.")
    @PostMapping("/signup/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequestDTO request) {
        try {
            authService.verifyOtp(request.getEmail(), request.getOtp());
            return ResponseEntity.ok("OTP 인증 성공!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "OTP 재전송", description = "OTP 메일을 다시 전송합니다.")
    @PostMapping("/signup/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestBody SendOTPRequestDto request) {
        try {
            authService.resendOtp(request.getEmail());
            return ResponseEntity.ok("OTP가 재전송되었습니다.");
        } catch (MessagingException e) {
            return ResponseEntity.internalServerError().body("OTP 이메일 전송에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다.");
        }
    }

    @Operation(summary = "회원가입 - 최종 인증", description = "OTP 인증 후 비밀번호 포함 최종 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto request) {
        try {
            authService.signUp(request);
            return ResponseEntity.ok("회원가입 성공!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "이메일 중복 확인", description = "입력한 이메일이 중복되었는지 확인")
    @PostMapping("/email-check")
    public ResponseEntity<String> checkEmailDuplicate(@RequestBody CheckEmailDTO request) {
        boolean isDuplicate = authService.isEmailDuplicate(request.getEmail());
        if (isDuplicate) {
            return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다.");
        } else {
            authService.markEmailAsChecked(request.getEmail());
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        }
    }
    @Operation(summary = "비밀번호 찾기 - OTP 요청", description = "비밀번호 재설정 OTP를 이메일로 전송")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> sendPasswordResetOtp(@RequestBody ForgotPasswordRequestDto request) {
        try {
            authService.sendPasswordResetOtp(request.getEmail());
            return ResponseEntity.ok("비밀번호 재설정 OTP가 이메일로 전송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "비밀번호 찾기 - OTP 인증", description = "이메일로 전송된 OTP 인증")
    @PostMapping("/forgot-password/verify-otp")
    public ResponseEntity<String> verifyPasswordResetOtp(@RequestBody ForgotPasswordOtpVerificationDto request) {
        try {
            authService.verifyPasswordResetOtp(request.getEmail(), request.getOtp());
            return ResponseEntity.ok("OTP 인증 성공!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경")
    @PutMapping("/forgot-password/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDto request) {
        try {
            authService.resetPassword(request);
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
