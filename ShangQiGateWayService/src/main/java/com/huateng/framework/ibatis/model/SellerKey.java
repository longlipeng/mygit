package com.huateng.framework.ibatis.model;

public class SellerKey {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column TB_SELLER.ENTITY_ID
	 * 
	 * @abatorgenerated Sat Jan 08 16:26:26 CST 2011
	 */
	private String entityId;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to
	 * the database column TB_SELLER.FATHER_ENTITY_ID
	 * 
	 * @abatorgenerated Sat Jan 08 16:26:26 CST 2011
	 */
	private String fatherEntityId;

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column TB_SELLER.ENTITY_ID
	 * 
	 * @return the value of TB_SELLER.ENTITY_ID
	 * @abatorgenerated Sat Jan 08 16:26:26 CST 2011
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column TB_SELLER.ENTITY_ID
	 * 
	 * @param entityId
	 *            the value for TB_SELLER.ENTITY_ID
	 * @abatorgenerated Sat Jan 08 16:26:26 CST 2011
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method returns the
	 * value of the database column TB_SELLER.FATHER_ENTITY_ID
	 * 
	 * @return the value of TB_SELLER.FATHER_ENTITY_ID
	 * @abatorgenerated Sat Jan 08 16:26:26 CST 2011
	 */
	public String getFatherEntityId() {
		return fatherEntityId;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method sets the
	 * value of the database column TB_SELLER.FATHER_ENTITY_ID
	 * 
	 * @param fatherEntityId
	 *            the value for TB_SELLER.FATHER_ENTITY_ID
	 * @abatorgenerated Sat Jan 08 16:26:26 CST 2011
	 */
	public void setFatherEntityId(String fatherEntityId) {
		this.fatherEntityId = fatherEntityId;
	}
}