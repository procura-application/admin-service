package com.eprocurement.admin.masters.dto;

import com.eprocurement.admin.common.utils.CommonStatusEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectResponseDto {
    private String id;
    private String projectCode;
    private String projectName;
    private String description;
    private String businessUnitId;
    private String businessUnitCode;
    private String businessUnitName;
    private LocalDate startDate;
    private LocalDate endDate;
    private CommonStatusEnum status;
}