package com.overactive.milo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.overactive.milo.entity.Customer;
import com.overactive.milo.service.CustomerService;
import com.overactive.milo.util.ParameterMessages;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customers")
@CrossOrigin
public class CustomerRest 
{
	@Autowired
	CustomerService customerService;
	
	@ApiOperation(value = "Operation to obtain all customers.", notes = "The information comes from an embedded H2 database.")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParameterMessages.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParameterMessages.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParameterMessages.RESPONSE_OK) })
	@GetMapping
	public ResponseEntity<List<Customer>> listAllCustomers()
	{
		List<Customer> liCustomers = customerService.listCustomers();
		
		if(liCustomers.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(liCustomers);
	}
	
	@ApiOperation(value = "Operation to create a record of a customer.", notes = "The information is stored in an embedded H2 database.")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParameterMessages.ERROR_SERVER),
							@ApiResponse(code = 400, message = ParameterMessages.BAD_REQUEST),
							@ApiResponse(code = 404, message = ParameterMessages.ERROR_NO_DATA),
							@ApiResponse(code = 201, message = ParameterMessages.RESPONSE_OK_CREATE) })
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Object> createCustomer(@Valid @RequestBody Customer customer, BindingResult result)
	{
		log.info("Creating Customer : {}", customer);
        if (result.hasErrors())
        {
        	return new ResponseEntity<Object>(ParameterMessages.formatMessage(result), HttpStatus.BAD_REQUEST);
        }

        Customer customer2 = customerService.saveCustomer(customer);

        return  ResponseEntity.status(HttpStatus.CREATED).body(customer2);
		
	}

}
