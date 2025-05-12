package com.aesopwow.subsubclipclop.domain.common.enums;

public enum PlanType {
    BASIC(5), PREMIUM(10), ULTIMATE(20);

    private final int maxStaff;

    PlanType(int maxStaff) {
        this.maxStaff = maxStaff;
    }

    public int getMaxStaff() {
        return maxStaff;
    }
}