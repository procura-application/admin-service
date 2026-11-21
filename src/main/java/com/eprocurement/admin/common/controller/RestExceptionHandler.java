package com.eprocurement.admin.common.controller;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eprocurement.admin.common.exceptions.NotFoundResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eprocurement.admin.common.dto.ApiResponse;
import com.eprocurement.admin.common.exceptions.DuplicateResourceException;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<String>> handleValidationError(MethodArgumentNotValidException ex){
		 List<String> errors = ex.getBindingResult()
		            .getFieldErrors()
		            .stream()
		            .map(error -> error.getField() + ": " + error.getDefaultMessage())
		            .toList();
		ApiResponse<String> response = ApiResponse.<String>builder().data(null)
				.message("Validation Error")
				.status(HttpStatus.BAD_REQUEST.value())
				.errorList(errors).build();
		
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiResponse<String>> handleDuplicateRsource(DuplicateResourceException ex){
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

	@ExceptionHandler(NotFoundResourceException.class)
	public ResponseEntity<ApiResponse<String>> handleNotFoundRsource(NotFoundResourceException ex){
		ArrayList<String> errorList = new ArrayList<String>();
		errorList.add(ex.getMessage());
		ApiResponse<String> response = ApiResponse.<String>builder()
				.status(HttpStatus.NOT_FOUND.value())
				.data(null)
				.errorList(errorList)
				.message(ex.getMessage())
				.build();

		return ResponseEntity.badRequest().body(response);

	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse<String>> handleInvalidArgument(IllegalArgumentException ex){
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
