package com.overactive.milo.security.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.overactive.milo.security.entity.UserSecurity;
import com.overactive.milo.security.repository.UserRepository;

@Service
@Transactional
public class UserService 
{
	@Autowired
	UserRepository userRepository;
	
	public UserSecurity getUserByUserName(String username)
	{
		return userRepository.findByUserName(username);
	}
	
	public boolean isUserExistByUserName(String userName)
	{
		return userRepository.existsByUserName(userName);
	}
	
	public boolean isUserExistByEmail(String email)
	{
		return userRepository.existsByEmail(email);
	}
	
	public void save (UserSecurity user)
	{
		userRepository.save(user);
	}
}
