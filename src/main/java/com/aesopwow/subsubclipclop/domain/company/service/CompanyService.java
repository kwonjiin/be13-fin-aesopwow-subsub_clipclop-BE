package com.aesopwow.subsubclipclop.domain.company.service;

import com.aesopwow.subsubclipclop.entity.Company;
import com.aesopwow.subsubclipclop.entity.InfoDb;
import com.aesopwow.subsubclipclop.entity.Payment;
import com.aesopwow.subsubclipclop.domain.company.dto.CompanyUpdateRequestDTO;

import java.util.Optional;

public interface CompanyService {
    void updateCompanyInfo(Long companyNo, CompanyUpdateRequestDTO companyUpdateRequestDTO);

    Optional<Company> getCompanyByNo(Long companyNo);

    void save(Company company);

    void saveCompanyPayment(Company companyNo, Payment payment);

    void saveCompanyDb(Company companyNo, InfoDb infoDb);
}
