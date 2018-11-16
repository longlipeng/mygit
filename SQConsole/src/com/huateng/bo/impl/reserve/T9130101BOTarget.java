package com.huateng.bo.impl.reserve;

import com.huateng.bo.reserve.T9130101BO;
import com.huateng.dao.iface.reserve.TblReserveSettleDao;
import com.huateng.po.reserve.TblBalanceReserveQuery;
import com.huateng.po.reserve.TblFocusReserve;
import com.huateng.po.reserve.TblMchtSettleReserve;
import com.huateng.po.reserve.TblMchtSettleReserveTmp;
import com.huateng.po.reserve.TblPaymentReserve;

public class T9130101BOTarget implements T9130101BO {

	public TblReserveSettleDao tblReserveSettleDao;

	
	public String addRedemp(TblMchtSettleReserve tblMchtSettleReserve) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.addRedemp(tblMchtSettleReserve);
		return "00";
	}
	
	public TblMchtSettleReserve getReserve(String reserveId) {
		// TODO Auto-generated method stub
		return tblReserveSettleDao.getReserve(reserveId);
	}
	
	public TblMchtSettleReserveTmp getReserveTmp(String reserveId) {
		// TODO Auto-generated method stub
		return tblReserveSettleDao.getReserveTmp(reserveId);
	}

	public String addRedempTmp(TblMchtSettleReserveTmp tblMchtSettleReserveTmp) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.addRedempTmp(tblMchtSettleReserveTmp);
		return "00";
	}
	
	public String savaReserve(TblMchtSettleReserve tblMchtSettleReserve) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.savaReserve(tblMchtSettleReserve);
		return "00";
	}
	
	public String saveFocus(TblFocusReserve tblFocusReserve) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.saveFocus(tblFocusReserve);
		return "00";
	}
	
	public String upFocus(TblFocusReserve tblFocusReserve) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.upFocus(tblFocusReserve);
		return "00";
	}
	
	
	public TblReserveSettleDao getTblReserveSettleDao() {
		return tblReserveSettleDao;
	}
	public void setTblReserveSettleDao(TblReserveSettleDao tblReserveSettleDao) {
		this.tblReserveSettleDao = tblReserveSettleDao;
	}

	public String savePayment(TblPaymentReserve tblPaymentReserve) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.savePayment(tblPaymentReserve);
		return "00";
	}

	public String upPayment(TblPaymentReserve tblPaymentReserve) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.upPayment(tblPaymentReserve);
		return "00";
	}

	public String addBalance(TblBalanceReserveQuery tblBalanceReserveQuery) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.addBalance(tblBalanceReserveQuery);
		return "00";
	}


	
}
