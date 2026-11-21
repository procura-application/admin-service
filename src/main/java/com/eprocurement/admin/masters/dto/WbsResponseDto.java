package com.eprocurement.admin.masters.dto;

import com.eprocurement.admin.common.utils.CommonStatusEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WbsResponseDto {
    private String id;
    private String wbsCode;
    private String wbsName;
    private String wbsDescription;
    private String projectId;
    private String projectCode;
    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private CommonStatusEnum status;
}