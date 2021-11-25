package com.overactive.milo.security.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.overactive.milo.security.entity.RoleSecurity;
import com.overactive.milo.security.enums.RoleNames;
import com.overactive.milo.security.repository.RoleRepository;

@Service
@Transactional
public class RoleService 
{
	@Autowired
	RoleRepository roleRepository;
	
	public RoleSecurity getRoleByRoleName(RoleNames roleName)
	{
		return roleRepository.findByRoleNames(roleName);
	}
	
	public void save(RoleSecurity role)
	{
		roleRepository.save(role);
	}
}
