package com.eprocurement.admin.users.service;

import com.eprocurement.admin.common.utils.CommonStatusEnum;
import com.eprocurement.admin.security.dto.SignUpRequestDto;
import com.eprocurement.admin.security.entity.User;
import com.eprocurement.admin.security.enums.UserRoles;
import com.eprocurement.admin.security.repository.UserRepository;
import com.eprocurement.admin.security.service.AuthenticationService;
import com.eprocurement.admin.users.dto.UpdateUserRequestDto;
import com.eprocurement.admin.users.dto.UserCreationRequestDto;
import com.eprocurement.admin.users.dto.UserCreationResponseDto;
import com.eprocurement.admin.users.entity.UserPermissionEntity;
import com.eprocurement.admin.users.enums.FunctionalPermissionEnum;
import com.eprocurement.admin.users.enums.UserHierarchyLevelEnum;
import com.eprocurement.admin.users.mappers.UserManagementMapper;
import com.eprocurement.admin.users.repository.UserPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@WithMockUser(username = "test")
public class UserManagementServiceTest {

    @Autowired
    private  UserManagementMapper userMapper;
    final String INITIAL_PASSWORD="Test@123";
    @Autowired
    private  UserPermissionRepository userPermissionRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  UserManagementService userManagementService;

    // we are gojng to use given_when_then naming convention
    @Test
    public void givenNewUserRequest_whenCreateBuyer_thenCreateNewValidBuyer(){

        UserCreationRequestDto userCreationRequestDto = buildUserCreationRequestDto("test",INITIAL_PASSWORD,
                "test@yopmail.com","test","user",UserHierarchyLevelEnum.EMPLOYEE,Arrays.asList(FunctionalPermissionEnum.PR_CREATION,FunctionalPermissionEnum.PR_BU_VIEW));
       UserCreationResponseDto userResponse =  userManagementService.createBuyer(userCreationRequestDto).getData();
       Assertions.assertTrue(userRepository.findByUsername(userCreationRequestDto.getUsername()).isPresent());
       User user = userRepository.findByUsername(userCreationRequestDto.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
       Assertions.assertNotEquals(userRepository.findByUsername(userCreationRequestDto.getUsername()).get().getPassword(),userCreationRequestDto.getPassword());
        Assertions.assertFalse(userPermissionRepository.findByUser(user).isEmpty());
        Assertions.assertEquals(userResponse.getFirstName(),userCreationRequestDto.getFirstName());
        Assertions.assertEquals(userResponse.getLastName(),userCreationRequestDto.getLastName());
        Assertions.assertEquals(userResponse.getUsername(),userCreationRequestDto.getUsername());
        Assertions.assertEquals(userResponse.getEmail(),userCreationRequestDto.getEmail());


    }

    public UserCreationRequestDto buildUserCreationRequestDto(String username,String password,String email,
                                                              String firstName,String lastName,UserHierarchyLevelEnum hierarchyLevel,
                                                              List<FunctionalPermissionEnum> permissionsList){
        return UserCreationRequestDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .hierarchyLevel(hierarchyLevel)
                .permissionsList(permissionsList)
                .build();
    }

}
