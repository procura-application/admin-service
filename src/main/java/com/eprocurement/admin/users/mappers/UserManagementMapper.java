package com.eprocurement.admin.users.mappers;

import com.eprocurement.admin.security.dto.SignUpRequestDto;
import com.eprocurement.admin.security.entity.User;
import com.eprocurement.admin.users.dto.UpdateUserRequestDto;
import com.eprocurement.admin.users.dto.UserCreationRequestDto;
import com.eprocurement.admin.users.dto.UserCreationResponseDto;
import com.eprocurement.admin.users.enums.FunctionalPermissionEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserManagementMapper {

    SignUpRequestDto toSignUpDto(UserCreationRequestDto userCreationRequestDto);

    @Mapping(source = "functionalPermissionEnums",target = "permissionsList")
    UserCreationResponseDto toResponseDto(User user, List<FunctionalPermissionEnum> functionalPermissionEnums);



    User toEntity(UserCreationRequestDto userCreationRequestDto);
}
