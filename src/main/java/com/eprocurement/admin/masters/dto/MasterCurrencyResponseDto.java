package com.eprocurement.admin.masters.dto;

import lombok.Data;

@Data
public class MasterCurrencyResponseDto {

    private String masterCurrencyCode;
    private String masterCurrencyName;
    private String mainUnit;
    private String subUnit;
    private String status;
}
