package com.eprocurement.admin.masters.mapper;

import org.mapstruct.Mapper;

import com.eprocurement.admin.masters.dto.BusinessUnitRequestDto;
import com.eprocurement.admin.masters.dto.BusinessUnitResponseDto;
import com.eprocurement.admin.masters.entity.BusinessUnit;
import com.eprocurement.admin.security.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BusinessUnitMapper {
	
	public BusinessUnit toEntity(BusinessUnitRequestDto businessUnitRequestDto);
	public BusinessUnitResponseDto toDto(BusinessUnit businessUnit);
		//public BusinessUnitManagerDto toDto(User user);
	public List<BusinessUnitResponseDto> toDtoList(List<BusinessUnit> businessUnit);

}
