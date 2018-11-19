package com.allinfinance.prepay.dao;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.seller.customer.CustomerDTO;

public interface AttachInfoDAO {
	
	public void insertAttachInfoDTO(CustomerDTO customerDTO,String type,String sex,String id) 
			throws BizServiceException;
	public void updateAttachInfoDTO(CustomerDTO customerDTO,String type,String sex,String id) 
			throws BizServiceException;
	public int selectAttachInfoDTO(CustomerDTO customerDTO,String type,String id) 
			throws BizServiceException;
}
