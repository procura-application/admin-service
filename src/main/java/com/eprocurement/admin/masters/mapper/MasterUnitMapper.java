package com.eprocurement.admin.masters.mapper;

import com.eprocurement.admin.masters.dto.MasterUnitRequestDto;
import com.eprocurement.admin.masters.dto.MasterUnitResponseDto;
import com.eprocurement.admin.masters.entity.MasterUnitEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MasterUnitMapper {

    MasterUnitEntity toEntity(MasterUnitRequestDto masterUnitRequestDto);
    MasterUnitResponseDto toDto(MasterUnitEntity masterUnitEntity);
    List<MasterUnitResponseDto> toDtoList(List<MasterUnitEntity> masterUnitEntities);
}
