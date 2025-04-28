package com.aesopwow.subsubclipclop.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "request")
public class Request extends BaseEntity {
    @Id
    @Column(name = "request_no")
    private Long requestNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_no", nullable = false)
    private Analysis analysis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no", nullable = false)
    private Company company;
}
