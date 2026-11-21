package com.eprocurement.admin.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eprocurement.admin.security.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{
	
	public Optional<User> findByUsername(String username);
	public Optional<User> findByEmail(String email);

}
