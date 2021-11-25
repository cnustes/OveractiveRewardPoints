package com.overactive.milo.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.overactive.milo.security.entity.RoleSecurity;
import com.overactive.milo.security.enums.RoleNames;

@Repository
public interface RoleRepository extends JpaRepository<RoleSecurity, Long> 
{
	public RoleSecurity findByRoleNames(RoleNames roleNames);
}
