package com.eprocurement.admin.masters.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eprocurement.admin.masters.entity.BusinessUnit;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, String>, JpaSpecificationExecutor<BusinessUnit> {
	
	public Optional<BusinessUnit> findByBusinessUnitName(String businessUnitName);
	public boolean existsByBusinessUnitNameIgnoreCase(String businessUnitName);
	public boolean existsByBusinessUnitNameIgnoreCaseAndIdNot(String businessUnitName, String id);
	public boolean existsByBusinessUnitManager_UserId(String userId);
	public boolean existsByBusinessUnitManager_UserIdAndIdNot(String userId, String id);

	@Query(nativeQuery = true,value = "select nextval('masters.business_unit_code_seq')")
	public Long getBusinessUnitCodeSeq();

}
