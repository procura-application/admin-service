package com.eprocurement.admin.security.mapper;

import org.mapstruct.Mapper;

import com.eprocurement.admin.security.dto.SignUpRequestDto;
import com.eprocurement.admin.security.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	User toEntity(SignUpRequestDto signUpRequestDto);

}
