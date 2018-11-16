
package com.huateng.struts.mchnt.action;

import com.huateng.bo.mchnt.T2070302BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.mchnt.TblMchtBeneficiaryInfTmp;
import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
 
public class T201001Action extends BaseAction{

	private static final long serialVersionUID = 1L;
	T2070302BO t2070302BO=(T2070302BO) ContextUtil.getBean("T2070302BO");
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("recover".equals(method)) {
			log("恢复新增商户审核拒绝");
			rspCode = recover();
		}else if("recoverTerm".equals(method)) {
			log("恢复新增终端审核拒绝");
			rspCode = recoverTerm();
		}else if("delete".equals(method)) {
			log("删除商户费率信息");
			rspCode = delete();
		}else if("delete1".equals(method)) {
			log("删除受益人信息");
			rspCode = delete1();
		}else if("add".equals(method)) {
			log("添加受益人信息");
			rspCode = add();
		}else if("update".equals(method)) {
			log("修改受益人信息");
			rspCode = update();
		}
		return rspCode;
	}

	private String recoverTerm() {
		try{
			 String sql="update tbl_term_inf_tmp set term_sta='0' where term_id='"+termId+"' and term_sta='8' ";
			 String del="delete from TBL_TERM_REFUSE where term_id='"+termId+"' and refuse_type='新增审核拒绝' ";
			 commQueryDAO.excute(sql);
			 commQueryDAO.excute(del);
			}catch(Exception e){
				e.printStackTrace();
				return "终端恢复失败";
			}
			return Constants.SUCCESS_CODE;
	}

	private String recover() {
		try{
		 String sql="update tbl_mcht_base_inf_tmp set mcht_status='1' where mcht_no='"+mchtId+"'";
		 String del="delete from TBL_MCHNT_REFUSE where mchnt_id='"+mchtId+"' ";
		 commQueryDAO.excute(sql);
		 commQueryDAO.excute(del);
		}catch(Exception e){
			e.printStackTrace();
			return "商户恢复失败";
		}
		return Constants.SUCCESS_CODE;
	}

	private String delete() {
		//新增状态的可以直接删除,不需审核
		TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp = t2070302BO.getAlgo2(discId);
		if(tblHisDiscAlgo2Tmp==null){
			return "该记录不存在或已删除";
		}
		
//		if(StringUtil.isEmpty(tblHisDiscAlgo2Tmp.getMCHT_NO())){
		if(tblHisDiscAlgo2Tmp.getSA_SATUTE().equals("0")){//20120915修改
			t2070302BO.deleteAlgo2(discId);
		}else{//非新增状态的直接修改状态
			tblHisDiscAlgo2Tmp.setSA_SATUTE("4");
			t2070302BO.updateAlgo2(tblHisDiscAlgo2Tmp);
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除受益人信息
	 * @return
	 */
	public String delete1(){
		//AntiMoneyLaunderingTmp amlt = new AntiMoneyLaunderingTmp();
		//antiMoneyLaunderingTmp.setAntiId(antiId);
		TblMchtBeneficiaryInfTmp amlt = t2070302BO.getBeneficiary(beneficiaryId);
		if(amlt==null){
			return "该记录不存在或已删除";
		}
		
		t2070302BO.deleteBeneficiary(beneficiaryId);
		
		/*try {
			String sql = "delete from TBL_MCHT_BENEFICIARY_INF_TMP where BENEFICIARY_ID = '"+beneficiaryId+"'";
			commQueryDAO.excute(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "受益人信息删除失败！";
		}*/
		
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 添加受益人信息
	 * @return
	 */
	public String add(){
		try {
			TblMchtBeneficiaryInfTmp amlt = t2070302BO.getBeneficiary(beneficiaryId1);
			if(amlt!=null){
				return "受益人编号已重复。";
			}
			TblMchtBeneficiaryInfTmp amlt1 = new TblMchtBeneficiaryInfTmp();
			amlt1.setBeneficiaryId(beneficiaryId1);
			amlt1.setMchtNo(mchtNoInPut);
			amlt1.setBeneficiaryName(beneficiaryName1);
			amlt1.setBeneficiaryAddress(beneficiaryAddress1);
			amlt1.setBeneficiaryIdType(beneficiaryIdTypemd1);
			amlt1.setBeneficiaryIdCard(beneficiaryIdCard1);
			amlt1.setBeneficiaryExpiration(beneficiaryExpiration1);
			
			t2070302BO.addBeneficiary(amlt1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "添加受益人信息失败！";
		}
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改受益人信息
	 * @return
	 */
	public String update(){
		try {
			TblMchtBeneficiaryInfTmp amlt = new TblMchtBeneficiaryInfTmp();
			amlt.setBeneficiaryId(beneficiaryId);
			amlt.setMchtNo(mchtNoInPut);
			amlt.setBeneficiaryName(beneficiaryName);
			amlt.setBeneficiaryAddress(beneficiaryAddress);
			amlt.setBeneficiaryIdType(beneficiaryIdTypemd);
			amlt.setBeneficiaryIdCard(beneficiaryIdCard);
			amlt.setBeneficiaryExpiration(beneficiaryExpiration);
			
			t2070302BO.updateBeneficiary(amlt);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "修改受益人信息失败";
		}
		
		return Constants.SUCCESS_CODE;
	}
	
	
	private String termId;
	
	private String mchtId; 

	private String discId;	
	
	//受益人信息
	//商户号
	private String mchtNoInPut;
	
	//受益人id:T20100.js传过来的id
	private String beneficiaryId;
	
	private String beneficiaryName;
	
	private String beneficiaryAddress;
	
	private String beneficiaryIdTypemd;
	
	private String beneficiaryIdCard;
	
	private String beneficiaryExpiration;
	
	
	
	
	private String beneficiaryId1;
	
	private String beneficiaryName1;
	
	private String beneficiaryAddress1;
	
	private String beneficiaryIdTypemd1;
	
	private String beneficiaryIdCard1;
	
	private String beneficiaryExpiration1;
	
	
	public String getBeneficiaryId1() {
		return beneficiaryId1;
	}

	public void setBeneficiaryId1(String beneficiaryId1) {
		this.beneficiaryId1 = beneficiaryId1;
	}

	public String getBeneficiaryName1() {
		return beneficiaryName1;
	}

	public void setBeneficiaryName1(String beneficiaryName1) {
		this.beneficiaryName1 = beneficiaryName1;
	}

	public String getBeneficiaryAddress1() {
		return beneficiaryAddress1;
	}

	public void setBeneficiaryAddress1(String beneficiaryAddress1) {
		this.beneficiaryAddress1 = beneficiaryAddress1;
	}

	public String getBeneficiaryIdTypemd1() {
		return beneficiaryIdTypemd1;
	}

	public void setBeneficiaryIdTypemd1(String beneficiaryIdTypemd1) {
		this.beneficiaryIdTypemd1 = beneficiaryIdTypemd1;
	}

	public String getBeneficiaryIdCard1() {
		return beneficiaryIdCard1;
	}

	public void setBeneficiaryIdCard1(String beneficiaryIdCard1) {
		this.beneficiaryIdCard1 = beneficiaryIdCard1;
	}

	public String getBeneficiaryExpiration1() {
		return beneficiaryExpiration1;
	}

	public void setBeneficiaryExpiration1(String beneficiaryExpiration1) {
		this.beneficiaryExpiration1 = beneficiaryExpiration1;
	}

	public String getMchtNoInPut() {
		return mchtNoInPut;
	}

	public void setMchtNoInPut(String mchtNoInPut) {
		this.mchtNoInPut = mchtNoInPut;
	}
	
	public String getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBeneficiaryAddress() {
		return beneficiaryAddress;
	}

	public void setBeneficiaryAddress(String beneficiaryAddress) {
		this.beneficiaryAddress = beneficiaryAddress;
	}

	public String getBeneficiaryIdTypemd() {
		return beneficiaryIdTypemd;
	}

	public void setBeneficiaryIdTypemd(String beneficiaryIdTypemd) {
		this.beneficiaryIdTypemd = beneficiaryIdTypemd;
	}

	public String getBeneficiaryIdCard() {
		return beneficiaryIdCard;
	}

	public void setBeneficiaryIdCard(String beneficiaryIdCard) {
		this.beneficiaryIdCard = beneficiaryIdCard;
	}

	public String getBeneficiaryExpiration() {
		return beneficiaryExpiration;
	}

	public void setBeneficiaryExpiration(String beneficiaryExpiration) {
		this.beneficiaryExpiration = beneficiaryExpiration;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getDiscId() {
		return discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public String getMchtId() {
		return mchtId;
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}
	 
}
