package com.overactive.milo.dto;


import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class PointsDetailDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String customerName;
	private String customerId;
	private int totalPoints;
	private List<TransactionsDetailDTO> listTransactionsDetail;

}
