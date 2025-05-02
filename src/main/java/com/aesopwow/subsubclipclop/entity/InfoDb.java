package com.aesopwow.subsubclipclop.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "info_db")
public class InfoDb extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_db_no")
    private Long infoDbNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no", nullable = false)
    private Company company;

    @Column(length = 20, nullable = false)
    private String name;
}
