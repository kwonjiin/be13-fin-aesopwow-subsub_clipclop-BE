package com.aesopwow.subsubclipclop.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "현재 비밀번호는 필수 항목입니다")
    private String oldPassword;
    @NotBlank(message = "새 비밀번호는 필수 항목입니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호는 최소 8자 이상, 하나의 문자, 숫자, 특수문자를 포함해야 합니다")
    private String newPassword;

}
