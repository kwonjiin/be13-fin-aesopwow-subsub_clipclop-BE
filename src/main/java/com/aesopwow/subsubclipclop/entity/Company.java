package com.aesopwow.subsubclipclop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "company")
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_no")
    private Long companyNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_no", nullable = false)
    private Membership membership;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(name = "registration_number", length = 20, nullable = false)
    private String registrationNumber;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "is_subscribed", nullable = false)
    @Builder.Default
    private Boolean isSubscribed = false;

    @Column(name = "membership_started_at")
    private LocalDateTime membershipStartedAt;

    @Column(name = "membership_expired_at")
    private LocalDateTime membershipExpiredAt;

    public Company(Long companyNo) {
        this.companyNo = companyNo;
    }
}
