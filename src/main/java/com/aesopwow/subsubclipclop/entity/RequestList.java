package com.aesopwow.subsubclipclop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "request_list")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RequestList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 숫자 자동 증가 설정 추가
    @Column(name = "request_list_no")
    private Long requestListNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_no", nullable = false)
    private Analysis analysis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "info_db_no", nullable = false)
    private InfoDb infoDb;
}
