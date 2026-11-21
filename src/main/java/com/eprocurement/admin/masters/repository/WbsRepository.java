package com.eprocurement.admin.masters.repository;

import com.eprocurement.admin.masters.entity.WbsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface WbsRepository extends JpaRepository<WbsEntity, String>, JpaSpecificationExecutor<WbsEntity> {

    boolean existsByWbsNameIgnoreCaseAndProject_Id(String wbsName, String projectId);

    boolean existsByWbsNameIgnoreCaseAndProject_IdAndIdNot(String wbsName, String projectId, String id);

    @Query(nativeQuery = true, value = "select nextval('masters.wbs_code_seq')")
    Long getWbsCodeSeq();
}