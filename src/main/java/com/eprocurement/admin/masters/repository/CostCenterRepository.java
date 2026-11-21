package com.eprocurement.admin.masters.repository;

import com.eprocurement.admin.masters.entity.CostCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CostCenterRepository extends JpaRepository<CostCenterEntity, String>, JpaSpecificationExecutor<CostCenterEntity> {

    boolean existsByCostCenterNameIgnoreCase(String costCenterName);

    boolean existsByCostCenterNameIgnoreCaseAndIdNot(String costCenterName, String id);

    @Query(nativeQuery = true, value = "select nextval('masters.cost_center_code_seq')")
    Long getCostCenterCodeSeq();
}