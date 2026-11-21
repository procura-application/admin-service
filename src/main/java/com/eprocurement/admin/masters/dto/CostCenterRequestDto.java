package com.eprocurement.admin.masters.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CostCenterRequestDto {

    @NotBlank(message = "Please enter cost center name")
    private String costCenterName;

    private String description;

    @NotBlank(message = "Please send the business unit ID")
    private String businessUnitId;

    @NotNull(message = "Please enter the effective from date")
    private LocalDate effectiveFromDate;

    private LocalDate effectiveToDate;

    @AssertTrue(message = "Effective to date cannot be before effective from date")
    public boolean isEffectiveDateRangeValid() {
        return effectiveFromDate == null || effectiveToDate == null || !effectiveToDate.isBefore(effectiveFromDate);
    }
}