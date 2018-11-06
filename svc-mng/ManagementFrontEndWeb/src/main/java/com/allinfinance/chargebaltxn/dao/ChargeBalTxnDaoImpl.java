package com.allinfinance.chargebaltxn.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.charagebalTxn.dto.ChargeTxnDTO;
import com.huateng.framework.exception.BizServiceException;

public class ChargeBalTxnDaoImpl extends SqlMapClientDaoSupport implements ChargeBalTxnDao {

	@Override
	public void insert(ChargeTxnDTO dto) throws BizServiceException {
		try{
			this.getSqlMapClientTemplate().insert("TB_MNG_CHARGE_BAl_TXN.insertChargeBalTxn",dto);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
 
}
