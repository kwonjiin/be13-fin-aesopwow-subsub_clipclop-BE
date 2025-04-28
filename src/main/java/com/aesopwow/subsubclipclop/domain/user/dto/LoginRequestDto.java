package com.aesopwow.subsubclipclop.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String password;
}
