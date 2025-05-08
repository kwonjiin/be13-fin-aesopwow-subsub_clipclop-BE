package com.aesopwow.subsubclipclop.domain.request_list.service;

import com.aesopwow.subsubclipclop.domain.request_list.dto.RequireListRequestDto;
import com.aesopwow.subsubclipclop.domain.request_list.dto.RequireListResponseDto;
import com.aesopwow.subsubclipclop.domain.request_list.repository.RequireListRepository;
import com.aesopwow.subsubclipclop.entity.Analysis;
import com.aesopwow.subsubclipclop.entity.Company;
import com.aesopwow.subsubclipclop.entity.DbInfo;
import com.aesopwow.subsubclipclop.entity.RequestList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequireListServiceImpl implements RequireListService {
    private final RequireListRepository requireListRepository;

    @Override
    public RequireListResponseDto getRequireList(Long requireListNo) {
        RequestList requestList = requireListRepository.findById(requireListNo).get();

        return new RequireListResponseDto(requestList);
    }

    @Override
    public RequireListResponseDto createRequireList(RequireListRequestDto requireListRequestDto) {
        RequestList requestList = RequestList.builder()
                .analysis(new Analysis(requireListRequestDto.getAnalysis_no().byteValue()))
                .company(new Company(requireListRequestDto.getCompany_no()))
                .dbInfo(new DbInfo(requireListRequestDto.getDbInfoNo()))
                .build();

        requireListRepository.save(requestList);

        return new RequireListResponseDto(requestList);
    }
}
