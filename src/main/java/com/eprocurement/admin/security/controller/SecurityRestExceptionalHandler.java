package com.eprocurement.admin.security.controller;

import java.util.ArrayList;

import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eprocurement.admin.common.dto.ApiResponse;
import com.eprocurement.admin.security.exceptions.UserAlreadyExistsException;

import io.jsonwebtoken.lang.Arrays;

@RestControllerAdvice
public class SecurityRestExceptionalHandler {

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleUserNotFound(UsernameNotFoundException ex){
		ArrayList<String> errorList = new ArrayList<String>();
		errorList.add(ex.getMessage());
		ApiResponse<String> response = ApiResponse.<String>builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.data(null)
				.errorList(errorList)
				.message(ex.getMessage())
				.build();
		
		return ResponseEntity.badRequest().body(response);
		
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<String>> handleUserExists(UserAlreadyExistsException ex){
		ArrayList<String> errorList = new ArrayList<String>();
		errorList.add(ex.getMessage());
		ApiResponse<String> response = ApiResponse.<String>builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.data(null)
				.errorList(errorList)
				.message(ex.getMessage())
				.build();
		
		return ResponseEntity.badRequest().body(response);
		
	}
}
