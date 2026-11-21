package com.eprocurement.admin.masters.dto;

import lombok.Data;

@Data
public class BusinessUnitResponseDto {
	private String id;
	private String businessUnitCode;
	private String businessUnitName;
	private BusinessUnitManagerDto businessUnitManager;
	private String businessUnitDescription;
	private String status;

}
