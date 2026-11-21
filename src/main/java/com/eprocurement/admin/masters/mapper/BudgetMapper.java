package com.eprocurement.admin.masters.mapper;

import com.eprocurement.admin.masters.dto.BudgetResponseDto;
import com.eprocurement.admin.masters.entity.BudgetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BudgetMapper {

    @Mapping(source = "wbs.id", target = "wbsId")
    @Mapping(source = "wbs.wbsCode", target = "wbsCode")
    @Mapping(source = "wbs.wbsName", target = "wbsName")
    @Mapping(source = "currency.masterCurrencyCode", target = "currencyCode")
    @Mapping(source = "currency.masterCurrencyName", target = "currencyName")
    BudgetResponseDto toDto(BudgetEntity entity);

    List<BudgetResponseDto> toDtoList(List<BudgetEntity> entities);
}