package com.eprocurement.admin.masters.repository;

import com.eprocurement.admin.masters.entity.MasterCurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MasterCurrencyRepository extends JpaRepository<MasterCurrencyEntity,String>, JpaSpecificationExecutor<MasterCurrencyEntity> {

    Optional<MasterCurrencyEntity> findByMasterCurrencyName(String currencyName);
}
