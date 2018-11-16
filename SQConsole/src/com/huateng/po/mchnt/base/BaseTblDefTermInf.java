package com.huateng.po.mchnt.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_DEF_TERM_INF table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_DEF_TERM_INF"
 */

public abstract class BaseTblDefTermInf  implements Serializable {

	public static String REF = "TblDefTermInf";
	public static String PROP_UPT_DATE = "uptDate";
	public static String PROP_IMEI = "imei";
	public static String PROP_OPR_ID = "oprId";
	public static String PROP_SN = "sn";
	public static String PROP_PHONE_NO = "phoneNo";
	public static String PROP_RESERVE10 = "reserve10";
	public static String PROP_TERM_NO = "termNo";
	public static String PROP_MCHT_REC_ID = "mchtRecId";
	public static String PROP_PRO_CODE = "proCode";
	public static String PROP_TERM_TYPE = "termType";
	public static String PROP_CITY_CODE = "cityCode";
	public static String PROP_COUNTRY = "country";
	public static String PROP_STATUS = "status";
	public static String PROP_TERM_MODEL = "termModel";
	public static String PROP_RESERVE1 = "reserve1";
	public static String PROP_CRT_DATE = "crtDate";
	public static String PROP_RESERVE2 = "reserve2";
	public static String PROP_RESERVE3 = "reserve3";
	public static String PROP_ADDRESS = "address";
	public static String PROP_RESERVE8 = "reserve8";
	public static String PROP_RESERVE9 = "reserve9";
	public static String PROP_REC_ID = "recId";
	public static String PROP_RESERVE4 = "reserve4";
	public static String PROP_RESERVE5 = "reserve5";
	public static String PROP_TERM_BRAND = "termBrand";
	public static String PROP_RESERVE6 = "reserve6";
	public static String PROP_RESERVE7 = "reserve7";


	// constructors
	public BaseTblDefTermInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblDefTermInf (java.lang.String recId) {
		this.setRecId(recId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String recId;

	// fields
	private java.lang.String mchtRecId;
	private java.lang.String termNo;
	private java.lang.String termType;
	private java.lang.String termBrand;
	private java.lang.String termModel;
	private java.lang.String sn;
	private java.lang.String phoneNo;
	private java.lang.String imei;
	private java.lang.String proCode;
	private java.lang.String cityCode;
	private java.lang.String country;
	private java.lang.String address;
	private java.lang.String status;
	private java.lang.String reserve1;
	private java.lang.String reserve2;
	private java.lang.String reserve3;
	private java.lang.String reserve4;
	private java.lang.String reserve5;
	private java.lang.String reserve6;
	private java.lang.String reserve7;
	private java.lang.String reserve8;
	private java.lang.String reserve9;
	private java.lang.String reserve10;
	private java.lang.String oprId;
	private java.lang.String crtDate;
	private java.lang.String uptDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="REC_ID"
     */
	public java.lang.String getRecId () {
		return recId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setRecId (java.lang.String recId) {
		this.recId = recId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: MCHT_REC_ID
	 */
	public java.lang.String getMchtRecId () {
		return mchtRecId;
	}

	/**
	 * Set the value related to the column: MCHT_REC_ID
	 * @param mchtRecId the MCHT_REC_ID value
	 */
	public void setMchtRecId (java.lang.String mchtRecId) {
		this.mchtRecId = mchtRecId;
	}



	/**
	 * Return the value associated with the column: TERM_NO
	 */
	public java.lang.String getTermNo () {
		return termNo;
	}

	/**
	 * Set the value related to the column: TERM_NO
	 * @param termNo the TERM_NO value
	 */
	public void setTermNo (java.lang.String termNo) {
		this.termNo = termNo;
	}



	/**
	 * Return the value associated with the column: TERM_TYPE
	 */
	public java.lang.String getTermType () {
		return termType;
	}

	/**
	 * Set the value related to the column: TERM_TYPE
	 * @param termType the TERM_TYPE value
	 */
	public void setTermType (java.lang.String termType) {
		this.termType = termType;
	}



	/**
	 * Return the value associated with the column: TERM_BRAND
	 */
	public java.lang.String getTermBrand () {
		return termBrand;
	}

	/**
	 * Set the value related to the column: TERM_BRAND
	 * @param termBrand the TERM_BRAND value
	 */
	public void setTermBrand (java.lang.String termBrand) {
		this.termBrand = termBrand;
	}



	/**
	 * Return the value associated with the column: TERM_MODEL
	 */
	public java.lang.String getTermModel () {
		return termModel;
	}

	/**
	 * Set the value related to the column: TERM_MODEL
	 * @param termModel the TERM_MODEL value
	 */
	public void setTermModel (java.lang.String termModel) {
		this.termModel = termModel;
	}



	/**
	 * Return the value associated with the column: SN
	 */
	public java.lang.String getSn () {
		return sn;
	}

	/**
	 * Set the value related to the column: SN
	 * @param sn the SN value
	 */
	public void setSn (java.lang.String sn) {
		this.sn = sn;
	}



	/**
	 * Return the value associated with the column: PHONE_NO
	 */
	public java.lang.String getPhoneNo () {
		return phoneNo;
	}

	/**
	 * Set the value related to the column: PHONE_NO
	 * @param phoneNo the PHONE_NO value
	 */
	public void setPhoneNo (java.lang.String phoneNo) {
		this.phoneNo = phoneNo;
	}



	/**
	 * Return the value associated with the column: IMEI
	 */
	public java.lang.String getImei () {
		return imei;
	}

	/**
	 * Set the value related to the column: IMEI
	 * @param imei the IMEI value
	 */
	public void setImei (java.lang.String imei) {
		this.imei = imei;
	}



	/**
	 * Return the value associated with the column: PRO_CODE
	 */
	public java.lang.String getProCode () {
		return proCode;
	}

	/**
	 * Set the value related to the column: PRO_CODE
	 * @param proCode the PRO_CODE value
	 */
	public void setProCode (java.lang.String proCode) {
		this.proCode = proCode;
	}



	/**
	 * Return the value associated with the column: CITY_CODE
	 */
	public java.lang.String getCityCode () {
		return cityCode;
	}

	/**
	 * Set the value related to the column: CITY_CODE
	 * @param cityCode the CITY_CODE value
	 */
	public void setCityCode (java.lang.String cityCode) {
		this.cityCode = cityCode;
	}



	/**
	 * Return the value associated with the column: COUNTRY
	 */
	public java.lang.String getCountry () {
		return country;
	}

	/**
	 * Set the value related to the column: COUNTRY
	 * @param country the COUNTRY value
	 */
	public void setCountry (java.lang.String country) {
		this.country = country;
	}



	/**
	 * Return the value associated with the column: ADDRESS
	 */
	public java.lang.String getAddress () {
		return address;
	}

	/**
	 * Set the value related to the column: ADDRESS
	 * @param address the ADDRESS value
	 */
	public void setAddress (java.lang.String address) {
		this.address = address;
	}



	/**
	 * Return the value associated with the column: STATUS
	 */
	public java.lang.String getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: STATUS
	 * @param status the STATUS value
	 */
	public void setStatus (java.lang.String status) {
		this.status = status;
	}



	/**
	 * Return the value associated with the column: RESERVE1
	 */
	public java.lang.String getReserve1 () {
		return reserve1;
	}

	/**
	 * Set the value related to the column: RESERVE1
	 * @param reserve1 the RESERVE1 value
	 */
	public void setReserve1 (java.lang.String reserve1) {
		this.reserve1 = reserve1;
	}



	/**
	 * Return the value associated with the column: RESERVE2
	 */
	public java.lang.String getReserve2 () {
		return reserve2;
	}

	/**
	 * Set the value related to the column: RESERVE2
	 * @param reserve2 the RESERVE2 value
	 */
	public void setReserve2 (java.lang.String reserve2) {
		this.reserve2 = reserve2;
	}



	/**
	 * Return the value associated with the column: RESERVE3
	 */
	public java.lang.String getReserve3 () {
		return reserve3;
	}

	/**
	 * Set the value related to the column: RESERVE3
	 * @param reserve3 the RESERVE3 value
	 */
	public void setReserve3 (java.lang.String reserve3) {
		this.reserve3 = reserve3;
	}



	/**
	 * Return the value associated with the column: RESERVE4
	 */
	public java.lang.String getReserve4 () {
		return reserve4;
	}

	/**
	 * Set the value related to the column: RESERVE4
	 * @param reserve4 the RESERVE4 value
	 */
	public void setReserve4 (java.lang.String reserve4) {
		this.reserve4 = reserve4;
	}



	/**
	 * Return the value associated with the column: RESERVE5
	 */
	public java.lang.String getReserve5 () {
		return reserve5;
	}

	/**
	 * Set the value related to the column: RESERVE5
	 * @param reserve5 the RESERVE5 value
	 */
	public void setReserve5 (java.lang.String reserve5) {
		this.reserve5 = reserve5;
	}



	/**
	 * Return the value associated with the column: RESERVE6
	 */
	public java.lang.String getReserve6 () {
		return reserve6;
	}

	/**
	 * Set the value related to the column: RESERVE6
	 * @param reserve6 the RESERVE6 value
	 */
	public void setReserve6 (java.lang.String reserve6) {
		this.reserve6 = reserve6;
	}



	/**
	 * Return the value associated with the column: RESERVE7
	 */
	public java.lang.String getReserve7 () {
		return reserve7;
	}

	/**
	 * Set the value related to the column: RESERVE7
	 * @param reserve7 the RESERVE7 value
	 */
	public void setReserve7 (java.lang.String reserve7) {
		this.reserve7 = reserve7;
	}



	/**
	 * Return the value associated with the column: RESERVE8
	 */
	public java.lang.String getReserve8 () {
		return reserve8;
	}

	/**
	 * Set the value related to the column: RESERVE8
	 * @param reserve8 the RESERVE8 value
	 */
	public void setReserve8 (java.lang.String reserve8) {
		this.reserve8 = reserve8;
	}



	/**
	 * Return the value associated with the column: RESERVE9
	 */
	public java.lang.String getReserve9 () {
		return reserve9;
	}

	/**
	 * Set the value related to the column: RESERVE9
	 * @param reserve9 the RESERVE9 value
	 */
	public void setReserve9 (java.lang.String reserve9) {
		this.reserve9 = reserve9;
	}



	/**
	 * Return the value associated with the column: RESERVE10
	 */
	public java.lang.String getReserve10 () {
		return reserve10;
	}

	/**
	 * Set the value related to the column: RESERVE10
	 * @param reserve10 the RESERVE10 value
	 */
	public void setReserve10 (java.lang.String reserve10) {
		this.reserve10 = reserve10;
	}



	/**
	 * Return the value associated with the column: OPR_ID
	 */
	public java.lang.String getOprId () {
		return oprId;
	}

	/**
	 * Set the value related to the column: OPR_ID
	 * @param oprId the OPR_ID value
	 */
	public void setOprId (java.lang.String oprId) {
		this.oprId = oprId;
	}



	/**
	 * Return the value associated with the column: CRT_DATE
	 */
	public java.lang.String getCrtDate () {
		return crtDate;
	}

	/**
	 * Set the value related to the column: CRT_DATE
	 * @param crtDate the CRT_DATE value
	 */
	public void setCrtDate (java.lang.String crtDate) {
		this.crtDate = crtDate;
	}



	/**
	 * Return the value associated with the column: UPT_DATE
	 */
	public java.lang.String getUptDate () {
		return uptDate;
	}

	/**
	 * Set the value related to the column: UPT_DATE
	 * @param uptDate the UPT_DATE value
	 */
	public void setUptDate (java.lang.String uptDate) {
		this.uptDate = uptDate;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.mchnt.TblDefTermInf)) return false;
		else {
			com.huateng.po.mchnt.TblDefTermInf tblDefTermInf = (com.huateng.po.mchnt.TblDefTermInf) obj;
			if (null == this.getRecId() || null == tblDefTermInf.getRecId()) return false;
			else return (this.getRecId().equals(tblDefTermInf.getRecId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getRecId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getRecId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}