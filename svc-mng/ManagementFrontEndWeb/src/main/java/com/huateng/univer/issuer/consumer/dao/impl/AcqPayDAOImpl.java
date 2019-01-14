package com.huateng.univer.issuer.consumer.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.univer.issuer.dto.consumer.AcqPayDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.issuer.consumer.dao.AcqPayDAO;

public class AcqPayDAOImpl extends SqlMapClientDaoSupport implements AcqPayDAO {
	public void delBank(AcqPayDTO acqPayDTO) throws BizServiceException {
		this.getSqlMapClientTemplate().delete("CONSUMER.delBank", acqPayDTO);
	}

	public void delete(AcqPayDTO acqPayDTO) throws BizServiceException {
		try {
			this.getSqlMapClientTemplate()
					.delete("CONSUMER.delCert", acqPayDTO);
			this.getSqlMapClientTemplate().delete("CONSUMER.delCardProduct",
					acqPayDTO);
			this.getSqlMapClientTemplate()
					.delete("CONSUMER.delBank", acqPayDTO);
			this.getSqlMapClientTemplate().delete("CONSUMER.delCms", acqPayDTO);
			this.getSqlMapClientTemplate().delete("CONSUMER.delSysPara",
					acqPayDTO);
		} catch (DataAccessException e) {
			
			this.logger.error(e.getMessage());
		}
	}

	public void deleteCardProduct(AcqPayDTO acqPayDTO)
			throws BizServiceException {
		this.getSqlMapClientTemplate().delete("CONSUMER.delCardProduct",
				acqPayDTO);
	}
}
