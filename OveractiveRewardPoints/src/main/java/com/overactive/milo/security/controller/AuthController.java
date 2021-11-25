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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	
	@ApiOperation(value = "Operation that allows to create a user to generate a token.", notes = "The word admin must be sent in role to be able to execute the create and update services.")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParameterMessages.ERROR_SERVER),
							@ApiResponse(code = 400, message = ParameterMessages.BAD_REQUEST),
							@ApiResponse(code = 404, message = ParameterMessages.ERROR_NO_DATA),
							@ApiResponse(code = 200, message = ParameterMessages.RESPONSE_OK) })
	@PostMapping("/newUser")
	public ResponseEntity<?> newUser(@Valid @RequestBody NewUserDTO newUser, BindingResult result)
	{
		if(result.hasErrors())
		{
			return new ResponseEntity<Object>(ParameterMessages.formatMessage(result), HttpStatus.BAD_REQUEST);
			
		}
		
		if(userService.isUserExistByUserName(newUser.getUserName()))
		{
			return new ResponseEntity<Object>(ParameterMessages.especificError("username", "UserName already exist"), HttpStatus.BAD_REQUEST);
		}
		
		if(userService.isUserExistByEmail(newUser.getEmail()))
		{
			return new ResponseEntity<Object>(ParameterMessages.especificError("email", "Email already exist"), HttpStatus.BAD_REQUEST);			
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
	
	@ApiOperation(value = "Operation to obtain a token.", notes = "The scope of the token will depend on the user who generates it.")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParameterMessages.ERROR_SERVER),
							@ApiResponse(code = 400, message = ParameterMessages.BAD_REQUEST),
							@ApiResponse(code = 404, message = ParameterMessages.ERROR_NO_DATA),
							@ApiResponse(code = 200, message = ParameterMessages.RESPONSE_OK) })
	@PostMapping("/login")
	public ResponseEntity<?>login(@Valid @RequestBody LoginUserDTO loginUser, BindingResult result)
	{
		if(result.hasErrors())
		{
			return new ResponseEntity<Object>(ParameterMessages.formatMessage(result), HttpStatus.BAD_REQUEST);
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
