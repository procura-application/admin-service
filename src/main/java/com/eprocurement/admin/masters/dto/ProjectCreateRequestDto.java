package com.eprocurement.admin.masters.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectCreateRequestDto {

    @NotBlank(message = "Please enter project name")
    private String projectName;

    private String description;

    @NotBlank(message = "Please send the business unit ID")
    private String businessUnitId;

    @NotNull(message = "Please enter the start date")
    private LocalDate startDate;

    private LocalDate endDate;

    @AssertTrue(message = "End date cannot be before start date")
    public boolean isDateRangeValid() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }
}