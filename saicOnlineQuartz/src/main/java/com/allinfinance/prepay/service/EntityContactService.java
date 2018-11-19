package com.allinfinance.prepay.service;

import java.util.List;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.entity.dto.ContactDTO;

public interface EntityContactService {
	public void insert(ContactDTO contactDTO) throws BizServiceException ;
	
	public void updateContact(ContactDTO dto) throws BizServiceException ;
	public List<ContactDTO> inqueryContact(String entityId)throws BizServiceException;

}
