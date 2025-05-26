package com.aesopwow.subsubclipclop.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDto {
    private String email;
    private String password;
    private String confirmPassword;
}
