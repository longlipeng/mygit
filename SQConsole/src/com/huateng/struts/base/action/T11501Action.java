package com.huateng.struts.base.action;

import java.util.List;

import com.huateng.bo.base.T10206BO;
import com.huateng.bo.base.T11501BO;
import com.huateng.common.Constants;
import com.huateng.po.base.TblAgentInfo;
import com.huateng.po.base.TblEmvPara;
import com.huateng.po.base.TblEmvParaPK;
import com.huateng.po.risk.TblWhiteList;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

public class T11501Action extends BaseAction {
    
	private T11501BO t11501BO = (T11501BO) ContextUtil.getBean("T11501BO");
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("delete".equals(method)) {
			rspCode = delete();
		} else if("add".equals(method)) {
			rspCode = add();
		} else if("update".equals(method)) {
			rspCode = update();
		}
		return rspCode;
	}
	
	
	
	private String delete() throws Exception{
		
		t11501BO.delete(agentNo);
		
		return Constants.SUCCESS_CODE;
	}
	
	
	private String add() throws Exception{
		TblAgentInfo tblAgentInfo = new TblAgentInfo();
		/*BeanUtils.copyProperties(tblAgentInfo, this);
		if(t11501BO.get(agentNo) != null){
			return "代理商已存在";
		}*/
		/*System.out.println(agentNo);
		tblAgentInfo.setAgentNo(agentNo);*/
		
		//自增+1
		String sql = "select max(agent_no) from tbl_agent_info";
		String agentNoAdd = "";
	    
		agentNoAdd = (String)commQueryDAO.findBySQLQuery(sql.toString()).get(0);

		if(agentNoAdd == null){
			agentNoAdd = "0000";
		}else{
			int i = Integer.parseInt(agentNoAdd);
			agentNoAdd = String.format("%04d", i+1);
		}
		
		/*String sql = "select agent_no from tbl_agent_info";
		List list = commQueryDAO.findBySQLQuery(sql.toString());
		
		String agentNoAdd = GenerateNextId.agentNo(list,4);*/
		
		tblAgentInfo.setAgentNo(agentNoAdd);
		tblAgentInfo.setAgentNm(agentNm);
		tblAgentInfo.setAgentProvince(agentProvince);
		tblAgentInfo.setAgentCity(agentCity);
		tblAgentInfo.setZipCode(zipCode);
		tblAgentInfo.setAgentConnNm(agentConnNm);
		tblAgentInfo.setAgentConnTel(agentConnTel);
		tblAgentInfo.setAddress(address);
		tblAgentInfo.setAgentManagerNm(agentManagerNm);
		tblAgentInfo.setAgentManagerTel(agentManagerTel);
		tblAgentInfo.setAgentTel(agentTel);
		tblAgentInfo.setAgentWebAdd(agentWebAdd);
		tblAgentInfo.setAgentWebNm(agentWebNm);
		tblAgentInfo.setApplyDate(applyDate);
		tblAgentInfo.setBusiPerson(busiPerson);
		tblAgentInfo.setChannelType(channelType);
		tblAgentInfo.setContraEndDt(contraEndDt);
		tblAgentInfo.setDistrict(district);
		tblAgentInfo.setEmpNum(empNum);
		tblAgentInfo.setIcpCompNm(icpCompNm);
		tblAgentInfo.setIcpRecordNo(icpRecordNo);
		tblAgentInfo.setLicenceNo(licenceNo);
		tblAgentInfo.setManageArea(manageArea);
		tblAgentInfo.setManageOwner(manageOwner);
		tblAgentInfo.setManagerIdentNo(managerIdentNo);
		tblAgentInfo.setManagerNm(agentManagerNm);
		tblAgentInfo.setOrganizNo(organizNo);
		
		rspCode = t11501BO.add(tblAgentInfo);
		return rspCode;
	}

	public String update() throws Exception{
		TblAgentInfo tblAgentInfo = t11501BO.get(agentNo_update);
		
		
	//	BeanUtils.copyProperties(tblAgentInfo, this);
		
		tblAgentInfo.setAgentNm(agentNm);
		tblAgentInfo.setAgentProvince(agentProvince);
		tblAgentInfo.setAgentCity(agentCity);
		tblAgentInfo.setZipCode(zipCode);
		tblAgentInfo.setAgentConnNm(agentConnNm);
		tblAgentInfo.setAgentConnTel(agentConnTel);
		tblAgentInfo.setAddress(address);
		tblAgentInfo.setAgentManagerNm(agentManagerNm);
		tblAgentInfo.setAgentManagerTel(agentManagerTel);
		tblAgentInfo.setAgentTel(agentTel);
		tblAgentInfo.setAgentWebAdd(agentWebAdd);
		tblAgentInfo.setAgentWebNm(agentWebNm);
		tblAgentInfo.setApplyDate(applyDate);
		tblAgentInfo.setBusiPerson(busiPerson);
		tblAgentInfo.setChannelType(channelType);
		tblAgentInfo.setContraEndDt(contraEndDt);
		tblAgentInfo.setDistrict(district);
		tblAgentInfo.setEmpNum(empNum);
		tblAgentInfo.setIcpCompNm(icpCompNm);
		tblAgentInfo.setIcpRecordNo(icpRecordNo);
		tblAgentInfo.setLicenceNo(licenceNo);
		tblAgentInfo.setManageArea(manageArea);
		tblAgentInfo.setManageOwner(manageOwner);
		tblAgentInfo.setManagerIdentNo(managerIdentNo);
		tblAgentInfo.setManagerNm(agentManagerNm);
		tblAgentInfo.setOrganizNo(organizNo);
		
		rspCode = t11501BO.update(tblAgentInfo);
		return rspCode;
		
	}
	
	
	private String agentNo;       //代理商编号
    private String agentNm;       //代理商名称
    private String agentProvince;  //代理商所属省份
    private String agentCity;      //代理商所属城市
    private String zipCode;       //邮政编码
    private String empNum;        //员工人数
    private String manageArea;    //经营场所面积
    private String manageOwner;   //经营场所权属
    private String agentWebNm;   //代理商网站名称
    private String agentWebAdd;  //代理商网站地址
    private String icpRecordNo;  //ICP备案号
    private String icpCompNm;    //ICP备案公司名称
    private String managerNm;    //法人代表名称
    private String managerIdentNo;//法人代表身份证号
    private String licenceNo;    //代理商营业执照编号
    private String organizNo;   //代理商组织机构代码证编号
    private String address;      //收件地址
    private String agentManagerNm;//代理商法人代表名称
    private String agentManagerTel;//代理商法人代表电话
    private String agentConnNm;   //代理商商务联系人名称
    private String agentConnTel;  //代理商商务联系人电话
    private String agentTel;      //代理商电话
    private String district;      //所属大区
    private String busiPerson;    //所属业务人员
    private String channelType;   //渠道合作商类型
    private String contraEndDt;   //合同到期日
    private String applyDate;     //申请日期
    private String agentNo_update;
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getAgentNm() {
		return agentNm;
	}
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}
	public String getAgentProvince() {
		return agentProvince;
	}
	public void setAgentProvince(String agentProvince) {
		this.agentProvince = agentProvince;
	}
	public String getAgentCity() {
		return agentCity;
	}
	public void setAgentCity(String agentCity) {
		this.agentCity = agentCity;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getEmpNum() {
		return empNum;
	}
	public void setEmpNum(String empNum) {
		this.empNum = empNum;
	}
	public String getManageArea() {
		return manageArea;
	}
	public void setManageArea(String manageArea) {
		this.manageArea = manageArea;
	}
	public String getManageOwner() {
		return manageOwner;
	}
	public void setManageOwner(String manageOwner) {
		this.manageOwner = manageOwner;
	}
	public String getAgentWebNm() {
		return agentWebNm;
	}
	public void setAgentWebNm(String agentWebNm) {
		this.agentWebNm = agentWebNm;
	}
	public String getAgentWebAdd() {
		return agentWebAdd;
	}
	public void setAgentWebAdd(String agentWebAdd) {
		this.agentWebAdd = agentWebAdd;
	}
	public String getIcpRecordNo() {
		return icpRecordNo;
	}
	public void setIcpRecordNo(String icpRecordNo) {
		this.icpRecordNo = icpRecordNo;
	}
	public String getIcpCompNm() {
		return icpCompNm;
	}
	public void setIcpCompNm(String icpCompNm) {
		this.icpCompNm = icpCompNm;
	}
	public String getManagerNm() {
		return managerNm;
	}
	public void setManagerNm(String managerNm) {
		this.managerNm = managerNm;
	}
	public String getManagerIdentNo() {
		return managerIdentNo;
	}
	public void setManagerIdentNo(String managerIdentNo) {
		this.managerIdentNo = managerIdentNo;
	}
	public String getLicenceNo() {
		return licenceNo;
	}
	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}
	public String getOrganizNo() {
		return organizNo;
	}
	public void setOrganizNo(String organizNo) {
		this.organizNo = organizNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAgentManagerNm() {
		return agentManagerNm;
	}
	public void setAgentManagerNm(String agentManagerNm) {
		this.agentManagerNm = agentManagerNm;
	}
	public String getAgentManagerTel() {
		return agentManagerTel;
	}
	public void setAgentManagerTel(String agentManagerTel) {
		this.agentManagerTel = agentManagerTel;
	}
	public String getAgentConnNm() {
		return agentConnNm;
	}
	public void setAgentConnNm(String agentConnNm) {
		this.agentConnNm = agentConnNm;
	}
	public String getAgentConnTel() {
		return agentConnTel;
	}
	public void setAgentConnTel(String agentConnTel) {
		this.agentConnTel = agentConnTel;
	}
	public String getAgentTel() {
		return agentTel;
	}
	public void setAgentTel(String agentTel) {
		this.agentTel = agentTel;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getBusiPerson() {
		return busiPerson;
	}
	public void setBusiPerson(String busiPerson) {
		this.busiPerson = busiPerson;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getContraEndDt() {
		return contraEndDt;
	}
	public void setContraEndDt(String contraEndDt) {
		this.contraEndDt = contraEndDt;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}



	public T11501BO getT11501BO() {
		return t11501BO;
	}



	public void setT11501BO(T11501BO t11501bo) {
		t11501BO = t11501bo;
	}



	public String getAgentNo_update() {
		return agentNo_update;
	}



	public void setAgentNo_update(String agentNo_update) {
		this.agentNo_update = agentNo_update;
	}
    
    
}
