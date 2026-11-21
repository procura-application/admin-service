package com.eprocurement.admin.users.repository;

import com.eprocurement.admin.security.entity.User;
import com.eprocurement.admin.users.entity.UserPermissionEntity;
import com.eprocurement.admin.users.enums.FunctionalPermissionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermissionEntity,String> {

    List<UserPermissionEntity> findByUser(User user);
    List<UserPermissionEntity> findByUserAndPermissionTypeIn(User user, List<FunctionalPermissionEnum> functionalPermissionEnumList);
}
