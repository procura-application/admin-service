package com.eprocurement.admin.masters.dto;

import com.eprocurement.admin.common.utils.CommonStatusEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CostCenterResponseDto {
    private String id;
    private String costCenterCode;
    private String costCenterName;
    private String description;
    private String businessUnitId;
    private String businessUnitCode;
    private String businessUnitName;
    private LocalDate effectiveFromDate;
    private LocalDate effectiveToDate;
    private CommonStatusEnum status;
}