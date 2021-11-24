package com.overactive.milo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.overactive.milo.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> 
{
	public Customer findCustomerByCustomerId(String customerId);
}
