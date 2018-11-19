package com.huateng.framework.ibatis.model;

public class SellOrderPaymentKey {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column TB_SELL_ORDER_PAYMENT.ORDER_ID
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	private String orderId;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column TB_SELL_ORDER_PAYMENT.PAYMENT_ID
	 * 
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	private String paymentId;

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column TB_SELL_ORDER_PAYMENT.ORDER_ID
	 * 
	 * @return the value of TB_SELL_ORDER_PAYMENT.ORDER_ID
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column TB_SELL_ORDER_PAYMENT.ORDER_ID
	 * 
	 * @param orderId
	 *            the value for TB_SELL_ORDER_PAYMENT.ORDER_ID
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column TB_SELL_ORDER_PAYMENT.PAYMENT_ID
	 * 
	 * @return the value of TB_SELL_ORDER_PAYMENT.PAYMENT_ID
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column TB_SELL_ORDER_PAYMENT.PAYMENT_ID
	 * 
	 * @param paymentId
	 *            the value for TB_SELL_ORDER_PAYMENT.PAYMENT_ID
	 * @abatorgenerated Thu Mar 29 16:35:26 CST 2012
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
}