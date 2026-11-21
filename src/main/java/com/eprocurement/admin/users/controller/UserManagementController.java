package com.eprocurement.admin.users.controller;

import com.eprocurement.admin.common.dto.ApiResponse;
import com.eprocurement.admin.users.dto.UserCreationRequestDto;
import com.eprocurement.admin.users.dto.UserCreationResponseDto;
import com.eprocurement.admin.users.service.UserManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/")
@RequiredArgsConstructor
public class UserManagementController {
    private final UserManagementService userManagementService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<UserCreationResponseDto>> createBuyer(@RequestBody @Valid UserCreationRequestDto userCreationRequestDto){

        return ResponseEntity.ok(userManagementService.createBuyer(userCreationRequestDto));
    }
}
