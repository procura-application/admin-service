package com.eprocurement.admin.masters.repository;

import com.eprocurement.admin.masters.entity.MasterItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterItemRepository extends JpaRepository<MasterItemEntity,String>, JpaSpecificationExecutor<MasterItemEntity> {

    @Query(nativeQuery = true,value = "select nextval('masters.master_item_seq')")
    public Long getMasterItemSeq();



    public boolean existsByMasterItemNameIgnoreCaseAndMasterItemCategory_BusinessUnit_Id(String itemName,String businessUnitId);


}
