package com.aesopwow.subsubclipclop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "require_list")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RequireList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 숫자 자동 증가 설정 추가
    @Column(name = "require_list_no")
    private Long requireListNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_no", nullable = false)
    private Analysis analysis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "info_db_no", nullable = false)
    private InfoDb infoDb;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Builder.Default
    private RequireList.RequireListStatus status = RequireList.RequireListStatus.PENDING;

    public enum RequireListStatus {
        SUCCESS, FAIL, PENDING
    }
}
