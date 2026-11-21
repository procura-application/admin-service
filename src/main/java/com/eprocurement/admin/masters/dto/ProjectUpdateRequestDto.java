package com.eprocurement.admin.masters.dto;

import com.eprocurement.admin.common.utils.CommonStatusEnum;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectUpdateRequestDto {

    private String projectName;
    private String description;
    private String businessUnitId;
    private LocalDate startDate;
    private LocalDate endDate;
    private CommonStatusEnum status;

    @AssertTrue(message = "End date cannot be before start date")
    public boolean isDateRangeValid() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }
}