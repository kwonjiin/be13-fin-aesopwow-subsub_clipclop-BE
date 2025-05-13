package com.aesopwow.subsubclipclop.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpRequestDto {
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일을 입력해주세요.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private final String password;

    @NotBlank(message = "이름은 필수입니다.")
    private final String name;
}
