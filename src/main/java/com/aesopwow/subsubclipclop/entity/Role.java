package com.aesopwow.subsubclipclop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_no")
    private Byte roleNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoleType name;

    public enum RoleType {
        ADMIN, CLIENT_ADMIN, CLIENT_USER, USER
    }
}

