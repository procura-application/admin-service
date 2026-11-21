package com.eprocurement.admin.masters.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BusinessUnitRequestDto {
	
	@NotNull(message="please enter business unit name")
	@NotEmpty(message="please enter business unit name")
	private String businessUnitName;

	@NotNull(message = "Please Send the ID of the business unit Manager")
	@NotEmpty(message = "Please Send the ID of the business unit Manager")
	private String businessUnitManagerId;

	@NotNull(message = "Please Enter Business unit description")
	@NotEmpty(message = "Please Enter Business Unit Description")
	private String businessUnitDescription;

}
