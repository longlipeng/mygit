package com.allinfinance.batch.importOrder.service;

import java.io.InputStream;
import java.util.List;

import com.allinfinance.framework.dto.ImportServiceDTO;
import com.allinfinance.shangqi.gateway.dto.ApplyAndBindCardDTO;
import com.allinfinance.shangqi.gateway.dto.BatchCardHolderMessageDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.huateng.framework.exception.BizServiceException;
/**
 * 
 * @author wanglei
 *该接口是对于上汽的的卡较多的时候用于从文件中直接导入批量的持卡人信息，客户信息（客户信息其实就是持卡人信息）
 *直接组数据调用原来的接口进行实现
 */
public interface ImportService {
	public void batchProduceOrderForReadyOrder() throws BizServiceException;
	public void batchInsertCustomerAndCardHolderFromOffLine(BatchCardHolderMessageDTO batchCardHolderMessageDTO) throws BizServiceException;
	public void batchInsertCustomerAndCardHolderFromOnline(ApplyAndBindCardDTO applyAndBindCardDTO)throws BizServiceException;
	public void batchInsertIssue(BatchCardHolderMessageDTO batchCardHolderMessageDTO) throws BizServiceException;
	public ImportServiceDTO checkProduct(ImportServiceDTO importServiceDTO) throws BizServiceException;
	public ImportServiceDTO checkProductTwo(ImportServiceDTO importServiceDTO) throws BizServiceException;
	public ImportServiceDTO checkProductIdByCardholder(CardholderDTO cardholderDTO) throws BizServiceException;
	public ImportServiceDTO checkProductIdByCardholderTwo(CardholderDTO cardholderDTO) throws BizServiceException;
	public void batchInsertUnsignOrder(ImportServiceDTO importServiceDTO) throws BizServiceException;
}


