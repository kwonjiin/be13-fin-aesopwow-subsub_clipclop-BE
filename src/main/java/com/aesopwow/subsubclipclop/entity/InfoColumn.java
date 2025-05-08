package com.aesopwow.subsubclipclop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "info_column")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoColumn extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_column_no")
    private Long infoColumnNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "info_db_no", nullable = false)
    private DbInfo dbInfo;

    @Column(name = "analysis_column", length = 20, nullable = false)
    private String analysisColumn;

    @Column(name = "origin_table", length = 20, nullable = false)
    private String originTable;

    @Column(name = "origin_column", length = 20, nullable = false)
    private String originColumn;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;
}

