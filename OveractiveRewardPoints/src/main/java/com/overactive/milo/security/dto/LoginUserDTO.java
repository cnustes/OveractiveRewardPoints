package com.overactive.milo.security.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginUserDTO 
{
	@NotBlank
	private String userName;
	
	@NotBlank
	private String password;
}
