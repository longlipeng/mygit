package com.allinfinance.shangqi.gateway.service;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.shangqi.gateway.dto.TxnqueryDTO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.consumer.merchant.biz.service.impl.MerchantServiceImpl;

public class TxnqueryServiceImpl implements TxnqueryService{
	Logger logger = Logger.getLogger(TxnqueryServiceImpl.class);
	
	private PageQueryDAO pageQueryDAO;
	private TxnqueryDTO txnQueryDAO;
	
	
	

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public TxnqueryDTO getTxnQueryDAO() {
		return txnQueryDAO;
	}

	public void setTxnQueryDAO(TxnqueryDTO txnQueryDAO) {
		this.txnQueryDAO = txnQueryDAO;
	}

	@Override
	public PageDataDTO inqueryTxnRecord(TxnqueryDTO txnQueryDTO)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("TxnRecord.selectTxnRecordByDTO",
					txnQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			e.printStackTrace();
			throw new BizServiceException("查询客户失败");
		}
	}
	

}
