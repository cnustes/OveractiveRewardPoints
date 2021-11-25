package com.overactive.milo.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.overactive.milo.security.enums.RoleNames;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ROLE_SECURITY")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RoleSecurity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE_NAME", unique = true, nullable = false)
	private RoleNames roleNames;

	public RoleSecurity(@NotNull RoleNames roleNames) {
		super();
		this.roleNames = roleNames;
	}

	
}
