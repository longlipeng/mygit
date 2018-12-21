package com.huateng.dao.impl.reserve;

import com.huateng.dao._RootDAO;
import com.huateng.po.reserve.TblBalanceReserveQuery;
import com.huateng.po.reserve.TblFictitiousQuery;
import com.huateng.po.reserve.TblFocusReserve;
import com.huateng.po.reserve.TblFocusReserveTmp;
import com.huateng.po.reserve.TblHistoryQuery;
import com.huateng.po.reserve.TblMchtSettleReserve;
import com.huateng.po.reserve.TblMchtSettleReserveTmp;
import com.huateng.po.reserve.TblPaymentReserve;
import com.huateng.po.reserve.TblPaymentReserveTmp;

public class TblReserveSettleDao extends _RootDAO<TblMchtSettleReserve> implements com.huateng.dao.iface.reserve.TblReserveSettleDao {

	
	public TblReserveSettleDao(){}
	
	
	public Class<TblMchtSettleReserve> getReferenceClass() {
		// TODO Auto-generated method stub
		return TblMchtSettleReserve.class;
	}

	public void addRedemp(TblMchtSettleReserve tblMchtSettleReserve) {
		// TODO Auto-generated method stub
		super.update(tblMchtSettleReserve);
	}

	public TblMchtSettleReserve getReserve(String reserveId) {
		// TODO Auto-generated method stub
		return (TblMchtSettleReserve) super.get(getReferenceClass(), reserveId);
	}

	public TblMchtSettleReserveTmp getReserveTmp(String reserveId) {
		// TODO Auto-generated method stub
		return (TblMchtSettleReserveTmp) super.get(TblMchtSettleReserveTmp.class, reserveId);
	}

	public void addRedempTmp(TblMchtSettleReserveTmp tblMchtSettleReserve) {
		// TODO Auto-generated method stub
		super.update(tblMchtSettleReserve);
	}

	public void savaReserve(TblMchtSettleReserve tblMchtSettleReserve) {
		// TODO Auto-generated method stub
		super.save(tblMchtSettleReserve);
	}

	public void saveFocus(TblFocusReserve tblFocusReserve) {
		// TODO Auto-generated method stub
		super.save(tblFocusReserve);
	}

	public void upFocus(TblFocusReserve tblFocusReserve) {
		// TODO Auto-generated method stub
		super.update(tblFocusReserve);
	}

	public void savePayment(TblPaymentReserve tblPaymentReserve) {
		// TODO Auto-generated method stub
		super.save(tblPaymentReserve);
	}

	public void upPayment(TblPaymentReserve tblPaymentReserve) {
		// TODO Auto-generated method stub
		super.update(tblPaymentReserve);
	}

	public void addBalance(TblBalanceReserveQuery tblBalanceReserveQuery) {
		// TODO Auto-generated method stub
		super.save(tblBalanceReserveQuery);
	}

	public TblFocusReserve getFocus(String focusId) {
		// TODO Auto-generated method stub
		return (TblFocusReserve) super.get(TblFocusReserve.class, focusId);
	}

	public TblPaymentReserve getPayment(String paymentId) {
		// TODO Auto-generated method stub
		return (TblPaymentReserve) super.get(TblPaymentReserve.class , paymentId);
	}

	
	public void savePaymentTmp(TblPaymentReserveTmp tblPaymentReserveTmp) {
		// TODO Auto-generated method stub
		super.save(tblPaymentReserveTmp);
	}

	public void upPaymentTmp(TblPaymentReserveTmp tblPaymentReserveTmp) {
		// TODO Auto-generated method stub
		super.update(tblPaymentReserveTmp);
	}

	public TblPaymentReserveTmp getPaymentTmp(String paymentId) {
		// TODO Auto-generated method stub
		return (TblPaymentReserveTmp) super.get(TblPaymentReserveTmp.class, paymentId);
	}

	public void delPaymentTmp(String paymentId) {
		// TODO Auto-generated method stub
		super.delete(getPaymentTmp(paymentId));
	}

	public void saveFocusTmp(TblFocusReserveTmp tblFocusReserveTmp) {
		// TODO Auto-generated method stub
		super.save(tblFocusReserveTmp);
	}

	public void upFocusTmp(TblFocusReserveTmp tblFocusReserveTmp) {
		// TODO Auto-generated method stub
		super.update(tblFocusReserveTmp);
	}

	public TblFocusReserveTmp getFocusTmp(String focusId) {
		// TODO Auto-generated method stub
		return (TblFocusReserveTmp) super.get(TblFocusReserveTmp.class, focusId);
	}

	public void delFocusTmp(String focusId) {
		// TODO Auto-generated method stub
		super.delete(getFocusTmp(focusId));
	}

	public void saveFictitious(TblFictitiousQuery tblFictitiousQuery) {
		// TODO Auto-generated method stub
		super.save(tblFictitiousQuery);
	}

	public void saveHistory(TblHistoryQuery tblHistoryQuery) {
		// TODO Auto-generated method stub
		super.save(tblHistoryQuery);
	}

	
	
}
