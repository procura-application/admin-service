package com.eprocurement.admin.masters.entity;

import com.eprocurement.admin.common.entity.BaseAuditEntity;
import com.eprocurement.admin.common.utils.CommonStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "master_cost_center", schema = "masters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CostCenterEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "cost_center_code", unique = true)
    private String costCenterCode;

    @Column(name = "cost_center_name", nullable = false, unique = true)
    private String costCenterName;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_unit_id", nullable = false)
    private BusinessUnit businessUnit;

    @Column(name = "effective_from_date", nullable = false)
    private LocalDate effectiveFromDate;

    @Column(name = "effective_to_date")
    private LocalDate effectiveToDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CommonStatusEnum status;
}