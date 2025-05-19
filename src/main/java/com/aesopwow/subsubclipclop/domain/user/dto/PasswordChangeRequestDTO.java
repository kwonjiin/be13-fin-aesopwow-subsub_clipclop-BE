package com.aesopwow.subsubclipclop.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangeRequestDTO {

    //MARK: - 비밀번호 변경
    private String oldPassword;
    private String newPassword;

}
