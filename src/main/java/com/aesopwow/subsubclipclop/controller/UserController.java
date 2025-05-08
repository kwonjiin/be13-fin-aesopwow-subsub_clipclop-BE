package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.common.dto.BaseResponseDto;
import com.aesopwow.subsubclipclop.domain.user.dto.UserResponseDTO;
import com.aesopwow.subsubclipclop.domain.user.dto.UserUpdateRequestDTO;
import com.aesopwow.subsubclipclop.domain.user.service.UserService;
import com.aesopwow.subsubclipclop.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "유저 관련 API")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 직원 추가
    @PostMapping("/api/staffs")
    public ResponseEntity<BaseResponseDto<String>> addStaff(
            @RequestParam Long adminUserNo,
            @RequestParam String staffEmail
    ) {
        userService.addStaff(adminUserNo, staffEmail);

        return ResponseEntity.ok(
                new BaseResponseDto<>(HttpStatus.OK, "직원이 성공적으로 추가되었습니다.")
        );
    }


    // 직원정보 조회
    @GetMapping("/api/staffs")
    public ResponseEntity<List<UserResponseDTO>> getStaffList(@RequestParam Long adminUserNo) {
        List<User> staffList = userService.getStaffList(adminUserNo);

        List<UserResponseDTO> result = staffList.stream()
                .map(UserResponseDTO::from)
                .toList();

        return ResponseEntity.ok(result);
    }

    // 직원정보 수정
    @PutMapping("/users/{userNo}")
    @Operation(summary = "직원 정보 수정", description = "직원이름, 부서명, 비밀번호를 수정합니다.")
    public ResponseEntity<BaseResponseDto<String>> updateUser(
            @PathVariable Long userNo,
            @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {

        userService.updateUser(userNo, userUpdateRequestDTO);

        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, "직원 정보가 수정되었습니다."));
    }

    // 직원정보 삭제
    @DeleteMapping("/api/staffs/{userNo}")
    public ResponseEntity<BaseResponseDto<String>> deleteStaff(@PathVariable Long userNo) {
        userService.deleteStaff(userNo);
        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, "직원이 삭제되었습니다."));
    }
}
