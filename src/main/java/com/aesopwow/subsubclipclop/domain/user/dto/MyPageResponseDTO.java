package com.aesopwow.subsubclipclop.domain.user.dto;

import com.aesopwow.subsubclipclop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MyPageResponseDTO {
    private Long userNo;
    private String membershipName;
    private LocalDateTime membershipExpiredAt;
    private String username;
    private String companyName;

    public MyPageResponseDTO(User user) {
        this.userNo = user.getUserNo();
        this.membershipName = user.getCompany().getMembership().getName();
        this.membershipExpiredAt = user.getCompany().getMembershipExpiredAt();
        this.username = user.getName();
        this.companyName = user.getCompany().getName();
    }
}
