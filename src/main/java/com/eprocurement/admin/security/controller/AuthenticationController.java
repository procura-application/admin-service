package com.eprocurement.admin.security.controller;

import com.eprocurement.admin.security.enums.UserRoles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eprocurement.admin.common.dto.ApiResponse;
import com.eprocurement.admin.security.dto.AuthenticationRequest;
import com.eprocurement.admin.security.dto.AuthenticationResponse;
import com.eprocurement.admin.security.dto.SignUpRequestDto;
import com.eprocurement.admin.security.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authentication/")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@PostMapping("buyer/register")
	public ResponseEntity<ApiResponse<AuthenticationResponse>> registerBuyer(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
		
		return authenticationService.register(signUpRequestDto, UserRoles.BUYER);
	}
	
	@PostMapping("login")
	public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@Valid @RequestBody AuthenticationRequest authenticationRequest){
		
		return authenticationService.login(authenticationRequest);
	}

	@PostMapping("admin/register")
	public ResponseEntity<ApiResponse<AuthenticationResponse>> registerAdmin(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
		log.info("isnide admin register");
		return authenticationService.register(signUpRequestDto,UserRoles.ADMIN);
	}



	

}
