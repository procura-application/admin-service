package com.eprocurement.admin.security.enums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRoles {

	BUYER,
	SUPPLIER,
	ADMIN;
	
	public List<SimpleGrantedAuthority> getAuthorities(){
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
		return authorities;
	}
}
