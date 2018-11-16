package com.huateng.dao.iface.reserve;

import com.huateng.po.reserve.TblBalanceReserveQuery;
import com.huateng.po.reserve.TblFocusReserve;
import com.huateng.po.reserve.TblMchtSettleReserve;
import com.huateng.po.reserve.TblMchtSettleReserveTmp;
import com.huateng.po.reserve.TblPaymentReserve;

public interface TblReserveSettleDao {

	/**
	 * 根据id
	 * 正式表
	 * @param reserveId
	 * @return
	 */
	public TblMchtSettleReserve getReserve(String reserveId);
	
	/**
	 * 修改备款金额
	 * 正式表
	 * @param tblMchtSettleReserve
	 * @return
	 */
	public void addRedemp(TblMchtSettleReserve tblMchtSettleReserve);
	
	/**
	 * 根据id
	 * 临时表
	 * @param reserveId
	 * @return
	 */
	public TblMchtSettleReserveTmp getReserveTmp(String reserveId);
	
	/**
	 * 修改备款金额
	 * 临时表
	 * @param tblMchtSettleReserve
	 * @return
	 */
	public void addRedempTmp(TblMchtSettleReserveTmp tblMchtSettleReserveTmp);
	
	/**
	 * 新增
	 * 正式表
	 * @param tblMchtSettleReserve
	 * @return
	 */
	public void savaReserve(TblMchtSettleReserve tblMchtSettleReserve);
	
	/**
	 * 人行集中缴存备款交易表
	 * @param tblFocusReserve
	 * @return
	 */
	public void saveFocus(TblFocusReserve tblFocusReserve);
	
	/**
	 * 人行集中缴存备款交易表  修改
	 * @param tblFocusReserve
	 * @return
	 */
	public void upFocus(TblFocusReserve tblFocusReserve);
	
	/**
	 * 出款交易表   新增
	 * @param tblPaymentReserve
	 * @return
	 */
	public void savePayment(TblPaymentReserve tblPaymentReserve);
	
	/**
	 * 出款交易表   修改
	 * @param tblPaymentReserve
	 * @return
	 */
	public void upPayment(TblPaymentReserve tblPaymentReserve);
	
	/**
	 * 人行备付金余额查询记录 新增
	 * @param tblBalanceReserveQuery
	 * @return
	 */
	public void addBalance(TblBalanceReserveQuery tblBalanceReserveQuery);
	
}
