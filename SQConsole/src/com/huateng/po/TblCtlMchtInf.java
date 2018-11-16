package com.huateng.po;

import java.io.Serializable;

public class TblCtlMchtInf implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblCtlMchtInf";//受控商户信息
	public static String PROP_SA_BRH_ID = "SaBrhId";
	public static String PROP_SA_MER_EN_NAME = "SaMerEnName";
	public static String PROP_SA_LIMIT_AMT = "SaLimitAmt";
	public static String PROP_SA_LAST_UPD_OPR = "SaLastUpdOpr";
	public static String PROP_SA_LAST_UPD_TS = "SaLastUpdTs";
	public static String PROP_SA_ACTION = "SaAction";
	public static String PROP_SA_ACTION_OLD = "SaActionOld";
	public static String PROP_SA_MER_CH_NAME = "SaMerChName";
	public static String PROP_SA_LIMIT_AMT_OLD = "SaLimitAmtOld";
	public static String PROP_SA_STATE = "SaState";
	public static String PROP_ID = "Id";
	public static String PROP_BANK_LICENCE_NO = "BankLicenceNo";
	public static String PROP_LICENCE_NO = "LicenceNo";
	public static String PROP_IDENTITY_NO = "IdentityNo";
	public static String SA_AREA = "SaArea";

	protected void initialize() {
	}

	public TblCtlMchtInf() {
		super();
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private String datePk;  //主键时间

	// fields
	private java.lang.String saMerNo;    //商户编号
	private java.lang.String saMerEnName;//英文名称
	private java.lang.String saLimitAmt;//受控金额
	private java.lang.String saAction;//受控动作

	private java.lang.String saZoneNo;//分公司号
	private java.lang.String saAdmiBranNo;//主管分行号
	private java.lang.String saConnOr;//联系人
	private java.lang.String saConnTel;//联系电话

	private java.lang.String saInitZoneNo;//创建分行
	private java.lang.String saInitOprId;//创建人ID
	private java.lang.String saModiZoneNo;//修改分行
	private java.lang.String saModiOprId;//修改人ID
	private java.lang.String saInitTime;//创建时间
	private java.lang.String saModiTime;//修改时间
	private String saState;           //状态
	private String saLimitAmtOld;    //修改前的受控金额
	private String saActionOld;      //修改前的受控动作
	private java.lang.String bankLicenceNo;  //机构代码证号码
	private java.lang.String identityNo;    //法人身份证号

	private java.lang.String saMerChName;//中文名称
	private String addType;   //添加方式
	private String managerTel;   //法人联系电话
	private String appRemark;    //申请备注
	private String saArea;    //地区
	private java.lang.String licenceNo;    //营业执照编号
	
	
	
	public java.lang.String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(java.lang.String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public java.lang.String getBankLicenceNo() {
		return bankLicenceNo;
	}

	public void setBankLicenceNo(java.lang.String bankLicenceNo) {
		this.bankLicenceNo = bankLicenceNo;
	}

	public java.lang.String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(java.lang.String identityNo) {
		this.identityNo = identityNo;
	}

	

	/**
	 * Return the value associated with the column: SA_MER_CH_NAME
	 */
	public java.lang.String getSaMerChName() {
		return saMerChName;
	}

	/**
	 * Set the value related to the column: SA_MER_CH_NAME
	 * 
	 * @param saMerChName
	 *            the SA_MER_CH_NAME value
	 */
	public void setSaMerChName(java.lang.String saMerChName) {
		this.saMerChName = saMerChName;
	}

	/**
	 * Return the value associated with the column: SA_MER_EN_NAME
	 */
	public java.lang.String getSaMerEnName() {
		return saMerEnName;
	}

	/**
	 * Set the value related to the column: SA_MER_EN_NAME
	 * 
	 * @param saMerEnName
	 *            the SA_MER_EN_NAME value
	 */
	public void setSaMerEnName(java.lang.String saMerEnName) {
		this.saMerEnName = saMerEnName;
	}

	/**
	 * Return the value associated with the column: SA_LIMIT_AMT
	 */
	public java.lang.String getSaLimitAmt() {
		return saLimitAmt;
	}

	/**
	 * Set the value related to the column: SA_LIMIT_AMT
	 * 
	 * @param saLimitAmt
	 *            the SA_LIMIT_AMT value
	 */
	public void setSaLimitAmt(java.lang.String saLimitAmt) {
		this.saLimitAmt = saLimitAmt;
	}

	/**
	 * Return the value associated with the column: SA_ACTION
	 */
	public java.lang.String getSaAction() {
		return saAction;
	}

	/**
	 * Set the value related to the column: SA_ACTION
	 * 
	 * @param saAction
	 *            the SA_ACTION value
	 */
	public void setSaAction(java.lang.String saAction) {
		this.saAction = saAction;
	}

	/**
	 * @return the saZoneNo
	 */
	public java.lang.String getSaZoneNo() {
		return saZoneNo;
	}

	/**
	 * @param saZoneNo
	 *            the saZoneNo to set
	 */
	public void setSaZoneNo(java.lang.String saZoneNo) {
		this.saZoneNo = saZoneNo;
	}

	/**
	 * @return the saAdmiBranNo
	 */
	public java.lang.String getSaAdmiBranNo() {
		return saAdmiBranNo;
	}

	/**
	 * @param saAdmiBranNo
	 *            the saAdmiBranNo to set
	 */
	public void setSaAdmiBranNo(java.lang.String saAdmiBranNo) {
		this.saAdmiBranNo = saAdmiBranNo;
	}

	/**
	 * @return the saConnOr
	 */
	public java.lang.String getSaConnOr() {
		return saConnOr;
	}

	/**
	 * @param saConnOr
	 *            the saConnOr to set
	 */
	public void setSaConnOr(java.lang.String saConnOr) {
		this.saConnOr = saConnOr;
	}

	/**
	 * @return the saConnTel
	 */
	public java.lang.String getSaConnTel() {
		return saConnTel;
	}

	/**
	 * @param saConnTel
	 *            the saConnTel to set
	 */
	public void setSaConnTel(java.lang.String saConnTel) {
		this.saConnTel = saConnTel;
	}

	/**
	 * @return the saInitZoneNo
	 */
	public java.lang.String getSaInitZoneNo() {
		return saInitZoneNo;
	}

	/**
	 * @param saInitZoneNo
	 *            the saInitZoneNo to set
	 */
	public void setSaInitZoneNo(java.lang.String saInitZoneNo) {
		this.saInitZoneNo = saInitZoneNo;
	}

	/**
	 * @return the saInitOprId
	 */
	public java.lang.String getSaInitOprId() {
		return saInitOprId;
	}

	/**
	 * @param saInitOprId
	 *            the saInitOprId to set
	 */
	public void setSaInitOprId(java.lang.String saInitOprId) {
		this.saInitOprId = saInitOprId;
	}

	/**
	 * @return the saModiZoneNo
	 */
	public java.lang.String getSaModiZoneNo() {
		return saModiZoneNo;
	}

	/**
	 * @param saModiZoneNo
	 *            the saModiZoneNo to set
	 */
	public void setSaModiZoneNo(java.lang.String saModiZoneNo) {
		this.saModiZoneNo = saModiZoneNo;
	}

	/**
	 * @return the saModiOprId
	 */
	public java.lang.String getSaModiOprId() {
		return saModiOprId;
	}

	/**
	 * @param saModiOprId
	 *            the saModiOprId to set
	 */
	public void setSaModiOprId(java.lang.String saModiOprId) {
		this.saModiOprId = saModiOprId;
	}

	/**
	 * @return the saInitTime
	 */
	public java.lang.String getSaInitTime() {
		return saInitTime;
	}

	/**
	 * @param saInitTime
	 *            the saInitTime to set
	 */
	public void setSaInitTime(java.lang.String saInitTime) {
		this.saInitTime = saInitTime;
	}

	/**
	 * @return the saModiTime
	 */
	public java.lang.String getSaModiTime() {
		return saModiTime;
	}

	/**
	 * @param saModiTime
	 *            the saModiTime to set
	 */
	public void setSaModiTime(java.lang.String saModiTime) {
		this.saModiTime = saModiTime;
	}

	public String getSaState() {
		return saState;
	}

	public void setSaState(String saState) {
		this.saState = saState;
	}

	public String getSaLimitAmtOld() {
		return saLimitAmtOld;
	}

	public void setSaLimitAmtOld(String saLimitAmtOld) {
		this.saLimitAmtOld = saLimitAmtOld;
	}

	public String getSaActionOld() {
		return saActionOld;
	}

	public void setSaActionOld(String saActionOld) {
		this.saActionOld = saActionOld;
	}
	

	/*public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.huateng.po.TblCtlMchtInf))
			return false;
		else {
			com.huateng.po.TblCtlMchtInf tblCtlMchtInf = (com.huateng.po.TblCtlMchtInf) obj;
			if (null == this.getId() || null == tblCtlMchtInf.getId())
				return false;
			else
				return (this.getId().equals(tblCtlMchtInf.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}*/

	public String getDatePk() {
		return datePk;
	}

	public void setDatePk(String datePk) {
		this.datePk = datePk;
	}

	public java.lang.String getSaMerNo() {
		return saMerNo;
	}

	public void setSaMerNo(java.lang.String saMerNo) {
		this.saMerNo = saMerNo;
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	public String getManagerTel() {
		return managerTel;
	}

	public void setManagerTel(String managerTel) {
		this.managerTel = managerTel;
	}

	public String getAppRemark() {
		return appRemark;
	}

	public void setAppRemark(String appRemark) {
		this.appRemark = appRemark;
	}

	public String toString() {
		return super.toString();
	}

	public String getSaArea() {
		return saArea;
	}

	public void setSaArea(String saArea) {
		this.saArea = saArea;
	}

}