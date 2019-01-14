package com.huateng.framework.db.ibatis.model;

public class UnionOrderKey {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column TB_UNION_ORDER.NEW_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	private String newOrder;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column TB_UNION_ORDER.OLD_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	private String oldOrder;

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column TB_UNION_ORDER.NEW_ORDER
	 * 
	 * @return the value of TB_UNION_ORDER.NEW_ORDER
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	public String getNewOrder() {
		return newOrder;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column TB_UNION_ORDER.NEW_ORDER
	 * 
	 * @param newOrder
	 *            the value for TB_UNION_ORDER.NEW_ORDER
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	public void setNewOrder(String newOrder) {
		this.newOrder = newOrder;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column TB_UNION_ORDER.OLD_ORDER
	 * 
	 * @return the value of TB_UNION_ORDER.OLD_ORDER
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	public String getOldOrder() {
		return oldOrder;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column TB_UNION_ORDER.OLD_ORDER
	 * 
	 * @param oldOrder
	 *            the value for TB_UNION_ORDER.OLD_ORDER
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	public void setOldOrder(String oldOrder) {
		this.oldOrder = oldOrder;
	}
}