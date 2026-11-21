package com.eprocurement.admin.masters.dto;

import com.eprocurement.admin.masters.enums.ItemtypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequestDto {
	
	@NotNull(message="please enter category name")
	@NotEmpty(message="please enter category name")
	private String categoryName;

	private String businessUnitId;

	private ItemtypeEnum itemType;


}
