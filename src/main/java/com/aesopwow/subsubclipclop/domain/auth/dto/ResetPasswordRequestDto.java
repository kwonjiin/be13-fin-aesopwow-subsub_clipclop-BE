package com.aesopwow.subsubclipclop.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDto {
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-={}\\[\\]:\";'<>?,./]).{8,}$", message = "비밀번호는 8자 이상이며, 영문자와 특수문자를 포함해야 합니다.")
    private String password;
    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String confirmPassword;
}
