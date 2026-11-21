package com.eprocurement.admin.masters.mapper;

import com.eprocurement.admin.masters.dto.WbsResponseDto;
import com.eprocurement.admin.masters.entity.WbsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WbsMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.projectCode", target = "projectCode")
    @Mapping(source = "project.projectName", target = "projectName")
    WbsResponseDto toDto(WbsEntity entity);

    List<WbsResponseDto> toDtoList(List<WbsEntity> entities);
}