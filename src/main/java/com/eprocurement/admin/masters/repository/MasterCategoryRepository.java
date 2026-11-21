package com.eprocurement.admin.masters.repository;

import com.eprocurement.admin.masters.entity.MasterCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MasterCategoryRepository extends JpaRepository<MasterCategoryEntity,String>, JpaSpecificationExecutor<MasterCategoryEntity> {

    @Query(nativeQuery = true,value = "select nextval('masters.master_category_seq')")
    public Long getMasterCategorySeq();

    public Optional<MasterCategoryEntity> findByMasterCategoryName(String categoryName);

    boolean existsByMasterCategoryNameAndMasterCategoryIdNot(String categoryName, String masterCategoryId);
}
