package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.company.dto.CompanyResponseDto;
import com.aesopwow.subsubclipclop.domain.info_db.repository.InfoDbRepository;
import com.aesopwow.subsubclipclop.entity.Company;
import com.aesopwow.subsubclipclop.entity.Payment;
import com.aesopwow.subsubclipclop.domain.common.dto.BaseResponseDto;
import com.aesopwow.subsubclipclop.domain.company.dto.CompanyUpdateRequestDTO;
import com.aesopwow.subsubclipclop.domain.company.service.CompanyService;
import com.aesopwow.subsubclipclop.domain.payment.repository.PaymentRepository;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
@Tag(name = "Company", description = "회사 관련 API")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private PaymentRepository paymentRepository;
    private InfoDbRepository infoDbRepository;

    @GetMapping("/{companyNo}")
    @Operation(summary = "회사 상세 조회", description = "회사 번호로 회사 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<BaseResponseDto<CompanyResponseDto>> getCompanyByNo(
            @Parameter(description = "회사 번호", example = "1")
            @PathVariable Long companyNo
    ) {
        Company company = companyService.getCompanyByNo(companyNo)
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));

        CompanyResponseDto companyResponseDto = new CompanyResponseDto(company);

        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, companyResponseDto));
    }

    @PutMapping("/{companyNo}/update")
    @Operation(summary = "회사 정보 수정", description = "회사 정보를 JSON으로 받아 수정한다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })

    public ResponseEntity<BaseResponseDto<Company>> updateCompany(
            @Parameter(description = "회사 번호", example = "1")
            @PathVariable("companyNo") Long companyNo,
            @Valid @RequestBody CompanyUpdateRequestDTO companyUpdateRequestDTO) {

        Company company = companyService.getCompanyByNo(companyNo)
                .orElseThrow(() -> new RuntimeException("회사를 찾을 수 없습니다."));

        if (companyUpdateRequestDTO.getCompanyName() != null) company.setName(companyUpdateRequestDTO.getCompanyName());
//        if (companyUpdateRequestDTO.getDepartmentName() != null) company.setDepartmentName(companyUpdateRequestDTO.getDepartmentName());

        if (companyUpdateRequestDTO.getPayment() != null) {
            Payment payment = paymentRepository.findById(companyUpdateRequestDTO.getPayment())
                    .orElseThrow(() -> new RuntimeException("결제 정보를 찾을 수 없습니다."));
            companyService.saveCompanyPayment(company, payment);
        }


//        if (companyUpdateRequestDTO.getInfoDb() != null) {
//            InfoDb infoDb = infoDbRepository.findById(Long.valueOf(companyUpdateRequestDTO.getInfoDb()))
//                    .orElseThrow(() -> new RuntimeException("DB 정보를 찾을 수 없습니다."));
//            companyService.saveCompanyDb(company, infoDb);
//        }

        companyService.save(company);

        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, company));
    }
}