package com.eprocurement.admin.masters.dto;

import com.eprocurement.admin.common.utils.CommonStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BudgetResponseDto {
    private String id;
    private String budgetCode;
    private String budgetName;
    private String budgetDescription;
    private String wbsId;
    private String wbsCode;
    private String wbsName;
    private String currencyCode;
    private String currencyName;
    private BigDecimal allocatedAmount;
    private BigDecimal consumedAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private CommonStatusEnum status;
}