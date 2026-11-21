package com.eprocurement.admin.masters.mapper;

import com.eprocurement.admin.masters.dto.CategoryResponseDto;
import com.eprocurement.admin.masters.entity.MasterCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MasterCategoryMapper {

    @Mapping(source = "businessUnit.businessUnitCode",target = "businessUnitCode")
    @Mapping(source = "businessUnit.businessUnitName",target = "businessUnitName")
    @Mapping(source = "businessUnit.id",target = "businessUnitId")
    CategoryResponseDto toDto(MasterCategoryEntity entity);

    List<CategoryResponseDto> toDtoList(List<MasterCategoryEntity> entity);
}
