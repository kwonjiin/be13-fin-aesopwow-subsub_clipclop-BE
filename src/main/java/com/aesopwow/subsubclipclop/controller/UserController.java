package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.common.dto.BaseResponseDto;
import com.aesopwow.subsubclipclop.domain.user.dto.UserResponseDTO;
import com.aesopwow.subsubclipclop.domain.user.dto.UserUpdateRequestDTO;
import com.aesopwow.subsubclipclop.domain.user.service.UserService;
import com.aesopwow.subsubclipclop.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "유저 관련 API19")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 직원 추가
    @PostMapping("/staffs")
    @Operation(summary = "직원 추가", description = "관리자 계정으로 직원을 추가합니다.")
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
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<BaseResponseDto<String>> addStaff(
            @RequestParam @Valid Long adminUserNo,
            @RequestParam @Valid @Email String staffEmail
    ) {
        userService.addStaff(adminUserNo, staffEmail);

        return ResponseEntity.ok(
                new BaseResponseDto<>(HttpStatus.OK, "직원이 성공적으로 추가되었습니다.")
        );
    }


    // 직원정보 조회
    @GetMapping("/staffs")
    @Operation(summary = "직원 정보 조회", description = "관리자 계정으로 직원을 조회합니다.")
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
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<BaseResponseDto<List<UserResponseDTO>>> getStaffList(@RequestParam @Valid Long adminUserNo) {
            List<User> staffList = userService.getStaffList(adminUserNo);

            List<UserResponseDTO> result = staffList.stream()
                    .map(UserResponseDTO::from)
                    .toList();

        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, result));
    }


    // 직원정보 수정
    @PutMapping("/staffs/{userNo}")
    @Operation(summary = "직원 정보 수정", description = "관리자 계정으로 직원이름, 부서명, 비밀번호를 수정합니다.")
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
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<BaseResponseDto<String>> updateUser(
            @PathVariable Long userNo,
            @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {

        userService.updateUser(userNo, userUpdateRequestDTO);

        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, "직원 정보가 수정되었습니다."));
    }

    // 직원정보 삭제
    @DeleteMapping("/staffs/{userNo}")
    @Operation(summary = "직원 삭제", description = "관리자 계정으로 직원을 삭제합니다.")
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
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<BaseResponseDto<String>> deleteStaff(@PathVariable Long userNo) {
        userService.deleteStaff(userNo);
        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, "직원이 삭제되었습니다."));
    }
}