package com.allinfinance.prepay.service;

import java.util.List;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.model.Customer;
import com.allinfinance.prepay.model.CustomerExample;
import com.allinfinance.univer.seller.customer.CustomerDTO;

public interface CustomerService {
	
	public CustomerDTO getCustomerByIdNo(CustomerDTO customerDTO)
			throws BizServiceException ;
	
	public CustomerDTO insertCustomer(CustomerDTO customerDTO)
			throws BizServiceException ;
	
	public CustomerDTO viewCustomer(CustomerDTO customerDTO)
			throws BizServiceException ;
	
	public List<Customer> selectByExample(CustomerExample example);

}
