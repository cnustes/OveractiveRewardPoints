package com.overactive.milo.service;

import java.util.List;

import com.overactive.milo.model.Customer;

public interface CustomerService 
{
	public Customer saveCustomer(Customer customer);
	public Customer updateCustomer(Customer customer);
	public Customer getCustomerByCustomerId(String customerId);
	public List<Customer> listCustomers();
}
