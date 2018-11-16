package com.huateng.po.mchnt;

/***
 * 检查商户关键字段是否重复及是否在黑名单中
 * 创建关键字段检查结果的对象类
 * 检查MCC是否为：8062，8211，8220，8398，9703，9704，9705，9706，9707，9708，9498
 * @author Administrator
 *
 */
public class CheckMchtInf {
	
	//检查商户名
	private java.lang.String ckMchtNm;
	//检查营业执照注册号
	private java.lang.String ckLicenceNo;
	//检查法人代表身份证号
	private java.lang.String ckIdentityNo;
	//检查法人电话
	private java.lang.String ckManagerTel;
	//检查组织机构代码证号
	private java.lang.String ckBankLicenceNo;
	//检查商户地址
	private java.lang.String ckAddr;
	//检查MCC
	private java.lang.String ckMCC;
	
	
	public java.lang.String getCkManagerTel() {
		return ckManagerTel;
	}
	public void setCkManagerTel(java.lang.String ckManagerTel) {
		this.ckManagerTel = ckManagerTel;
	}
	public java.lang.String getCkMchtNm() {
		return ckMchtNm;
	}
	public void setCkMchtNm(java.lang.String ckMchtNm) {
		this.ckMchtNm = ckMchtNm;
	}
	public java.lang.String getCkLicenceNo() {
		return ckLicenceNo;
	}
	public void setCkLicenceNo(java.lang.String ckLicenceNo) {
		this.ckLicenceNo = ckLicenceNo;
	}
	public java.lang.String getCkIdentityNo() {
		return ckIdentityNo;
	}
	public void setCkIdentityNo(java.lang.String ckIdentityNo) {
		this.ckIdentityNo = ckIdentityNo;
	}
	public java.lang.String getCkBankLicenceNo() {
		return ckBankLicenceNo;
	}
	public void setCkBankLicenceNo(java.lang.String ckBankLicenceNo) {
		this.ckBankLicenceNo = ckBankLicenceNo;
	}
	public java.lang.String getCkAddr() {
		return ckAddr;
	}
	public void setCkAddr(java.lang.String ckAddr) {
		this.ckAddr = ckAddr;
	}
	public java.lang.String getCkMCC() {
		return ckMCC;
	}
	public void setCkMCC(java.lang.String ckMCC) {
		this.ckMCC = ckMCC;
	}
	
	

}
