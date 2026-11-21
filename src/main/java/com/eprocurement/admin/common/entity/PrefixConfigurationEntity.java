package com.eprocurement.admin.common.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "prefix_configurations",schema = "masters")
@Entity
@Data
public class PrefixConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="prefix_configuration_id")
    private String prefixConfigurationId;

    @Column(name="business_unit_code_prefix")
    private String businessUnitCodePrefix;

    @Column(name="master_category_code_prefix")
    private String masterCategoryCodePrefix;

    @Column(name="master_item_code_prefix")
    private String masterItemCodePrefix;

    @Column(name="project_code_prefix")
    private String projectCodePrefix;

    @Column(name="wbs_code_prefix")
    private String wbsCodePrefix;

    @Column(name="budget_code_prefix")
    private String budgetCodePrefix;

    @Column(name="delivery_location_code_prefix")
    private String deliveryLocationCodePrefix;

}
