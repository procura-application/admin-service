package com.eprocurement.admin.masters.specifications;

import com.eprocurement.admin.common.utils.SearchCriteria;
import com.eprocurement.admin.masters.entity.BusinessUnit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
@AllArgsConstructor
public class BusinessUnitSpecifications implements Specification<BusinessUnit> {

    private SearchCriteria searchCriteria;
    @Override
    public @Nullable Predicate toPredicate(Root<BusinessUnit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if(searchCriteria.getOperator().equalsIgnoreCase("=")){
            return criteriaBuilder.like(root.get(searchCriteria.getFirstValue()),"%"+searchCriteria.getLastValue()+"%");
        }
        return null;
    }



}
