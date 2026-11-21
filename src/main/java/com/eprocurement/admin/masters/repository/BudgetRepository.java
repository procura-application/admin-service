package com.eprocurement.admin.masters.repository;

import com.eprocurement.admin.masters.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BudgetRepository extends JpaRepository<BudgetEntity, String>, JpaSpecificationExecutor<BudgetEntity> {

    boolean existsByBudgetNameIgnoreCaseAndWbs_Id(String budgetName, String wbsId);

    boolean existsByBudgetNameIgnoreCaseAndWbs_IdAndIdNot(String budgetName, String wbsId, String id);

    @Query(nativeQuery = true, value = "select nextval('masters.budget_code_seq')")
    Long getBudgetCodeSeq();
}