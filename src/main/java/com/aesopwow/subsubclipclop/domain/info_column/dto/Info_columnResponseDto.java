package com.aesopwow.subsubclipclop.domain.info_column.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info_columnResponseDto {
    private Long info_column_no;
    private Long info_db_no;
    private String analysis_column;
    private String origin_column;
    private String origin_table;
    private String note;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
