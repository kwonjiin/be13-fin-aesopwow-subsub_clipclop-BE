package com.aesopwow.subsubclipclop.domain.request_list.service;

import com.aesopwow.subsubclipclop.domain.request_list.dto.Request_listRequestDto;
import com.aesopwow.subsubclipclop.domain.request_list.dto.Request_listResponseDto;
import com.aesopwow.subsubclipclop.domain.request_list.repository.Request_listRepository;
import com.aesopwow.subsubclipclop.entity.Analysis;
import com.aesopwow.subsubclipclop.entity.Company;
import com.aesopwow.subsubclipclop.entity.DbInfo;
import com.aesopwow.subsubclipclop.entity.RequestList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Request_listServiceImpl implements Request_listService{
    private final Request_listRepository request_listRepository;

    @Override
    public Request_listResponseDto getRequest_list(Long request_list_no) {
        RequestList requestList = request_listRepository.findById(request_list_no).get();

        Request_listResponseDto request_listResponseDto
                = new Request_listResponseDto(requestList);

        return request_listResponseDto;
    }

    @Override
    public Request_listResponseDto createRequest_list(Request_listRequestDto requestDto) {
        RequestList requestList = RequestList.builder()
                .analysis(new Analysis(requestDto.getAnalysis_no().byteValue()))
                .company(new Company(requestDto.getCompany_no()))
                .dbInfo(new DbInfo(requestDto.getInfo_db_no()))
                .build();

        request_listRepository.save(requestList);

        return new Request_listResponseDto(requestList);
    }
}
