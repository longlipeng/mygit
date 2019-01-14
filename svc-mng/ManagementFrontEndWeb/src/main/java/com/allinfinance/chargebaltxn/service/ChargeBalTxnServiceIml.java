package com.allinfinance.chargebaltxn.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.allinfinance.charagebalTxn.dto.ChargeTxnDTO;
import com.allinfinance.chargebaltxn.dao.ChargeBalTxnDao;
import com.allinfinance.framework.dto.PageDataDTO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;

public class ChargeBalTxnServiceIml implements ChargeBalTxnService{
	private PageQueryDAO pageQueryDAO;
	private ChargeBalTxnDao chargeBalTxnDao;
	private CommonsDAO    commonsDAO;
	
	
	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public ChargeBalTxnDao getChargeBalTxnDao() {
		return chargeBalTxnDao;
	}

	public void setChargeBalTxnDao(ChargeBalTxnDao chargeBalTxnDao) {
		this.chargeBalTxnDao = chargeBalTxnDao;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	@Override
	public void insertChargeBal(ChargeTxnDTO dto) throws BizServiceException {
		Date date = new Date();
		SimpleDateFormat fromat = new SimpleDateFormat("yyyyMMdd");
		
		dto.setDateSettle(fromat.format(date));
		dto.setDateTxn(fromat.format(date));
		SimpleDateFormat fromat1 = new SimpleDateFormat("HHmmss");
		dto.setTimeTxn(fromat1.format(date));
		dto.setTxnStat("2");
		dto.setSeqNo(commonsDAO.getNextValueOfSequence("TB_MNG_CHARGE_BAL_TXN"));
		dto.setChargeAmt(String.format("%d", (int)(Double.parseDouble(dto.getChargeAmt())*100)));
//		dto.setChargeAmt(Double.parseDouble()*100+"");
		dto.setChargeFee(String.format("%d", (int)(Double.parseDouble(dto.getChargeFee())*100)));
//		dto.setChargeFee(Double.parseDouble(dto.getChargeFee())*100+"");
		chargeBalTxnDao.insert(dto);
		
	}

	@Override
	public PageDataDTO queryChargeBalTxn(ChargeTxnDTO dto)
			throws BizServiceException {
		// TODO Auto-generated method stub
		return pageQueryDAO.query("TB_MNG_CHARGE_BAl_TXN.selectChargeBalTxn", dto);
	}

}
