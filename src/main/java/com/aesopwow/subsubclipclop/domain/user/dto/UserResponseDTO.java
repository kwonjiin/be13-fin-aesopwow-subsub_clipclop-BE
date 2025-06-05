package com.aesopwow.subsubclipclop.domain.user.dto;

import com.aesopwow.subsubclipclop.entity.Role;
import com.aesopwow.subsubclipclop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDTO {
    private Long userNo;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String email;
    private Long companyNo;
    private String companyName;
    private String departmentName;
    private Long infoDbNo;
    private Byte roleNo;
    private Role.RoleType roleName;

    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(
                user.getUserNo(),
                user.getName(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getCompany().getCompanyNo(),
                user.getCompany().getName(),
                user.getDepartmentName(),
                user.getInfoDb().getInfoDbNo(),
                user.getRole().getRoleNo(),
                user.getRole().getName()
        );
    }
}
