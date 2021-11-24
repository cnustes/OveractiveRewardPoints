package com.overactive.milo;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.overactive.milo.model.Customer;
import com.overactive.milo.model.Transaction;
import com.overactive.milo.repository.TransactionRepository;

@DataJpaTest
public class TransactionRepositoryMockTest 
{
	@Autowired
	TransactionRepository transactionRepository;
	
	@Test
	public void whenFindByCustomer_thenReturnListTransaction()
	{
		Transaction transaction = Transaction.builder()
				.amount(52)
				.pointTransaction(50)
				.creationDate(new Date())
				.customer(Customer.builder()
						.Id(1L)
						.customerId("999")
						.customerName("Emily")
						.creationDate(new Date()).build()).build();
		
		transactionRepository.save(transaction);
		
		List<Transaction> list = transactionRepository.findByCustomer(transaction.getCustomer());
		
		Assertions.assertThat(list.size()).isEqualTo(20);
	}
}
