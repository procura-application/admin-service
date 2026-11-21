package com.eprocurement.admin.masters.entity;

import com.eprocurement.admin.common.entity.BaseAuditEntity;
import com.eprocurement.admin.common.utils.CommonStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(schema = "masters",name = "master_currency")
@NoArgsConstructor
@AllArgsConstructor
public class MasterCurrencyEntity extends BaseAuditEntity {


    @Id
    @Column(name = "master_currency_code",unique = true)
    private String masterCurrencyCode;
    @Column(name = "master_currency_name",unique = true)
    private String masterCurrencyName;
    @Column(name = "main_unit")
    private String mainUnit;
    @Column(name="sub_unit")
    private String subUnit;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;
}
