package com.huateng.po.risk;

public class TblWhiteList {
	private String uuid;   // 自增主键
    private String mchtNo;  //商户号
    private String beginDt;  //起始日期
    private String validity; //有效期
    private String insOpr;   //申请人
    private String insDt;    //申请时间
    private String updOpr;   //审核人
    private String updDt;    //审核时间
    private String state;    //状态
    private String appRemark;//申请备注
    private String addType;   //添加方式
	
    
    
    public TblWhiteList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TblWhiteList(String uuid, String mchtNo, String beginDt,
			String validity, String insOpr, String insDt, String updOpr,
			String updDt, String state, String appRemark, String addType) {
		super();
		this.uuid = uuid;
		this.mchtNo = mchtNo;
		this.beginDt = beginDt;
		this.validity = validity;
		this.insOpr = insOpr;
		this.insDt = insDt;
		this.updOpr = updOpr;
		this.updDt = updDt;
		this.state = state;
		this.appRemark = appRemark;
		this.addType = addType;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getMchtNo() {
		return mchtNo;
	}
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	public String getBeginDt() {
		return beginDt;
	}
	public void setBeginDt(String beginDt) {
		this.beginDt = beginDt;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getInsOpr() {
		return insOpr;
	}
	public void setInsOpr(String insOpr) {
		this.insOpr = insOpr;
	}
	public String getInsDt() {
		return insDt;
	}
	public void setInsDt(String insDt) {
		this.insDt = insDt;
	}
	public String getUpdOpr() {
		return updOpr;
	}
	public void setUpdOpr(String updOpr) {
		this.updOpr = updOpr;
	}
	public String getUpdDt() {
		return updDt;
	}
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAppRemark() {
		return appRemark;
	}
	public void setAppRemark(String appRemark) {
		this.appRemark = appRemark;
	}
	public String getAddType() {
		return addType;
	}
	public void setAddType(String addType) {
		this.addType = addType;
	}
	
    
    
}
