package com.huateng.bo.reserve;

import com.huateng.po.reserve.TblBalanceReserveQuery;
import com.huateng.po.reserve.TblFictitiousQuery;
import com.huateng.po.reserve.TblFocusReserve;
import com.huateng.po.reserve.TblFocusReserveTmp;
import com.huateng.po.reserve.TblHistoryQuery;
import com.huateng.po.reserve.TblMchtSettleReserve;
import com.huateng.po.reserve.TblMchtSettleReserveTmp;
import com.huateng.po.reserve.TblPaymentReserve;
import com.huateng.po.reserve.TblPaymentReserveTmp;

public interface T9130101BO {

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
	public String addRedemp(TblMchtSettleReserve tblMchtSettleReserve);
	
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
	public String addRedempTmp(TblMchtSettleReserveTmp tblMchtSettleReserveTmp);
	
	/**
	 * 新增
	 * 正式表
	 * @param tblMchtSettleReserve
	 * @return
	 */
	public String savaReserve(TblMchtSettleReserve tblMchtSettleReserve);
	
	/**
	 * 人行集中缴存备款交易表  新增
	 * @param tblFocusReserve
	 * @return
	 */
	public String saveFocus(TblFocusReserve tblFocusReserve);
	
	/**
	 * 人行集中缴存备款交易表  修改
	 * @param tblFocusReserve
	 * @return
	 */
	public String upFocus(TblFocusReserve tblFocusReserve);
	
	/**
	 * 人行集中缴存备款交易表  查询
	 * @param focusId
	 * @return
	 */
	public TblFocusReserve getFocus(String focusId);
	
	/**
	 * 回款交易表   新增
	 * @param tblPaymentReserve
	 * @return
	 */
	public String savePayment(TblPaymentReserve tblPaymentReserve);
	
	/**
	 * 回款交易表   修改
	 * @param tblPaymentReserve
	 * @return
	 */
	public String upPayment(TblPaymentReserve tblPaymentReserve);
	
	/**
	 * 回款交易表   查询
	 * @param paymentId
	 * @return
	 */
	public TblPaymentReserve getPayment(String paymentId);
	
	/**
	 * 人行备付金余额查询记录 新增
	 * @param tblBalanceReserveQuery
	 * @return
	 */
	public String addBalance(TblBalanceReserveQuery tblBalanceReserveQuery);
	
	/**
	 * 人行备付金余额查询记录 修改
	 * @param tblBalanceReserveQuery
	 * @return
	 */
	public String upBalance(TblBalanceReserveQuery tblBalanceReserveQuery);
	
	/**
	 * 回款交易表   新增
	 * @param tblPaymentReserveTmp
	 * @return
	 */
	public String savePaymentTmp(TblPaymentReserveTmp tblPaymentReserveTmp);
	
	/**
	 * 回款交易表   修改
	 * @param tblPaymentReserveTmp
	 * @return
	 */
	public String upPaymentTmp(TblPaymentReserveTmp tblPaymentReserveTmp);
	
	/**
	 * 回款交易表   查询
	 * @param paymentId
	 * @return
	 */
	public TblPaymentReserveTmp getPaymentTmp(String paymentId);
	
	/**
	 * 回款交易表   删除
	 * @param paymentId
	 * @return
	 */
	public String delPaymentTmp(String paymentId);
	
	/**
	 * 备款交易表   新增
	 * @param tblPaymentReserveTmp
	 * @return
	 */
	public String saveFocusTmp(TblFocusReserveTmp tblFocusReserveTmp);
	
	/**
	 * 备款交易表   修改
	 * @param tblPaymentReserveTmp
	 * @return
	 */
	public String upFocusTmp(TblFocusReserveTmp tblFocusReserveTmp);
	
	/**
	 * 备款交易表   查询
	 * @param paymentId
	 * @return
	 */
	public TblFocusReserveTmp getFocusTmp(String focusId);
	
	/**
	 * 备款交易表   删除
	 * @param paymentId
	 * @return
	 */
	public String delFocusTmp(String focusId);
	
	/**
	 * 虚拟记账余额查询
	 * @param tblFictitiousQuery
	 * @return
	 */
	public String saveFictitious(TblFictitiousQuery tblFictitiousQuery);
	
	/**
	 * 历史余额查询  新增
	 * @param tblHistoryQuery
	 * @return
	 */
	public String saveHistory(TblHistoryQuery tblHistoryQuery);
	
	/**
	 * 历史余额查询   修改
	 * @param tblHistoryQuery
	 * @return
	 */
	public String upHistory(TblHistoryQuery tblHistoryQuery);
	
}
