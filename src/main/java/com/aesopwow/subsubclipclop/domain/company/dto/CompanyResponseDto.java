package com.aesopwow.subsubclipclop.domain.company.dto;

import com.aesopwow.subsubclipclop.entity.Company;
import com.aesopwow.subsubclipclop.entity.Membership;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CompanyResponseDto {
    private Long companyNo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String email;
    private boolean isDeleted;
    private boolean isSubscribed;
    private LocalDateTime membershipExpiredAt;
    private LocalDateTime membershipStartedAt;
    private String name;
    private String phone;
    private String registrationNumber;
//    private Membership membership;
    private String departmentName;

    public CompanyResponseDto(Company company) {
        this.companyNo = company.getCompanyNo();
        this.createdAt = company.getCreatedAt();
        this.updatedAt = company.getUpdatedAt();
        this.email = company.getEmail();
        this.isDeleted = company.getIsDeleted();
        this.isSubscribed = company.getIsSubscribed();
        this.membershipExpiredAt = company.getMembershipExpiredAt();
        this.membershipStartedAt = company.getMembershipStartedAt();
        this.name = company.getName();
        this.phone = company.getPhone();
        this.registrationNumber = company.getRegistrationNumber();
//        this.membership = company.getMembership();
        this.departmentName = company.getDepartmentName();
    }
}
