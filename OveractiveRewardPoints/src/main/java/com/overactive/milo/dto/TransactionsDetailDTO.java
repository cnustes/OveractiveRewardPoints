package com.overactive.milo.dto;

import java.io.Serializable;
import java.util.List;

import com.overactive.milo.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class TransactionsDetailDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String monthTransaction;
	private int totalPointsPerMonth;
	private List<Transaction> liTransactions;

}
