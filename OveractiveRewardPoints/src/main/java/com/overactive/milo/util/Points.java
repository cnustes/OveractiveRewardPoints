package com.overactive.milo.util;

import java.util.List;

import com.overactive.milo.entity.Transaction;

public class Points 
{
	public static int calculatePointLongByTransactions(List<Transaction> transactions)
	{
		transactions.stream().filter(x -> x.getAmount()> 50);
		
		int  points = 0;
				
		for (Transaction transaction : transactions) 
		{
			points = calculatePointByTransaction(transaction);
		}
		
		return points;
	}
	
	public static int calculatePointByTransaction(Transaction transaction)
	{
		int amountTransaction = transaction.getAmount();
		int over100 = amountTransaction - 100;
		
		int  points = 0;
		
		if(over100 > 0)
		{
			points += (over100 * 2);
		} 
		
		if(amountTransaction > 50)
		{
			points += 50;
		}
		
		return points;
	}

}
