package com.eprocurement.admin.masters.dto;

import lombok.Data;

@Data
public class MasterItemResponseDto {

    private String masterItemId;
    private String masterItemName;
    private String masterItemCode;
    private String masterItemDescription;
    private String masterItemCategoryId;
    private String masterItemCategory;
    private String masterItemCategoryName;
    private String masterItemBusinessUnitCode;
    private String masterItemBusinessUnitName;
    private String status;
}
