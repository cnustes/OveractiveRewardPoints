package com.overactive.milo.controller;

import java.util.ArrayList;
import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import com.overactive.milo.dto.PointsDetailDTO;
import com.overactive.milo.entity.Transaction;
import com.overactive.milo.service.PointsDetailService;
import com.overactive.milo.service.TransactionService;
import com.overactive.milo.util.ParameterMessages;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/transactions")
public class TransactionRest 
{
	@Autowired
	TransactionService transactionService;
	@Autowired
	PointsDetailService pointsDetailService;
	
	@ApiOperation(value = "Operation to obtain all customers.", notes = "The information comes from an embedded H2 database.")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParameterMessages.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParameterMessages.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParameterMessages.RESPONSE_OK) })
	@GetMapping("/list")
	public ResponseEntity<List<Transaction>> listAllTransactions()
	{
		List<Transaction> lTransactions = transactionService.listTransactions();
		
		if(lTransactions.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(lTransactions);
	}
	
	@ApiOperation(value = "Operation that allows to obtain the record of a transaction by its ID.", notes = "The information comes from an embedded H2 database.")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParameterMessages.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParameterMessages.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParameterMessages.RESPONSE_OK) })
	@GetMapping(value = "/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("transactionId") Long transactionId ) 
	{
		Transaction transaction = transactionService.getTransactionById(transactionId);
	    
        if (null == transaction) {
        	return  ResponseEntity.notFound().build();
        } 

        return  ResponseEntity.ok(transaction);
    }
	
	@ApiOperation(value = "Operation to create a record of a transaction.", notes = "The information is stored in an embedded H2 database.")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParameterMessages.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParameterMessages.ERROR_NO_DATA),
			@ApiResponse(code = 201, message = ParameterMessages.RESPONSE_OK_CREATE) })
	@PostMapping
	public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction, BindingResult result)
	{
		log.info("Creating Transaction : {}", transaction);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ParameterMessages.formatMessage(result));
        }

        Transaction customerDB = transactionService.createTransaction(transaction);

        return  ResponseEntity.status(HttpStatus.CREATED).body(customerDB);
		
	}
	
	@ApiOperation(value = "Operation to update a record of a transaction.", notes = "The information is stored in an embedded H2 database.")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParameterMessages.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParameterMessages.ERROR_NO_DATA),
			@ApiResponse(code = 201, message = ParameterMessages.RESPONSE_OK_CREATE) })
	@PutMapping(value = "/{id}")
	public ResponseEntity<Transaction> updateTransaction(@PathVariable("id") Long transactionId,@Valid @RequestBody Transaction transaction, BindingResult result)
	{
		log.info("Updating Transaction : {}", transaction);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ParameterMessages.formatMessage(result));
        }
		
		transaction.setId(transactionId);
		Transaction transactionDB =  transactionService.updateTransaction(transaction);
        if (transactionDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactionDB);		
	}	
	
	@ApiOperation(value = "Operation that allows to obtain the detail of points and transactions of all or a single client in a period of 3 months.", notes = "The information comes from an embedded H2 database.")
	@ApiResponses(value = { @ApiResponse(code = 500, message = ParameterMessages.ERROR_SERVER),
			@ApiResponse(code = 404, message = ParameterMessages.ERROR_NO_DATA),
			@ApiResponse(code = 200, message = ParameterMessages.RESPONSE_OK) })
	@GetMapping
    public ResponseEntity<List<PointsDetailDTO>> listPointsDetail(@RequestParam(name = "customerId" , required = false) String customerId ) 
	{
		List<PointsDetailDTO> lDetailDTOs = new ArrayList<>();
		
		lDetailDTOs = pointsDetailService.lDetailDTOs(customerId);
	    
        if (null == customerId && lDetailDTOs.size() <= 0) {
           return  ResponseEntity.noContent().build();
        } else if(lDetailDTOs.size() <= 0)
        {
        	return  ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(lDetailDTOs);
    }
	
}
