package com.eprocurement.admin.users.dto;

import com.eprocurement.admin.users.enums.FunctionalPermissionEnum;
import com.eprocurement.admin.users.enums.UserHierarchyLevelEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationResponseDto {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UserHierarchyLevelEnum hierarchyLevel;
    private List<FunctionalPermissionEnum> permissionsList;
}
