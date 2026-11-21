package com.eprocurement.admin.users.entity;

import com.eprocurement.admin.common.entity.BaseAuditEntity;
import com.eprocurement.admin.common.utils.CommonStatusEnum;
import com.eprocurement.admin.masters.entity.BusinessUnit;
import com.eprocurement.admin.security.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "user_bu_details",schema = "masters")
@Data
@Builder
public class UserBuDetailsEntity extends BaseAuditEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_bu_details_id")
    private String userBuDetailsId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_unit_id")
    private BusinessUnit businessUnit;
}
