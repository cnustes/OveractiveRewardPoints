package com.overactive.milo.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.overactive.milo.security.entity.UserSecurity;

@Repository
public interface UserRepository extends JpaRepository<UserSecurity, Long> 
{
	public UserSecurity findByUserName(String userName);
	public boolean existsByUserName(String userName);
	public boolean existsByEmail(String email);
}
