package com.aesopwow.subsubclipclop.domain.info_db.service;

import com.aesopwow.subsubclipclop.domain.info_db.dto.InfoDbResponseDto;
import com.aesopwow.subsubclipclop.domain.info_db.repository.InfoDbRepository;
import com.aesopwow.subsubclipclop.entity.InfoDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoDbServiceImpl implements InfoDbService {

    private final InfoDbRepository infoDbRepository;

    @Override
    public InfoDbResponseDto getInfoDb(Long infoDbNo) {

        InfoDb infoDb = infoDbRepository.findById(infoDbNo).get();


        InfoDbResponseDto infoDbResponseDto = new InfoDbResponseDto();

        infoDbResponseDto.setInfoDbNo(infoDb.getInfoDbNo());
        infoDbResponseDto.setCompanyNo(infoDb.getCompany().getCompanyNo());
        infoDbResponseDto.setName(infoDb.getName());
        infoDbResponseDto.setCreatedAt(infoDb.getCreatedAt());
        infoDbResponseDto.setUpdatedAt(infoDb.getUpdatedAt());

        return infoDbResponseDto;
    }
}
