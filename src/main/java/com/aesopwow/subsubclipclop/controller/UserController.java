package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.common.dto.BaseResponseDto;
import com.aesopwow.subsubclipclop.domain.user.dto.PasswordChangeRequestDTO;
import com.aesopwow.subsubclipclop.domain.user.dto.UserDeleteRequestDto;
import com.aesopwow.subsubclipclop.domain.user.dto.UserResponseDTO;
import com.aesopwow.subsubclipclop.domain.user.dto.UserUpdateRequestDTO;
import com.aesopwow.subsubclipclop.domain.user.service.UserService;
import com.aesopwow.subsubclipclop.entity.CustomUserDetails;
import com.aesopwow.subsubclipclop.entity.Role;
import com.aesopwow.subsubclipclop.entity.User;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "유저 관련 API")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 직원 추가
    @PostMapping("/staffs/add")
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
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam @Valid @Email String userEmail
    ) {
        User adminUser = customUserDetails.getUser();

        if (!Role.RoleType.CLIENT_ADMIN.equals(adminUser.getRole().getName())) {
            throw new CustomException(ErrorCode.ONLY_CLIENT_ADMIN_ALLOWED);
        }

        userService.addStaff(adminUser.getUserNo(), userEmail);

        return ResponseEntity.ok(
                new BaseResponseDto<>(HttpStatus.OK, "직원이 성공적으로 추가되었습니다.")
        );
    }

    // 직원정보 조회
    @GetMapping("/staffs/list")
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
    public ResponseEntity<BaseResponseDto<List<UserResponseDTO>>> getStaffList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        User user = customUserDetails.getUser();
        if (user.getRole().getName() == Role.RoleType.CLIENT_ADMIN) {
            List<User> staffList = userService.getStaffList(user.getUserNo());

            List<UserResponseDTO> result = staffList.stream()
                    .map(UserResponseDTO::from)
                    .toList();

            return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, result));
        } else {
            throw new CustomException(ErrorCode.ONLY_CLIENT_ADMIN_ALLOWED);
        }
    }

    // 직원정보 삭제
    @DeleteMapping("/staffs/{userNo}/delete")
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
    public ResponseEntity<BaseResponseDto<String>> deleteStaff(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long userNo) {
        User adminUser = customUserDetails.getUser();

        if (!Role.RoleType.CLIENT_ADMIN.equals(adminUser.getRole().getName())) {
            throw new CustomException(ErrorCode.ONLY_CLIENT_ADMIN_ALLOWED);
        }
        userService.deleteStaff(userNo);
        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, "직원이 삭제되었습니다."));
    }

    // 유저 정보 조회
    @GetMapping("/my")
    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자의 정보를 조회합니다.")
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
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<BaseResponseDto<UserResponseDTO>> getMyInfo(
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userNo = customUserDetails.getUser().getUserNo();

        User user = userService.getOneUserByUserNo(userNo);

        UserResponseDTO userResponseDTO = UserResponseDTO.from(user);

        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, userResponseDTO));
    }

    // 유저 정보 수정
    @PutMapping("/my/update")
    @Operation(summary = "내 정보 수정", description = "현재 로그인한 사용자의 개인정보를 수정합니다.")
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
                    responseCode = "401",
                    description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<BaseResponseDto<String>> updateMyInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody UserUpdateRequestDTO userUpdateRequestDTO
    ) {
        Long loginUserNo = customUserDetails.getUser().getUserNo();

        userService.updateUser(loginUserNo, userUpdateRequestDTO);

        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, "내 정보가 수정되었습니다."));
    }

    // 비밀번호 변경
    @PutMapping("/my/password")
    @Operation(summary = "비밀번호 변경", description = "기존 비밀번호를 확인한 후 새 비밀번호로 변경합니다.")
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
                    responseCode = "401",
                    description = "UNAUTHORIZED - 기존 비밀번호 불일치",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<BaseResponseDto<String>> changePassword(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid PasswordChangeRequestDTO passwordChangeRequestDTO
    ) {
        Long loginUserNo = customUserDetails.getUser().getUserNo();
        userService.changePassword(loginUserNo, passwordChangeRequestDTO);

        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, "비밀번호가 성공적으로 변경되었습니다."));
    }

    // 회원 탈퇴
    @PostMapping("/my/withdrawal")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 정보를 관리합니다.")
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
    public ResponseEntity<BaseResponseDto<String>> updateUserIs_deleted (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid UserDeleteRequestDto userDeleteRequestDto
    ) {
        Long loginUserNo = customUserDetails.getUser().getUserNo();
        userService.updateUserIs_deleted(loginUserNo, userDeleteRequestDto);

        return ResponseEntity.ok(
                new BaseResponseDto<>(HttpStatus.OK, "정상적으로 탈퇴 처리되었습니다.")
        );
    }
}


//    @GetMapping("")
//    public ResponseEntity<UserResponseDTO> getOneUser (
//            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
//
//        User user = customUserDetails.getUser();
//
//        UserResponseDTO userResponseDTO = UserResponseDTO.from(user);
//
//        return ResponseEntity.ok(userResponseDTO);
//    }

//    @GetMapping("")
//    public ResponseEntity<UserResponseDTO> getOneUserByUserNo (@RequestParam Long userNo) {
//        User user = userService.getOneUserByUserNo(userNo);
//
//        UserResponseDTO userResponseDTO = UserResponseDTO.from(user);
//
//        return ResponseEntity.ok(userResponseDTO);
//    }

//    @GetMapping("/basic-info/{userNo}")
//    @Operation(summary = "기본 사용자 정보 조회", description = "userNo로 companyNo, infoDbNo, roleNo를 조회합니다.")
//    public ResponseEntity<BaseResponseDto<UserResponseDTO>> getBasicInfo(@PathVariable @Valid Long userNo) {
//        User user = userService.getOneUserByUserNo(userNo);
//
//        UserResponseDTO userResponseDTO = UserResponseDTO.from(user);
//
//        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, userResponseDTO));
//    }
