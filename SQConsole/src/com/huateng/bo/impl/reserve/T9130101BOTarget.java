package com.huateng.bo.impl.reserve;

import com.huateng.bo.reserve.T9130101BO;
import com.huateng.dao.iface.reserve.TblReserveSettleDao;
import com.huateng.po.reserve.TblBalanceReserveQuery;
import com.huateng.po.reserve.TblFictitiousQuery;
import com.huateng.po.reserve.TblFocusReserve;
import com.huateng.po.reserve.TblFocusReserveTmp;
import com.huateng.po.reserve.TblHistoryQuery;
import com.huateng.po.reserve.TblMchtSettleReserve;
import com.huateng.po.reserve.TblMchtSettleReserveTmp;
import com.huateng.po.reserve.TblPaymentReserve;
import com.huateng.po.reserve.TblPaymentReserveTmp;

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

	public TblFocusReserve getFocus(String focusId) {
		// TODO Auto-generated method stub
		return tblReserveSettleDao.getFocus(focusId);
	}

	public TblPaymentReserve getPayment(String paymentId) {
		// TODO Auto-generated method stub
		return tblReserveSettleDao.getPayment(paymentId);
	}

	public String savePaymentTmp(TblPaymentReserveTmp tblPaymentReserveTmp) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.savePaymentTmp(tblPaymentReserveTmp);
		return "00";
	}

	public String upPaymentTmp(TblPaymentReserveTmp tblPaymentReserveTmp) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.upPaymentTmp(tblPaymentReserveTmp);
		return "00";
	}

	public TblPaymentReserveTmp getPaymentTmp(String paymentId) {
		// TODO Auto-generated method stub
		return tblReserveSettleDao.getPaymentTmp(paymentId);
	}

	public String delPaymentTmp(String paymentId) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.delPaymentTmp(paymentId);
		return "00";
	}

	public String saveFocusTmp(TblFocusReserveTmp tblFocusReserveTmp) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.saveFocusTmp(tblFocusReserveTmp);
		return "00";
	}

	public String upFocusTmp(TblFocusReserveTmp tblFocusReserveTmp) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.upFocusTmp(tblFocusReserveTmp);
		return "00";
	}

	public TblFocusReserveTmp getFocusTmp(String focusId) {
		// TODO Auto-generated method stub
		return tblReserveSettleDao.getFocusTmp(focusId);
	}

	public String delFocusTmp(String focusId) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.delFocusTmp(focusId);
		return "00";
	}

	public String saveFictitious(TblFictitiousQuery tblFictitiousQuery) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.saveFictitious(tblFictitiousQuery);
		return "00";
	}

	public String saveHistory(TblHistoryQuery tblHistoryQuery) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.saveHistory(tblHistoryQuery);
		return "00";
	}

	public String upBalance(TblBalanceReserveQuery tblBalanceReserveQuery) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.upBalance(tblBalanceReserveQuery);
		return "00";
	}

	public String upHistory(TblHistoryQuery tblHistoryQuery) {
		// TODO Auto-generated method stub
		tblReserveSettleDao.upHistory(tblHistoryQuery);
		return "00";
	}

	

	
}
