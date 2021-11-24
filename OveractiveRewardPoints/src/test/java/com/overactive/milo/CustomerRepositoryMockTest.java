package com.overactive.milo;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.overactive.milo.model.Customer;
import com.overactive.milo.repository.CustomerRepository;

@DataJpaTest
public class CustomerRepositoryMockTest 
{
	@Autowired
	CustomerRepository customerRepository;
	
	@Test
	public void whenFindByCustomerId_thenReturnCustomer()
	{
		Customer customer = Customer.builder()
			.Id(Long.parseLong("4"))
			.customerId("2020")
			.customerName("Emily")
			.creationDate(new Date()).build();
		
		customerRepository.save(customer);
		
		Customer customer02 = customerRepository.findCustomerByCustomerId(customer.getCustomerId());
		
		Assertions.assertThat(customer02.getCustomerId()).isEqualTo("2020");
	}
}
