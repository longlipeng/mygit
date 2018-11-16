package com.huateng.struts.base.action;

import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.base.T10108BO;
import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.po.base.TblBranchManage;

public class T10108Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private String Id;
	private String brancharea;
	
	public String getBrancharea() {
		return brancharea;
	}

	public void setBrancharea(String brancharea) {
		this.brancharea = brancharea;
	}
	
	private String branchlevel;
	private String parentbranchid;
	private String phonenum;
	private String branchpos;
	private String branchaddr;
	private String linkname;
	private String linkmanpho;
	private String linkmanmail;
	private String branchfax;
	private String branchname;
	private String branchdes;
	private String branchDataList;
	private String state;	
	
	private T10108BO t10108BO = (T10108BO) ContextUtil.getBean("T10108BO");
//	private T90102BO t90102BO = (T90102BO) ContextUtil.getBean("T90102BO");
	
	@Override
	protected String subExecute() throws Exception {
		try {
			if("add".equals(getMethod())) {			
				rspCode = add();			
			} else if("delete".equals(getMethod())) {
				rspCode = delete();
			} else if("update".equals(getMethod())) {
				rspCode = update();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对分公司的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	private String add() {
		TblBranchManage tblBranchManage=new TblBranchManage();
		if(t10108BO.get(getId())!=null){
			return "该分公司号已经存在";
		}
		tblBranchManage.setId(getId());
		tblBranchManage.setBranchLevel(this.getBranchlevel());
		tblBranchManage.setParentBranchId(this.getParentbranchid());
		tblBranchManage.setBrancharea(getBrancharea());
		tblBranchManage.setPhonenum(getPhonenum());
		tblBranchManage.setBranchpos(getBranchpos());
		tblBranchManage.setBranchaddr(getBranchaddr());
		tblBranchManage.setLinkname(getLinkname() );
		tblBranchManage.setLinkmanpho(getLinkmanpho());
		tblBranchManage.setLinkmanmail(getLinkmanmail());
		tblBranchManage.setBranchfax(getBranchfax());
		tblBranchManage.setBranchname(getBranchname());
		tblBranchManage.setBranchdes(getBranchdes());
		tblBranchManage.setCreoprid(operator.getOprId());
		tblBranchManage.setCreoprdate(CommonFunction.getCurrentDateTime());
		tblBranchManage.setUpoprid(operator.getOprId());
		tblBranchManage.setUpoprdate(CommonFunction.getCurrentDateTime());
		tblBranchManage.setState("0");//新增未审核
		
		t10108BO.add(tblBranchManage);
		log("添加分公司信息成功。操作员编号：" + operator.getOprId() + "，分公司编号：" +getId());
		return Constants.SUCCESS_CODE;
	}

	private String update() throws Exception{
		jsonBean.parseJSONArrayData(getBranchDataList());
		int len = jsonBean.getArray().size();
		
		TblBranchManage tblBranchManage = null;
		List<TblBranchManage> tblBranchManageInfoList = new ArrayList<TblBranchManage>(len);
		
		for(int i = 0; i < len; i++) {
			Id = jsonBean.getJSONDataAt(i).getString("Id");
			tblBranchManage = t10108BO.get(Id);
			
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			BeanUtils.setObjectWithPropertiesValue(tblBranchManage, jsonBean, true);
     		tblBranchManage.setBrancharea(brancharea);
     		tblBranchManage.setParentBranchId(jsonBean.getJSONDataAt(i).getString("parentbranchid"));//20120824add
     		tblBranchManage.setBranchLevel(jsonBean.getJSONDataAt(i).getString("branchlevel"));//20120824add
     		tblBranchManage.setUpoprid(operator.getOprId());
			tblBranchManage.setUpoprdate(CommonFunction.getCurrentDateTime());
			//只有“正常”状态，更新state值，“新增待审核”、“修改待审核”保持state值不变
			String state=tblBranchManage.getState();
			if(("1").equals(state)){
				tblBranchManage.setState("2");
			}
//			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			tblBranchManageInfoList.add(tblBranchManage);
		}	
		String s = t10108BO.update(tblBranchManageInfoList);
		log("更新分公司信息成功。操作员编号：" + operator.getOprId()+ "，分公司编号：" +getId());
	    return Constants.SUCCESS_CODE;
	}
	
	private String delete() throws Exception {
		TblBranchManage tblBranchManage = t10108BO.get(this.getId());
		String state = tblBranchManage.getState();
		//删除状态不允许再删除
		if(("7").equals(state)){
			return "该分公司已是删除状态，请勿重复删除";
		}
		if("0".equals(state)){
			t10108BO.delete(this.getId());//新增待审核状态可直接删除
		}else{//其它状态将state更新为“删除待审核”
			tblBranchManage.setState("8");
			tblBranchManage.setUpoprid(operator.getOprId());
			tblBranchManage.setUpoprdate(CommonFunction.getCurrentDateTime());
		}
			
		//修改状态到临时表
		List<TblBranchManage> tblBranchManageList = new ArrayList<TblBranchManage>();
		tblBranchManageList.add(tblBranchManage);
		t10108BO.update(tblBranchManageList);
		//修改状态到真实表
//		TblBranchManageTrue tblBranchManageTrue = t90102BO.get(this.getId());
//		BeanUtils.copyProperties(tblBranchManage,tblBranchManageTrue);
//		t90102BO.update(tblBranchManageTrue);
		
		log("删除分公司信息成功。操作员编号：" + operator.getOprId()+ "，分公司编号：" +getId());
		return Constants.SUCCESS_CODE;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBranchDataList() {
		return branchDataList;
	}

	public void setBranchDataList(String branchDataList) {
		this.branchDataList = branchDataList;
	}

	public String getBranchdes() {
		return branchdes;
	}

	public void setBranchdes(String branchdes) {
		this.branchdes = branchdes;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
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

	public String getBranchfax() {
		return branchfax;
	}

	public void setBranchfax(String branchfax) {
		this.branchfax = branchfax;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getBranchlevel() {
		return branchlevel;
	}

	public void setBranchlevel(String branchlevel) {
		this.branchlevel = branchlevel;
	}

	public String getParentbranchid() {
		return parentbranchid;
	}

	public void setParentbranchid(String parentbranchid) {
		this.parentbranchid = parentbranchid;
	}
	
}