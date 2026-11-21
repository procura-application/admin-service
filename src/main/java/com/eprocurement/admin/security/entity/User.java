package com.eprocurement.admin.security.entity;

import java.util.Collection;
import java.util.Set;

import com.eprocurement.admin.common.entity.BaseAuditEntity;
import com.eprocurement.admin.common.utils.CommonStatusEnum;
import com.eprocurement.admin.users.entity.UserBuDetailsEntity;
import com.eprocurement.admin.users.entity.UserPermissionEntity;
import com.eprocurement.admin.users.enums.UserHierarchyLevelEnum;
import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eprocurement.admin.security.enums.UserRoles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_details",schema="masters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseAuditEntity implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name ="user_id")
	@Id
	private String userId;
	@Column(name="user_name")
	private String username;
	@Column(name="password")
	private String password;
	@Column(name="email")
	private String email;
	@Column(name="role")
	@Enumerated(EnumType.STRING)
	private UserRoles role;

	@Column(name = "first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;

	@Column(name = "hierarchy_level")
	@Enumerated(EnumType.STRING)
	private UserHierarchyLevelEnum hierarchyLevel;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private CommonStatusEnum status;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return role.getAuthorities();
	}

	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	private Set<UserPermissionEntity> userPermissionEntities;

	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	private Set<UserBuDetailsEntity> userBuDetailsEntities;
}
