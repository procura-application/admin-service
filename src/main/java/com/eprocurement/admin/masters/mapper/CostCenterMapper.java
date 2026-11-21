package com.eprocurement.admin.masters.mapper;

import com.eprocurement.admin.masters.dto.CostCenterResponseDto;
import com.eprocurement.admin.masters.entity.CostCenterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CostCenterMapper {

    @Mapping(source = "businessUnit.id", target = "businessUnitId")
    @Mapping(source = "businessUnit.businessUnitCode", target = "businessUnitCode")
    @Mapping(source = "businessUnit.businessUnitName", target = "businessUnitName")
    CostCenterResponseDto toDto(CostCenterEntity entity);

    List<CostCenterResponseDto> toDtoList(List<CostCenterEntity> entities);
}