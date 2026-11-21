package com.eprocurement.admin.users.service;

import com.eprocurement.admin.common.dto.ApiResponse;
import com.eprocurement.admin.common.exceptions.DuplicateResourceException;
import com.eprocurement.admin.common.exceptions.NotFoundResourceException;
import com.eprocurement.admin.common.utils.CommonStatusEnum;
import com.eprocurement.admin.security.dto.SignUpRequestDto;
import com.eprocurement.admin.security.entity.User;
import com.eprocurement.admin.security.enums.UserRoles;
import com.eprocurement.admin.security.exceptions.UserAlreadyExistsException;
import com.eprocurement.admin.security.repository.UserRepository;
import com.eprocurement.admin.security.service.AuthenticationService;
import com.eprocurement.admin.users.dto.UpdateUserRequestDto;
import com.eprocurement.admin.users.dto.UserCreationRequestDto;
import com.eprocurement.admin.users.dto.UserCreationResponseDto;
import com.eprocurement.admin.users.entity.UserPermissionEntity;
import com.eprocurement.admin.users.enums.FunctionalPermissionEnum;
import com.eprocurement.admin.users.mappers.UserManagementMapper;
import com.eprocurement.admin.users.repository.UserPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserManagementMapper userMapper;
     final String INITIAL_PASSWORD="Test@123";
     private final UserPermissionRepository userPermissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public ApiResponse<UserCreationResponseDto> createBuyer(UserCreationRequestDto userCreationRequestDto){
        User user = userRepository.findByUsername(userCreationRequestDto.getUsername()).isPresent() ?
            updateUser(userCreationRequestDto,UserRoles.BUYER) : saveUser(userCreationRequestDto,UserRoles.BUYER);
        List<FunctionalPermissionEnum> functionalPermissions = saveFunctionalPermissions(user,userCreationRequestDto.getPermissionsList());
        UserCreationResponseDto responseDto = userMapper.toResponseDto(user,functionalPermissions);
        return ApiResponse.<UserCreationResponseDto>builder()
                .message("User Created Successfully")
                .data(responseDto)
                .errorList(null)
                .status(HttpStatus.CREATED.value())
        . build();
    }

    private List<FunctionalPermissionEnum> saveFunctionalPermissions(User user,List<FunctionalPermissionEnum> functionalPermissions){
        if(functionalPermissions==null || functionalPermissions.isEmpty()) return new ArrayList<>();
        List<FunctionalPermissionEnum> functionalPermissionEnumList = new ArrayList<>(functionalPermissions);
        List<UserPermissionEntity> fetchedUserPermissionEntityList = userPermissionRepository.findByUser(user);
        List<FunctionalPermissionEnum> savedFunctionalPermissions = new ArrayList<>();
        if(fetchedUserPermissionEntityList!=null && !fetchedUserPermissionEntityList.isEmpty()) {
            savedFunctionalPermissions = new ArrayList<>(fetchedUserPermissionEntityList.stream().map(UserPermissionEntity::getPermissionType).toList());
        }
        functionalPermissionEnumList.removeAll(savedFunctionalPermissions);
        List<FunctionalPermissionEnum> toSaveFunctionalPermissions = new ArrayList<>(functionalPermissionEnumList);
        savedFunctionalPermissions.removeAll(functionalPermissions);
        List<FunctionalPermissionEnum> toDeleteFunctionalPermissions = new ArrayList<>(savedFunctionalPermissions);
        List<UserPermissionEntity> toDeleteUserPermissionEntityList = new ArrayList<>(userPermissionRepository.findByUserAndPermissionTypeIn(user,toDeleteFunctionalPermissions));
        userPermissionRepository.deleteAll(toDeleteUserPermissionEntityList);
        List<UserPermissionEntity> userPermissionEntityList = new ArrayList<>();
        for(FunctionalPermissionEnum functionalPermissionEnum: toSaveFunctionalPermissions){
            UserPermissionEntity userPermissionEntity = UserPermissionEntity.builder()
                    .permissionType(functionalPermissionEnum)
                    .status(CommonStatusEnum.ACTIVE)
                    .user(user)
                    .build();
            userPermissionEntityList.add(userPermissionEntity);
        }
        userPermissionRepository.saveAll(userPermissionEntityList);
        return functionalPermissions;

    }



    public User saveUser(UserCreationRequestDto userCreationRequestDto, UserRoles role){
        User requestUser = userMapper.toEntity(userCreationRequestDto);
        requestUser.setRole(role);
        requestUser.setUserId(UUID.randomUUID().toString());
        requestUser.setPassword(passwordEncoder.encode(INITIAL_PASSWORD));
        requestUser.setStatus(CommonStatusEnum.ACTIVE);
       return userRepository.save(requestUser);

    }

    public User updateUser(UserCreationRequestDto userCreationRequestDto,UserRoles role){
        User fetchedUser = userRepository.findByUsername(userCreationRequestDto.getUsername()).orElseThrow(()-> new UsernameNotFoundException("Could not find user of user name:"+userCreationRequestDto.getUsername()));
        if(!StringUtils.isEmpty(userCreationRequestDto.getPassword())) fetchedUser.setPassword(passwordEncoder.encode(userCreationRequestDto.getPassword()));
        if(!StringUtils.isEmpty(userCreationRequestDto.getEmail())){
            if(userRepository.findByEmail(userCreationRequestDto.getEmail()).isPresent()) throw new DuplicateResourceException("Email Already Exists. Please Enter Different Email");
            fetchedUser.setEmail(userCreationRequestDto.getEmail());
        }
        if(!StringUtils.isEmpty(userCreationRequestDto.getFirstName())) fetchedUser.setFirstName(userCreationRequestDto.getFirstName());
        if(!StringUtils.isEmpty(userCreationRequestDto.getLastName())) fetchedUser.setLastName(userCreationRequestDto.getLastName());
        if(userCreationRequestDto.getHierarchyLevel()!=null) fetchedUser.setHierarchyLevel(userCreationRequestDto.getHierarchyLevel());

        return userRepository.save(fetchedUser);


    }


}
