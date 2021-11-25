package com.overactive.milo.security.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @ Setter @AllArgsConstructor
public class UserMain implements UserDetails
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String userName;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public static UserMain build(UserSecurity user)
	{
		List<GrantedAuthority> authorities =
				user.getRoles().stream().map(rol -> new SimpleGrantedAuthority(
						rol.getRoleNames().name())).collect(Collectors.toList());
		
		return new UserMain(user.getName(), user.getUserName(), user.getEmail(), user.getPassword(), authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
