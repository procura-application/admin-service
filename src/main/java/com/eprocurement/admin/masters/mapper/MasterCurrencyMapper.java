package com.eprocurement.admin.masters.mapper;

import com.eprocurement.admin.masters.dto.MasterCurrencyAddRequestDto;
import com.eprocurement.admin.masters.dto.MasterCurrencyResponseDto;
import com.eprocurement.admin.masters.entity.MasterCurrencyEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MasterCurrencyMapper {

    MasterCurrencyResponseDto toDto(MasterCurrencyEntity masterCurrencyEntity);
    MasterCurrencyEntity fromAddRequestToEntity(MasterCurrencyAddRequestDto masterCurrencyAddRequestDto);
    List<MasterCurrencyResponseDto> toDtoList(List<MasterCurrencyEntity> masterCurrencyEntity);

}
