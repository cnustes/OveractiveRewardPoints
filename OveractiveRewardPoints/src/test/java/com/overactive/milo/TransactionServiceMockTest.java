package com.overactive.milo;

import java.util.Date;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.overactive.milo.entity.Customer;
import com.overactive.milo.entity.Transaction;
import com.overactive.milo.repository.TransactionRepository;
import com.overactive.milo.service.TransactionService;
import com.overactive.milo.service.impl.TransactionServiceImpl;

@SpringBootTest
public class TransactionServiceMockTest
{
	@Mock
	private TransactionRepository transactionRepository;
	private TransactionService transactionService;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);
		transactionService = new TransactionServiceImpl(transactionRepository);
		Transaction transaction = Transaction.builder()
			.Id(1L)
			.amount(52)
			.pointTransaction(50)
			.creationDate(new Date())
			.customer(Customer.builder()
				.Id(1L)
				.customerId("999")
				.customerName("Emily")
				.creationDate(new Date()).build()).build();
		
		Mockito.when(transactionRepository.findById(1L))
			.thenReturn(Optional.of(transaction));
	}
	
	@Test
	public void whenValidGetId_ThenReturnTransaction() 
	{
		Transaction transaction = transactionService.getTransactionById(1L);
		Assertions.assertThat(transaction.getAmount()).isEqualTo(52);
	}
	
	
}
