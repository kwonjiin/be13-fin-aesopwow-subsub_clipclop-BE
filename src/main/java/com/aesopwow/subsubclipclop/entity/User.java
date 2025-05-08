package com.aesopwow.subsubclipclop.entity;

import com.aesopwow.subsubclipclop.domain.common.enums.PlanType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    @Column(length = 20, nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_no", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no", nullable = true)
    private Company company;

    @Column(name = "logined_at", nullable = false)
    private LocalDateTime loginedAt;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;


    private String departmentName; // 없어서 일단 임시로 추가

    @Enumerated(EnumType.STRING)
    private PlanType planType; // 플랜 타입 ENUM 추가

    @Column(nullable = false)
    private String email;
}

