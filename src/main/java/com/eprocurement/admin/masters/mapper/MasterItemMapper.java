package com.eprocurement.admin.masters.mapper;

import com.eprocurement.admin.masters.dto.MasterItemRequestDto;
import com.eprocurement.admin.masters.dto.MasterItemResponseDto;
import com.eprocurement.admin.masters.entity.MasterItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MasterItemMapper {

    @Mapping(source = "masterItemCategory.masterCategoryCode",target = "masterItemCategory")
    @Mapping(source = "masterItemCategory.masterCategoryName",target = "masterItemCategoryName")
    @Mapping(source = "masterItemCategory.businessUnit.businessUnitCode",target = "masterItemBusinessUnitCode")
    @Mapping(source = "masterItemCategory.businessUnit.businessUnitName",target = "masterItemBusinessUnitName")
    @Mapping(source = "masterItemCategory.masterCategoryId",target = "masterItemCategoryId")
    MasterItemResponseDto toDto(MasterItemEntity masterItemEntity);

    List<MasterItemResponseDto> toDtoList(List<MasterItemEntity> masterItemEntityList);
}
