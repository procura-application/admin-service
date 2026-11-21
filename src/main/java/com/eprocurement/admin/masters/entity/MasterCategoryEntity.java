package com.eprocurement.admin.masters.entity;

import com.eprocurement.admin.common.entity.BaseAuditEntity;
import com.eprocurement.admin.masters.enums.CategoryStatusEnum;
import com.eprocurement.admin.masters.enums.ItemtypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name="master_category",schema = "masters")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasterCategoryEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="master_category_id")
    private String masterCategoryId;

    @Column(name="master_category_code",unique = true)
    private String masterCategoryCode;

    @Column(name="master_category_name")
    private String masterCategoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="business_unit_id",nullable = false)
    private BusinessUnit businessUnit;

    @Enumerated(EnumType.STRING)
    @Column(name="item_type")
    private ItemtypeEnum itemType;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private CategoryStatusEnum status;



    @OneToMany(mappedBy = "masterItemCategory",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private Set<MasterItemEntity> masterItems;

}
