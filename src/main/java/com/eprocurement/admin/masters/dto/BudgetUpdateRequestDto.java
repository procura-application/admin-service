package com.eprocurement.admin.masters.dto;

import com.eprocurement.admin.common.utils.CommonStatusEnum;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BudgetUpdateRequestDto {

    private String budgetName;
    private String budgetDescription;
    private String wbsId;
    private String currencyCode;

    @PositiveOrZero(message = "Allocated amount cannot be negative")
    private BigDecimal allocatedAmount;

    private LocalDate startDate;
    private LocalDate endDate;
    private CommonStatusEnum status;

    @AssertTrue(message = "End date cannot be before start date")
    public boolean isDateRangeValid() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }
}