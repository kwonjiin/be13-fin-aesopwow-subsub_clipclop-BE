package com.aesopwow.subsubclipclop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "membership")
public class Membership extends BaseEntity {
    @Id
    @Column(name = "membership_no")
    private Byte membershipNo;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Byte duration;
}


