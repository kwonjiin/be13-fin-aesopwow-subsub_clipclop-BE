package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.user.dto.UserResponseDTO;
import com.aesopwow.subsubclipclop.entity.CustomUserDetails;
import com.aesopwow.subsubclipclop.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RestController
public class TestController {

    @GetMapping()
    public ResponseEntity<UserResponseDTO> getTest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        User user = customUserDetails.getUser();
        UserResponseDTO userResponseDTO = UserResponseDTO.from(user);
        return ResponseEntity.ok(userResponseDTO);
    }
}
