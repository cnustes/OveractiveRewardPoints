package com.overactive.milo.service;


import java.util.List;

import com.overactive.milo.entity.Customer;
import com.overactive.milo.entity.Transaction;


public interface TransactionService 
{
	public Transaction createTransaction(Transaction transaction);
	public Transaction updateTransaction(Transaction transaction);
	public List<Transaction> listTransactions();
	public List<Transaction> listTrasactionByCustomer(Customer customer);
	public Transaction getTransactionById(Long transactionId);	
}
