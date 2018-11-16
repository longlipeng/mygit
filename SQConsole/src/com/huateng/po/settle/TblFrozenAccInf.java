package com.huateng.po.settle;

public class TblFrozenAccInf {
    private String id;
	private String frozenDate;  //冻结日期
    private String mchtNo;      //商户号
    private String termId;      //终端号
    private String frozenAccount;    //冻结金额
    private String frozenAccountFinish;   //已冻结金额
    private String frozenAccountNoFinish;  //未完成冻结金额
    private String frozenRoute;           //冻结方式
    private String frozenReason;           //冻结备注
    private String frozenFinishFlag;       //冻结完成标志
    private String oprFlag;               // OPR_FLAG  CHAR(1)
    private String stats;               // 审核状态
    private String recOpr;               // 审核状态
    private String desId;               // 后台渠道
    
	public String getDesId() {
		return desId;
	}
	public void setDesId(String desId) {
		this.desId = desId;
	}
	public String getRecOpr() {
		return recOpr;
	}
	public void setRecOpr(String recOpr) {
		this.recOpr = recOpr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFrozenDate() {
		return frozenDate;
	}
	public void setFrozenDate(String frozenDate) {
		this.frozenDate = frozenDate;
	}
	public String getMchtNo() {
		return mchtNo;
	}
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getFrozenAccountFinish() {
		return frozenAccountFinish;
	}
	public void setFrozenAccountFinish(String frozenAccountFinish) {
		this.frozenAccountFinish = frozenAccountFinish;
	}
	public String getFrozenAccountNoFinish() {
		return frozenAccountNoFinish;
	}
	public void setFrozenAccountNoFinish(String frozenAccountNoFinish) {
		this.frozenAccountNoFinish = frozenAccountNoFinish;
	}
	public String getFrozenRoute() {
		return frozenRoute;
	}
	public void setFrozenRoute(String frozenRoute) {
		this.frozenRoute = frozenRoute;
	}
	public String getFrozenReason() {
		return frozenReason;
	}
	public void setFrozenReason(String frozenReason) {
		this.frozenReason = frozenReason;
	}
	public String getFrozenFinishFlag() {
		return frozenFinishFlag;
	}
	public void setFrozenFinishFlag(String frozenFinishFlag) {
		this.frozenFinishFlag = frozenFinishFlag;
	}
	public String getOprFlag() {
		return oprFlag;
	}
	public void setOprFlag(String oprFlag) {
		this.oprFlag = oprFlag;
	}
	public String getFrozenAccount() {
		return frozenAccount;
	}
	public void setFrozenAccount(String frozenAccount) {
		this.frozenAccount = frozenAccount;
	}
	public String getStats() {
		return stats;
	}
	public void setStats(String stats) {
		this.stats = stats;
	}
    
}
