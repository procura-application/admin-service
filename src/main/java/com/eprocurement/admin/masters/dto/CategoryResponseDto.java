package com.eprocurement.admin.masters.dto;

import com.eprocurement.admin.masters.enums.CategoryStatusEnum;
import com.eprocurement.admin.masters.enums.ItemtypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CategoryResponseDto {

    private String masterCategoryId;
    private String masterCategoryCode;
    private String masterCategoryName;
    private String businessUnitName;
    private String businessUnitCode;
    private String businessUnitId;
    private ItemtypeEnum itemType;
    private CategoryStatusEnum status;
}
