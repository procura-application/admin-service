package com.eprocurement.admin.masters.entity;

import com.eprocurement.admin.common.entity.BaseAuditEntity;
import com.eprocurement.admin.common.utils.CommonStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Table(name = "master_item",schema = "masters")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MasterItemEntity extends BaseAuditEntity {

    @Column(name = "master_item_id")
    @Id
    private String masterItemId;

    @Column(name="master_item_code")
    private String masterItemCode;

    @Column(name="master_item_name")
    private String masterItemName;

    @Column(name="master_item_description")
    private String masterItemDescription;

    @ManyToOne
    @JoinColumn(name = "master_item_category")
    private MasterCategoryEntity masterItemCategory;



    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;
}
