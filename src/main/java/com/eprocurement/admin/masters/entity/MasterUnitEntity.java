package com.eprocurement.admin.masters.entity;

import com.eprocurement.admin.common.entity.BaseAuditEntity;
import com.eprocurement.admin.common.utils.CommonStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "masters",name = "master_unit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterUnitEntity extends BaseAuditEntity {

    @Id
    @Column(name = "master_unit_code",unique = true)
    private String masterUnitCode;
    @Column(name = "master_unit_name",unique = true)
    private String masterUnitName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;
}
