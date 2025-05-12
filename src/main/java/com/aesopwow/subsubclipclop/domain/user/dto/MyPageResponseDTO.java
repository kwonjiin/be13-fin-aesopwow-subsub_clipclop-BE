package com.aesopwow.subsubclipclop.domain.user.dto;

import com.aesopwow.subsubclipclop.domain.membership.dto.MembershipResponseDto;
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
    private Long userNo;
    private MembershipResponseDto membership;
    private LocalDateTime membershipExpiredAt;
    private String name;
    private String departmentName;

    public MyPageResponseDTO(User user) {
        this.userNo = user.getUserNo();
        Membership membership = user.getCompany().getMembership();
        this.membership = MembershipResponseDto.builder()
                .membershipNo(membership.getMembershipNo())
                .name(membership.getName())
                .description(membership.getDescription())
                .price(membership.getPrice())
                .status(membership.getStatus())
                .duration(membership.getDuration())
                .maxPerson(membership.getMaxPerson())
                .build();
        this.membershipExpiredAt = user.getCompany().getMembershipExpiredAt();
        this.name = user.getName();
        this.departmentName = user.getDepartmentName();
    }
}