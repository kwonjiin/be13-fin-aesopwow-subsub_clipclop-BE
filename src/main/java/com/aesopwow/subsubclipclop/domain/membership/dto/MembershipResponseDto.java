package com.aesopwow.subsubclipclop.domain.membership.dto;

import com.aesopwow.subsubclipclop.entity.Membership;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipResponseDto {
    private Byte membershipNo;
    private String name;
    private String description;
    private Integer price;
    private boolean status;
    private Byte duration;
    private Byte maxPerson;

    public MembershipResponseDto(Membership membership) {
        this.membershipNo = membership.getMembershipNo();
        this.name = membership.getName();
        this.description = membership.getDescription();
        this.price = membership.getPrice();
        this.status = membership.getStatus();
        this.duration = membership.getDuration();
        this.maxPerson = membership.getMaxPerson();
    }
}
