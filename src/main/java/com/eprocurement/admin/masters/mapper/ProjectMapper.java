package com.eprocurement.admin.masters.mapper;

import com.eprocurement.admin.masters.dto.ProjectResponseDto;
import com.eprocurement.admin.masters.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source = "businessUnit.id", target = "businessUnitId")
    @Mapping(source = "businessUnit.businessUnitCode", target = "businessUnitCode")
    @Mapping(source = "businessUnit.businessUnitName", target = "businessUnitName")
    ProjectResponseDto toDto(ProjectEntity entity);

    List<ProjectResponseDto> toDtoList(List<ProjectEntity> entities);
}