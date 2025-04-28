package com.aesopwow.subsubclipclop.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class SignInRequestDto {
    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String password;
}
