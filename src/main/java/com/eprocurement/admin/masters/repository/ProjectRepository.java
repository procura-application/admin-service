package com.eprocurement.admin.masters.repository;

import com.eprocurement.admin.masters.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<ProjectEntity, String>, JpaSpecificationExecutor<ProjectEntity> {

    boolean existsByProjectNameIgnoreCase(String projectName);

    boolean existsByProjectNameIgnoreCaseAndIdNot(String projectName, String id);

    @Query(nativeQuery = true, value = "select nextval('masters.project_code_seq')")
    Long getProjectCodeSeq();
}