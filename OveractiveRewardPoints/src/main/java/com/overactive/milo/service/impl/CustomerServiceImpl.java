package com.overactive.milo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.overactive.milo.entity.Customer;
import com.overactive.milo.repository.CustomerRepository;
import com.overactive.milo.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl  implements CustomerService
{
	private final CustomerRepository CUSTOMERREPOSITORY;
	
	private final Date currentDate = new Date();
	
	@Override
	public Customer saveCustomer(Customer customer) {
		Customer customer2 = getCustomerByCustomerId(customer.getCustomerId());
		
		if(null != customer2)
		{
			return updateCustomer(customer2);
		} 
		
		customer.setCreationDate(currentDate);
		customer2 = CUSTOMERREPOSITORY.save(customer);
		return customer2;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		customer.setModificationDate(currentDate);
		return CUSTOMERREPOSITORY.save(customer);
	}

	@Override
	public Customer getCustomerByCustomerId(String customerId) {
		return CUSTOMERREPOSITORY.findCustomerByCustomerId(customerId);
	}

	@Override
	public List<Customer> listCustomers() {
		return CUSTOMERREPOSITORY.findAll();
	}

}
