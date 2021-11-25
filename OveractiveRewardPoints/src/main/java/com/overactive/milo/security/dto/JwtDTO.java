package com.overactive.milo.security.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class JwtDTO 
{
	private String token;
	private final String BEARER = "Bearer";
	private String userName;
	private Collection<? extends GrantedAuthority> authorities;

}
