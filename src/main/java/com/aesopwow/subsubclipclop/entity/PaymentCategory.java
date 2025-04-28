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
@Table(name = "payment_category")
public class PaymentCategory {
    @Id
    @Column(name = "payment_category_no")
    private Byte paymentCategoryNo;

    @Column(length = 20, nullable = false)
    private String name;
}

