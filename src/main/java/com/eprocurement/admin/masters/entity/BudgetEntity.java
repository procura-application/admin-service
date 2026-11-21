package com.eprocurement.admin.masters.entity;

import com.eprocurement.admin.common.entity.BaseAuditEntity;
import com.eprocurement.admin.common.utils.CommonStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "master_budget", schema = "masters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "budget_code", unique = true)
    private String budgetCode;

    @Column(name = "budget_name", nullable = false)
    private String budgetName;

    @Column(name = "budget_description")
    private String budgetDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wbs_id", nullable = false)
    private WbsEntity wbs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_code", nullable = false)
    private MasterCurrencyEntity currency;

    @Column(name = "allocated_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal allocatedAmount;

    @Builder.Default
    @Column(name = "consumed_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal consumedAmount = BigDecimal.ZERO;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CommonStatusEnum status;
}