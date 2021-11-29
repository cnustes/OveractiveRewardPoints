package com.overactive.milo.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.overactive.milo.security.entity.UserMain;
import com.overactive.milo.security.entity.UserSecurity;
import com.overactive.milo.security.service.UserService;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		UserSecurity user = userService.getUserByUserName(username);
		return UserMain.build(user);
	}

}
