package com.aesopwow.subsubclipclop.domain.info_db.service;

import com.aesopwow.subsubclipclop.domain.info_db.dto.InfoDbResponseDto;

public interface InfoDbService {
    InfoDbResponseDto getInfoDb(Long infoDbNo);
}
