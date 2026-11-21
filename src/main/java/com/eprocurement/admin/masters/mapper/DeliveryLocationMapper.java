package com.eprocurement.admin.masters.mapper;

import com.eprocurement.admin.masters.dto.DeliveryLocationResponseDto;
import com.eprocurement.admin.masters.entity.DeliveryLocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryLocationMapper {

    @Mapping(source = "businessUnit.id", target = "businessUnitId")
    @Mapping(source = "businessUnit.businessUnitCode", target = "businessUnitCode")
    @Mapping(source = "businessUnit.businessUnitName", target = "businessUnitName")
    DeliveryLocationResponseDto toDto(DeliveryLocationEntity entity);

    List<DeliveryLocationResponseDto> toDtoList(List<DeliveryLocationEntity> entities);
}