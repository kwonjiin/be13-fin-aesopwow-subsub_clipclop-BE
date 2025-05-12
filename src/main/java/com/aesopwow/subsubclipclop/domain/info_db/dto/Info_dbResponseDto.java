package com.aesopwow.subsubclipclop.domain.info_db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info_dbResponseDto {
    private Long info_db_no;
    private Long company_no;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
