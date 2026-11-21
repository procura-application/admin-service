package com.eprocurement.admin.masters.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BudgetCreateRequestDto {

    @NotBlank(message = "Please enter budget name")
    private String budgetName;

    private String budgetDescription;

    @NotBlank(message = "Please send the WBS ID")
    private String wbsId;

    @NotBlank(message = "Please send the currency code")
    private String currencyCode;

    @NotNull(message = "Please enter the allocated amount")
    @PositiveOrZero(message = "Allocated amount cannot be negative")
    private BigDecimal allocatedAmount;

    @NotNull(message = "Please enter the start date")
    private LocalDate startDate;

    private LocalDate endDate;

    @AssertTrue(message = "End date cannot be before start date")
    public boolean isDateRangeValid() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }
}