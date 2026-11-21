package com.eprocurement.admin.common.dto;

import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.http.HttpStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

	@Enumerated(EnumType.ORDINAL)
	private int status;
	private String message;
	private T data;
	private List<String> errorList;

}
