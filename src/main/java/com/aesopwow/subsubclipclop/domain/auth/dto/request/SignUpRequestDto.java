package com.aesopwow.subsubclipclop.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpRequestDto {
    @NotBlank
    @Email
    private final String username;

    @NotBlank
    private final String password;
}
