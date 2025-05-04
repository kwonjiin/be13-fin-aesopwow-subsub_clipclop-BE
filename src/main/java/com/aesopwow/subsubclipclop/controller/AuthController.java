package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.auth.dto.CheckEmailDTO;
import com.aesopwow.subsubclipclop.domain.auth.dto.LoginRequestDTO;
import com.aesopwow.subsubclipclop.domain.auth.dto.LoginResponseDTO;
import com.aesopwow.subsubclipclop.domain.auth.dto.OtpVerificationRequestDTO;
import com.aesopwow.subsubclipclop.domain.auth.dto.SignUpRequestDTO;
import com.aesopwow.subsubclipclop.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증 관련 API", description = "인증 관련 API 엔드포인트 모음")  // Swagger에서 API 그룹화
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 입력하여 로그인하고, JWT 토큰을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원가입 - OTP 요청", description = "이메일과 비밀번호를 입력하여 OTP 인증을 요청합니다. 이메일로 6자리 OTP를 전송합니다.")
    @PostMapping("/signup/otp")
    public ResponseEntity<String> requestOtp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        try {
            authService.sendOtp(signUpRequestDTO.getEmail(), signUpRequestDTO.getPassword());
            // 이메일과 비밀번호 모두 전달
            authService.sendOtp(signUpRequestDTO.getEmail(), signUpRequestDTO.getPassword());
            return ResponseEntity.ok("OTP가 이메일로 전송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(summary = "OTP 인증", description = "이메일로 전송된 OTP를 입력하여 인증을 진행합니다.")
    @PostMapping("/signup/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequestDTO otpVerificationRequestDTO) {
        try {
            // OTP 인증 확인
            authService.verifyOtp(otpVerificationRequestDTO.getEmail(), otpVerificationRequestDTO.getOtp());
            return ResponseEntity.ok("OTP 인증 성공!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "회원가입 - 최종 인증", description = "OTP 인증이 완료된 후, 비밀번호를 포함한 회원가입을 완료하고 JWT 토큰을 발급받습니다.")
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        try {
            // 회원가입 로직 실행 (OTP 인증 이후에 진행)
            authService.signUp(signUpRequestDTO);
            // JWT 토큰 발급 후 회원가입 성공 메시지 반환
            return ResponseEntity.ok("회원가입 성공!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "회원가입 - 이메일 중복 확인", description = "입력한 이메일이 중복되었는지 확인합니다.")
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
}
