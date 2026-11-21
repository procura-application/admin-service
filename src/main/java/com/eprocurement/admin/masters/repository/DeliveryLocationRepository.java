package com.eprocurement.admin.masters.repository;

import com.eprocurement.admin.masters.entity.DeliveryLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface DeliveryLocationRepository extends JpaRepository<DeliveryLocationEntity, String>, JpaSpecificationExecutor<DeliveryLocationEntity> {

    boolean existsByLocationNameIgnoreCaseAndBusinessUnit_Id(String locationName, String businessUnitId);

    boolean existsByLocationNameIgnoreCaseAndBusinessUnit_IdAndIdNot(String locationName, String businessUnitId, String id);

    @Query(nativeQuery = true, value = "select nextval('masters.delivery_location_code_seq')")
    Long getDeliveryLocationCodeSeq();
}