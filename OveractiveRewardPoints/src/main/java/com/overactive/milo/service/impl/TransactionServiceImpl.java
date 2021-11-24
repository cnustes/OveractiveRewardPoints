package com.overactive.milo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.overactive.milo.model.Customer;
import com.overactive.milo.model.Transaction;
import com.overactive.milo.repository.TransactionRepository;
import com.overactive.milo.service.TransactionService;
import com.overactive.milo.util.Points;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService
{
	private final TransactionRepository TRANSACTIONREPOSITORY;
	
	private final Date currentDate = new Date();
	
	@Override
	public Transaction createTransaction(Transaction transaction) 
	{
		Long transactionId = transaction.getId();
		Transaction transaction2 = transactionId == null ? null : getTransactionById(transactionId);
		
		if(transaction2 != null)
		{
			return transaction2;
		}
		
		transaction.setPointTransaction(Points.calculatePointByTransaction(transaction));
		transaction.setCreationDate(currentDate);
		transaction2 = TRANSACTIONREPOSITORY.save(transaction);
		return transaction2;
	}

	@Override
	public Transaction updateTransaction(Transaction transaction) 
	{
		Long transactionId = transaction.getId();
		Transaction transaction2 = transactionId == null ? null : getTransactionById(transactionId);
		
		if(null != transaction2)
		{
			transaction2.setCustomer(transaction.getCustomer());
			transaction2.setAmount(transaction.getAmount());
			transaction2.setModificationDate(currentDate);
			transaction2.setPointTransaction(Points.calculatePointByTransaction(transaction));
			return TRANSACTIONREPOSITORY.save(transaction2);			
		}
		
		return null;		
	}

	@Override
	public List<Transaction> listTransactions() {
		return TRANSACTIONREPOSITORY.findAll();
	}

	@Override
	public List<Transaction> listTrasactionByCustomer(Customer customer) {
		return TRANSACTIONREPOSITORY.findByCustomer(customer);
	}

	@Override
	public Transaction getTransactionById(Long transactionId) {
		return TRANSACTIONREPOSITORY.findById(transactionId).orElse(null);
	}		

}
