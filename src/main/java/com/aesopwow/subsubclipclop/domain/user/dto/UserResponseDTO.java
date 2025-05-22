package com.aesopwow.subsubclipclop.domain.user.dto;

import com.aesopwow.subsubclipclop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO {
    private Long userNo;
    private String name;

    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(
                user.getUserNo(),
                user.getName()
        );
    }
}
