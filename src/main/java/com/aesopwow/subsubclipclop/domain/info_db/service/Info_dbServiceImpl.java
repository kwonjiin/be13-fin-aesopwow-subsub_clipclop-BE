package com.aesopwow.subsubclipclop.domain.info_db.service;

import com.aesopwow.subsubclipclop.domain.info_db.dto.Info_dbResponseDto;
import com.aesopwow.subsubclipclop.domain.info_db.repository.Info_dbRepository;
import com.aesopwow.subsubclipclop.entity.InfoDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Info_dbServiceImpl implements Info_dbService{

    private final Info_dbRepository info_dbRepository;

    @Override
    public Info_dbResponseDto getInfo_db(Long info_db_no) {

        InfoDb infoDb = info_dbRepository.findById(info_db_no).get();


        Info_dbResponseDto info_dbResponseDto = new Info_dbResponseDto();

        info_dbResponseDto.setInfo_db_no(infoDb.getInfoDbNo());
        info_dbResponseDto.setCompany_no(infoDb.getCompany().getCompanyNo());
        info_dbResponseDto.setName(infoDb.getName());
        info_dbResponseDto.setCreated_at(infoDb.getCreatedAt());
        info_dbResponseDto.setUpdated_at(infoDb.getUpdatedAt());

        return info_dbResponseDto;
    }
}
