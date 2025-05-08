package com.aesopwow.subsubclipclop.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "info_db")
public class DbInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_db_no")
    private Long dbInfoNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no", nullable = false)
    private Company company;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "nickname", length = 20, nullable = false)
    private String nickname;

    @Column(name = "host", length = 50)
    private String host;

    @Column(name = "user", length = 50)
    private String user;

    @Column(name = "password", length = 50)
    private String password;

    public DbInfo(Long dbInfoNo) {
        this.dbInfoNo = dbInfoNo;
    }
}
