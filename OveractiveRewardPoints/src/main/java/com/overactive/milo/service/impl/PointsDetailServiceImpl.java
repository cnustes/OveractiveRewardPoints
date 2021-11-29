package com.overactive.milo.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.overactive.milo.dto.PointsDetailDTO;
import com.overactive.milo.dto.TransactionsDetailDTO;
import com.overactive.milo.entity.Customer;
import com.overactive.milo.entity.Transaction;
import com.overactive.milo.service.CustomerService;
import com.overactive.milo.service.PointsDetailService;
import com.overactive.milo.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointsDetailServiceImpl implements PointsDetailService
{
	
	private final CustomerService CUSTOMERSERVICE;
	private final TransactionService TRANSACTIONSERVICE;
	private final Date REPORTTIME = subtractMonths();

	@Override
	public List<PointsDetailDTO> lDetailDTOs(String customerId)
	{
		List<PointsDetailDTO> lDetailDTOs = new ArrayList<>();
		
		if(customerId == null)
		{
			List<Customer> liCustomers = CUSTOMERSERVICE.listCustomers();
			
			if(liCustomers.size() <= 0)
			{
				return lDetailDTOs;
			}
			
			for (Customer customer : liCustomers) 
			{
				detailDTOs(customer, lDetailDTOs);
			}
		} else
		{
			Customer customer = CUSTOMERSERVICE.getCustomerByCustomerId(customerId);
			if(customer == null)
			{
				return lDetailDTOs;
			}
			detailDTOs(customer, lDetailDTOs);
		}
		
		return lDetailDTOs;
	}
	
	private void detailDTOs(Customer customer, List<PointsDetailDTO> lDetailDTOs)
	{
		List<Transaction> lTransactions = TRANSACTIONSERVICE.listTrasactionByCustomer(customer);
		lTransactions.stream().filter(t -> t.getCreationDate().after(REPORTTIME) && t.getCreationDate().before(new Date()));
		
		Map<String, List<Transaction>> map = new HashMap<>();
		
		for (Transaction transaction : lTransactions) 
		{
			String monthName = monthName(transaction.getCreationDate());
			List<Transaction> list = new ArrayList<>();
			list.add(transaction);
			
			if(map.containsKey(monthName)) 
			{
				List<Transaction> tempList = map.get(monthName);
				tempList.add(transaction);
				map.put(monthName, tempList);
			} else
			{
				map.put(monthName, list);
			}
		}
		
		List<TransactionsDetailDTO> lTransactionsDetailDTOs = new ArrayList<>();
		
		for(String key : map.keySet())
		{
			List<Transaction> list = map.get(key);
			int sumPoints = list.stream().mapToInt(t -> t.getPointTransaction()).sum();
			
			TransactionsDetailDTO transactionsDetailDTO = new TransactionsDetailDTO();
			transactionsDetailDTO.setMonthTransaction(key);
			transactionsDetailDTO.setTotalPointsPerMonth(sumPoints);
			transactionsDetailDTO.setLiTransactions(list);
			
			lTransactionsDetailDTOs.add(transactionsDetailDTO);
		}
		
		int sumPoints = lTransactions.stream().mapToInt(t -> t.getPointTransaction()).sum();
		
		PointsDetailDTO pointsDetailDTO = new PointsDetailDTO();
		String customerIdIt = customer.getCustomerId();
		pointsDetailDTO.setCustomerId(customerIdIt);
		pointsDetailDTO.setCustomerName(customer.getCustomerName());
		pointsDetailDTO.setTotalPoints(sumPoints);
		pointsDetailDTO.setListTransactionsDetail(lTransactionsDetailDTOs);		
		
		lDetailDTOs.add(pointsDetailDTO);
	}
	
	private Date subtractMonths()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -3);
		return calendar.getTime();
	}
	
	private String monthName(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
	}

}
