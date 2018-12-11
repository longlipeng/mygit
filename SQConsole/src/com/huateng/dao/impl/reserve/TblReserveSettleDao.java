package com.huateng.dao.impl.reserve;

import com.huateng.dao._RootDAO;
import com.huateng.po.reserve.TblBalanceReserveQuery;
import com.huateng.po.reserve.TblFocusReserve;
import com.huateng.po.reserve.TblMchtSettleReserve;
import com.huateng.po.reserve.TblMchtSettleReserveTmp;
import com.huateng.po.reserve.TblPaymentReserve;

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

	
}
