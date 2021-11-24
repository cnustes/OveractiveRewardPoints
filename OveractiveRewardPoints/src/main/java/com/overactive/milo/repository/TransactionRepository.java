package com.overactive.milo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.overactive.milo.model.Customer;
import com.overactive.milo.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>
{
	public List<Transaction> findByCustomer(Customer customer);		
}
	
