package com.huateng.po.base;

public class TblBranchManage {
	private String id;//分公司号
	private String branchname;//分公司名称
	private String brancharea;//分公司所属地区
	private String phonenum;//分公司电话
	private String branchpos;//分公司邮编
	private String branchaddr;//分公司地址
	private String linkname;//联系人
	private String linkmanpho;//联系人电话
	private String linkmanmail;//联系人邮箱
	private String branchfax;//分公司传真
	private String branchdes;//分公司描述
	private String state;//公司状态
	private String creoprid;//创建人
	private String creoprdate;//创建时间
	private String upoprid;//修改人
	private String upoprdate;//修改时间
	private String branchLevel;//级别
	private String parentBranchId;//上级分公司编号
	private String branchCheckTime;//审核时间
	private String branchCheckOpr;//审核人
	
	public String getCreoprid() {
		return creoprid;
	}
	public void setCreoprid(String creoprid) {
		this.creoprid = creoprid;
	}
	public String getCreoprdate() {
		return creoprdate;
	}
	public void setCreoprdate(String creoprdate) {
		this.creoprdate = creoprdate;
	}
	public String getUpoprid() {
		return upoprid;
	}
	public void setUpoprid(String upoprid) {
		this.upoprid = upoprid;
	}
	public String getUpoprdate() {
		return upoprdate;
	}
	public void setUpoprdate(String upoprdate) {
		this.upoprdate = upoprdate;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public String getBranchfax() {
		return branchfax;
	}
	public void setBranchfax(String branchfax) {
		this.branchfax = branchfax;
	}
	public String getBrancharea() {
		return brancharea;
	}
	public void setBrancharea(String brancharea) {
		this.brancharea = brancharea;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getBranchpos() {
		return branchpos;
	}
	public void setBranchpos(String branchpos) {
		this.branchpos = branchpos;
	}
	public String getBranchaddr() {
		return branchaddr;
	}
	public void setBranchaddr(String branchaddr) {
		this.branchaddr = branchaddr;
	}
	public String getLinkname() {
		return linkname;
	}
	public void setLinkname(String linkname) {
		this.linkname = linkname;
	}
	public String getLinkmanpho() {
		return linkmanpho;
	}
	public void setLinkmanpho(String linkmanpho) {
		this.linkmanpho = linkmanpho;
	}
	public String getLinkmanmail() {
		return linkmanmail;
	}
	public void setLinkmanmail(String linkmanmail) {
		this.linkmanmail = linkmanmail;
	}
	
	public String getBranchdes() {
		return branchdes;
	}
	public void setBranchdes(String branchdes) {
		this.branchdes = branchdes;
	}
	public String getBranchLevel() {
		return branchLevel;
	}
	public void setBranchLevel(String branchLevel) {
		this.branchLevel = branchLevel;
	}
	public String getParentBranchId() {
		return parentBranchId;
	}
	public void setParentBranchId(String parentBranchId) {
		this.parentBranchId = parentBranchId;
	}
	public String getBranchCheckTime() {
		return branchCheckTime;
	}
	public void setBranchCheckTime(String branchCheckTime) {
		this.branchCheckTime = branchCheckTime;
	}
	public String getBranchCheckOpr() {
		return branchCheckOpr;
	}
	public void setBranchCheckOpr(String branchCheckOpr) {
		this.branchCheckOpr = branchCheckOpr;
	}
	
}
