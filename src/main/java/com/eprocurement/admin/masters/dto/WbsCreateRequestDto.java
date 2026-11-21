package com.eprocurement.admin.masters.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WbsCreateRequestDto {

    @NotBlank(message = "Please enter WBS name")
    private String wbsName;

    private String wbsDescription;

    @NotBlank(message = "Please send the project ID")
    private String projectId;

    @NotNull(message = "Please enter the start date")
    private LocalDate startDate;

    private LocalDate endDate;

    @AssertTrue(message = "End date cannot be before start date")
    public boolean isDateRangeValid() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }
}