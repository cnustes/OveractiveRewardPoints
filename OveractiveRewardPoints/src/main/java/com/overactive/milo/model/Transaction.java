package com.overactive.milo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TRANSACTION")
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Transaction implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@NotNull(message = "Creation date cannot be empty.")
	@Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
	
	@Column(name = "MODIFICATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modificationDate;
	
	@Positive(message = "The transaction value must be greater than 0.")
	@Column(name = "AMOUNT_TRANSACTION")
	private int amount;
	
	@Column(name = "POINT_TRANSACTION")
	private int pointTransaction;
	
	@NotNull(message = "Customer identification cannot be empty.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Customer customer;
}
