package com.overactive.milo;

import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
	
	Customer customer;
	
	@BeforeEach
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);
		transactionService = new TransactionServiceImpl(transactionRepository);
		
		customer = Customer.builder()
				.Id(1L)
				.customerId("999")
				.customerName("Emily")
				.creationDate(new Date()).build();
		
		Transaction transaction = Transaction.builder()
			.Id(1L)
			.amount(52)
			.pointTransaction(50)
			.creationDate(new Date())
			.customer(customer).build();
		
		Mockito.when(transactionRepository.findById(1L))
			.thenReturn(Optional.of(transaction));
		
		Mockito.when(transactionRepository.findAll())
		.thenReturn(Arrays.asList(transaction));
		
		Mockito.when(transactionRepository.findByCustomer(customer))
		.thenReturn(Arrays.asList(transaction));
		
		Mockito.when(transactionRepository.save(any(Transaction.class)))
		.thenReturn(transaction);
	}
	
	@Test
	public void whenValidGetId_ThenReturnTransaction() 
	{
		Transaction transaction = transactionService.getTransactionById(1L);
		Assertions.assertThat(transaction.getAmount()).isEqualTo(52);
	}
	
	@Test
	public void whenFindAll_ThenReturnAllTransactions() 
	{
		List<Transaction> list = transactionService.listTransactions();
		Assertions.assertThat(list.size()).isGreaterThan(0);
	}
	
	@Test
	public void whenFindAllTransactionByCostumer_ThenReturnAllTransactionsByCostumer() 
	{
		List<Transaction> list = transactionService.listTrasactionByCustomer(customer);
		Assertions.assertThat(list.size()).isGreaterThan(0);
	}
	
	@Test
	public void whenSaveTransaction_ThenReturnTransactionId()
	{
		Transaction transaction = transactionService.createTransaction(new Transaction());
		Assertions.assertThat(transaction.getId()).isGreaterThan(0);
	}
	
	@Test
	public void whenUpdateTransaction_ThenReturnTransactionModifiedDate()
	{
		Transaction transactionUp = Transaction.builder()
				.Id(1L)
				.amount(52)
				.creationDate(new Date())
				.customer(Customer.builder()
					.Id(1L)
					.customerId("999")
					.customerName("Emily")
					.creationDate(new Date()).build()).build();
		
		Transaction transaction = transactionService.updateTransaction(transactionUp);
		Assertions.assertThat(transaction.getId()).isGreaterThan(0);
	}
	
	
}
