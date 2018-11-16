package com.huateng.po.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the cst_city_code table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="cst_city_code"
 */

public abstract class BaseTblCityCode  implements Serializable {

	private static final long serialVersionUID = 1L;
	public static String REF = "TblCityCode";
	public static String PROP_CITY_CODE_OLD = "CityCodeOld";
	public static String PROP_MCHT_ADDR = "MchtAddr";
	public static String PROP_CITY_FLAG = "CityFlag";
	public static String PROP_CITY_CODE_NEW = "CityCodeNew";
	public static String PROP_MODI_TIME = "ModiTime";
	public static String PROP_CITY_NAME = "CityName";
	public static String PROP_MODI_OPR_ID = "ModiOprId";
	public static String PROP_INIT_OPR_ID = "InitOprId";
	public static String PROP_INIT_TIME = "InitTime";


	// constructors
	public BaseTblCityCode () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblCityCode (java.lang.String cityCodeNew) {
		this.setCityCodeNew(cityCodeNew);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTblCityCode (
		java.lang.String cityCodeNew,
		java.lang.String cityCodeOld,
		java.lang.String cityFlag,
		java.lang.String mchtAddr,
		java.lang.String cityName,
		java.lang.String initOprId,
		java.lang.String modiOprId,
		java.lang.String initTime,
		java.lang.String modiTime) {

		this.setCityCodeNew(cityCodeNew);
		this.setCityCodeOld(cityCodeOld);
		this.setCityFlag(cityFlag);
		this.setMchtAddr(mchtAddr);
		this.setCityName(cityName);
		this.setInitOprId(initOprId);
		this.setModiOprId(modiOprId);
		this.setInitTime(initTime);
		this.setModiTime(modiTime);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String cityCodeNew;

	// fields
	private java.lang.String cityCodeOld;
	private java.lang.String cityFlag;
	private java.lang.String mchtAddr;
	private java.lang.String cityName;
	private java.lang.String initOprId;
	private java.lang.String modiOprId;
	private java.lang.String initTime;
	private java.lang.String modiTime;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="CITY_CODE_NEW"
     */
	public java.lang.String getCityCodeNew () {
		return cityCodeNew;
	}

	/**
	 * Set the unique identifier of this class
	 * @param cityCodeNew the new ID
	 */
	public void setCityCodeNew (java.lang.String cityCodeNew) {
		this.cityCodeNew = cityCodeNew;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: CITY_CODE_OLD
	 */
	public java.lang.String getCityCodeOld () {
		return cityCodeOld;
	}

	/**
	 * Set the value related to the column: CITY_CODE_OLD
	 * @param cityCodeOld the CITY_CODE_OLD value
	 */
	public void setCityCodeOld (java.lang.String cityCodeOld) {
		this.cityCodeOld = cityCodeOld;
	}



	/**
	 * Return the value associated with the column: CITY_FLAG
	 */
	public java.lang.String getCityFlag () {
		return cityFlag;
	}

	/**
	 * Set the value related to the column: CITY_FLAG
	 * @param cityFlag the CITY_FLAG value
	 */
	public void setCityFlag (java.lang.String cityFlag) {
		this.cityFlag = cityFlag;
	}



	/**
	 * Return the value associated with the column: MCHT_ADDR
	 */
	public java.lang.String getMchtAddr () {
		return mchtAddr;
	}

	/**
	 * Set the value related to the column: MCHT_ADDR
	 * @param mchtAddr the MCHT_ADDR value
	 */
	public void setMchtAddr (java.lang.String mchtAddr) {
		this.mchtAddr = mchtAddr;
	}



	/**
	 * Return the value associated with the column: CITY_NAME
	 */
	public java.lang.String getCityName () {
		return cityName;
	}

	/**
	 * Set the value related to the column: CITY_NAME
	 * @param cityName the CITY_NAME value
	 */
	public void setCityName (java.lang.String cityName) {
		this.cityName = cityName;
	}



	/**
	 * Return the value associated with the column: INIT_OPR_ID
	 */
	public java.lang.String getInitOprId () {
		return initOprId;
	}

	/**
	 * Set the value related to the column: INIT_OPR_ID
	 * @param initOprId the INIT_OPR_ID value
	 */
	public void setInitOprId (java.lang.String initOprId) {
		this.initOprId = initOprId;
	}



	/**
	 * Return the value associated with the column: MODI_OPR_ID
	 */
	public java.lang.String getModiOprId () {
		return modiOprId;
	}

	/**
	 * Set the value related to the column: MODI_OPR_ID
	 * @param modiOprId the MODI_OPR_ID value
	 */
	public void setModiOprId (java.lang.String modiOprId) {
		this.modiOprId = modiOprId;
	}



	/**
	 * Return the value associated with the column: INIT_TIME
	 */
	public java.lang.String getInitTime () {
		return initTime;
	}

	/**
	 * Set the value related to the column: INIT_TIME
	 * @param initTime the INIT_TIME value
	 */
	public void setInitTime (java.lang.String initTime) {
		this.initTime = initTime;
	}



	/**
	 * Return the value associated with the column: MODI_TIME
	 */
	public java.lang.String getModiTime () {
		return modiTime;
	}

	/**
	 * Set the value related to the column: MODI_TIME
	 * @param modiTime the MODI_TIME value
	 */
	public void setModiTime (java.lang.String modiTime) {
		this.modiTime = modiTime;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblCityCode)) return false;
		else {
			com.huateng.po.TblCityCode tblCityCode = (com.huateng.po.TblCityCode) obj;
			if (null == this.getCityCodeNew() || null == tblCityCode.getCityCodeNew()) return false;
			else return (this.getCityCodeNew().equals(tblCityCode.getCityCodeNew()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getCityCodeNew()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getCityCodeNew().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}