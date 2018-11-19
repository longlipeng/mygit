package com.huateng.univer.entitybaseinfo.contact.biz.service;

import java.util.List;

import com.allinfinance.univer.entity.dto.ContactDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 联系人管理服务
 * 
 * @author wangjiaomiao
 * @since 1.0
 */
public interface ContactService {

	public List<ContactDTO> inqueryContact(String entityId)
			throws BizServiceException;

	public void insert(ContactDTO contactDTO) throws BizServiceException;

	public void insertContact(ContactDTO dto) throws BizServiceException;

	public void updateContact(ContactDTO dto) throws BizServiceException;

	public ContactDTO viewContact(ContactDTO dto) throws BizServiceException;

	public void deleteContact(ContactDTO dto) throws BizServiceException;

	public ContactDTO initContact(String refId, String refType)
			throws BizServiceException;

}