package com.overactive.milo.security.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.overactive.milo.security.dto.JwtDTO;
import com.overactive.milo.security.dto.LoginUserDTO;
import com.overactive.milo.security.dto.NewUserDTO;
import com.overactive.milo.security.entity.RoleSecurity;
import com.overactive.milo.security.entity.UserSecurity;
import com.overactive.milo.security.enums.RoleNames;
import com.overactive.milo.security.jwt.JwtProvider;
import com.overactive.milo.security.service.RoleService;
import com.overactive.milo.security.service.UserService;
import com.overactive.milo.util.ParameterMessages;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController 
{
	@Autowired
	PasswordEncoder passwordEncoder;	
	@Autowired
	AuthenticationManager authenticationManager;	
	@Autowired
	UserService userService;	
	@Autowired
	RoleService roleService;	
	@Autowired
	JwtProvider jwtProvider;
	
	@PostMapping("/newUser")
	public ResponseEntity<UserSecurity> newUser(@Valid @RequestBody NewUserDTO newUser, BindingResult result)
	{
		if(result.hasErrors())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ParameterMessages.formatMessage(result));
		}
		
		if(userService.isUserExistByUserName(newUser.getName()))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exist");
		}
		
		if(userService.isUserExistByEmail(newUser.getEmail()))
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exist");
		}
		
		UserSecurity user = new UserSecurity(newUser.getName(),newUser.getUserName(),newUser.getEmail(), passwordEncoder.encode(newUser.getPassword()));
		
		Set<RoleSecurity> roles = new HashSet<>();
		roles.add(roleService.getRoleByRoleName(RoleNames.ROLE_USER));
		
		if(newUser.getRoles().contains("admin"))
		{
			roles.add(roleService.getRoleByRoleName(RoleNames.ROLE_ADMIN));
		}
		
		user.setRoles(roles);
		userService.save(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);		
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtDTO>login(@Valid @RequestBody LoginUserDTO loginUser, BindingResult result)
	{
		if(result.hasErrors())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ParameterMessages.formatMessage(result));
		}
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		JwtDTO jwtDTO = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(jwtDTO);	
		
	}
}
