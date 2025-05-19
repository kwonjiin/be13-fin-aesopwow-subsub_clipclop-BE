package com.aesopwow.subsubclipclop.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffCreateRequestDTO {

    //MARK: - 직원 추가
    private String userEmail;
}
