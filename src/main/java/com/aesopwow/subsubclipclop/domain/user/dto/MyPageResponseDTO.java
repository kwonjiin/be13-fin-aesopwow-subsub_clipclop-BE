package com.aesopwow.subsubclipclop.domain.user.dto;

import com.aesopwow.subsubclipclop.entity.Membership;
import com.aesopwow.subsubclipclop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MyPageResponseDTO {
    private final Long userNo;
    private final Membership membership;
    private LocalDateTime membershipExpiredAt;
    private final String name;
    private final String departmentName;

    public MyPageResponseDTO(User user) {
        this.userNo = user.getUserNo();
        this.membership = user.getCompany().getMembership();
        this.membershipExpiredAt = user.getCompany().getMembershipExpiredAt();
        this.name = user.getName();
        this.departmentName = user.getDepartmentName();
    }
}