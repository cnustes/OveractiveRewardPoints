package com.overactive.milo.security.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER_TOKEN")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor 
public class UserSecurity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME_USER")
	private String name;
	
	@Column(name = "USERNAME", unique = true, nullable = false)
	private String userName;
	
	@Email
	@Column(name = "USER_EMAIL", unique = true, nullable = false)
	private String email;
	
	@Column(name = "USER_PASSWORD", nullable = false)
	private String password;
	
	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROL", joinColumns = @JoinColumn(name = "USER_ID"), 
			inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private Set<RoleSecurity> roles = new HashSet<>();

	public UserSecurity(@NotNull String name, String userName, @NotNull String email, @NotNull String password) {
		super();
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
	
	
	
}
