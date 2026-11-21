package com.eprocurement.admin.masters.repository;

import com.eprocurement.admin.masters.entity.MasterUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MasterUnitRepository extends JpaRepository<MasterUnitEntity,String>, JpaSpecificationExecutor<MasterUnitEntity> {

    Optional<MasterUnitEntity> findByMasterUnitName(String unitName);
}
