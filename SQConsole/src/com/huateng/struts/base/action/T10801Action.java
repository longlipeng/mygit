package com.huateng.struts.base.action;

import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;

/**
 * Title:银行账户信息维护
 * @version 1.0
 */
public class T10801Action extends BaseAction {
	
	protected String subExecute() throws Exception {
		try {
			if("add".equals(method)) {
				rspCode = add();
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			} else if("accept".equals(method)) {
				rspCode = accept();
			} else if("refuse".equals(method)) {
				rspCode = refuse();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，银行账户操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 添加银行账号
	 * @return
	 * @throws Exception 
	 */
	private String add() throws Exception {
		try{
			String sql1 = "select trim(BANK_ACCOUNT) from TBL_BANKNO_INFO_TMP WHERE BANK_ACCOUNT ='"+bankAccount+"'";
			String bankAccountTmp = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
			if (bankAccountTmp.equals(bankAccount)) {
				return "银行账号已经被使用";
			}
			String sql2 = "select MAX(BANK_NO) from TBL_BANKNO_INFO_TMP ";
			String bankNoMax = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql2);
			String bankNo;
			if (bankNoMax == "") {
				bankNo = "000001";
			}else {
				int i = Integer.parseInt(bankNoMax);
				i = i + 1;
				bankNo = String.format("%06d", i);
			}
			String sql3 = "INSERT INTO TBL_BANKNO_INFO_TMP " 
			+ "(BANK_NO, BANK_NAME, ACCOUNT_NAME, BANK_ACCOUNT, CRE_TIME, CRE_OPR_ID, UPD_TIME, UPD_OPR_ID,BANK_STATUS,REGION ) VALUES "
			+ "('"+bankNo+"', '"+bankName+"', '"+accountName+"', '"+bankAccount+"', '" + CommonFunction.getCurrentDateTime() + "', '" + operator.getOprId() + "', '" + CommonFunction.getCurrentDateTime() + "', '" + operator.getOprId() + "', '2', '" + region + "' ) ";
			log("添加银行账号成功。操作员编号：" + operator.getOprId()+ "，被添加银行账号：" + bankNo);
			CommonFunction.getCommQueryDAO().excute(sql3);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			log("银行账号成功失败："+e.getMessage());
			return Constants.DATA_OPR_FAIL;
		}
	}
	/**
	 * 删除银行账户
	 * @return
	 */
	private String delete() {
		try {
			String sql1 = "DELETE FROM TBL_BANKNO_INFO_TMP WHERE BANK_NO = '"+bankNo+"'"; 
			CommonFunction.getCommQueryDAO().excute(sql1);
			try {
				String sql2 = "DELETE FROM TBL_BANKNO_INFO WHERE BANK_NO = '"+bankNo+"'"; 
				CommonFunction.getCommQueryDAO().excute(sql2);
			} catch (Exception e) {
				e.printStackTrace();
				log("删除银行账户正式表失败:" + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log("删除银行账户临时表失败:" + e.getMessage());
		}
		log("删除银行账户信息成功。操作员编号：" + operator.getOprId()+ "，被删除银行账户：" + bankNo);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 银行账户信息
	 * @return
	 */
	private String update() {
		
		System.out.println("银行编号bankNo:"+ bankNo+" 银行名称bankName:"+bankName);
		System.out.println("账户户名accountName:"+ accountName+" 银行账号bankAccount:"+bankAccount);
		try {
			String sql = "UPDATE TBL_BANKNO_INFO_TMP SET BANK_NAME = '" + bankName + "', ACCOUNT_NAME = '" + accountName + "', BANK_ACCOUNT = '" + bankAccount + "', UPD_TIME = '" + CommonFunction.getCurrentDateTime() + "', UPD_OPR_ID = '" + operator.getOprId() + "', BANK_STATUS = '3', REGION = '"+ region +"' WHERE BANK_NO = '" + bankNo + "' ";
			CommonFunction.getCommQueryDAO().excute(sql);
			log("更新银行账户信息成功。操作员编号：" + operator.getOprId()+ "，被同步操作员信息号：" + bankNo);
		} catch (Exception e) {
			log("更新银行账户信息失败：" + e.getMessage());
			return Constants.DATA_OPR_FAIL;
		}
		return Constants.SUCCESS_CODE;
	}
	private String accept() {
		System.out.println("银行编号bankNo:"+ bankNo+" 银行名称updOprId:"+updOprId);
		if (operator.getOprId().equals(updOprId)) {
			return "提交人与审核人不能是同一个人";
		}
		try {
			try {
				String delSql = "DELETE FROM TBL_BANKNO_INFO WHERE BANK_NO = '" + bankNo + "' ";
				CommonFunction.getCommQueryDAO().excute(delSql);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String sql1 = "INSERT INTO TBL_BANKNO_INFO (BANK_NO, BANK_NAME, ACCOUNT_NAME, BANK_ACCOUNT, CRE_TIME, CRE_OPR_ID, UPD_TIME, UPD_OPR_ID, BANK_STATUS, AUDIT_OPR_ID, AUDITEE_OPR_ID, REGION ) select BANK_NO, BANK_NAME, ACCOUNT_NAME, BANK_ACCOUNT, CRE_TIME, CRE_OPR_ID, UPD_TIME, UPD_OPR_ID, BANK_STATUS, AUDIT_OPR_ID, AUDITEE_OPR_ID, REGION FROM TBL_BANKNO_INFO_TMP WHERE BANK_NO = '" + bankNo + "' ";
			CommonFunction.getCommQueryDAO().excute(sql1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String sql3 = "UPDATE TBL_BANKNO_INFO_TMP SET BANK_STATUS = '0' WHERE BANK_NO = '" + bankNo + "' ";
			CommonFunction.getCommQueryDAO().excute(sql3);
			
			String sql4 = "UPDATE TBL_BANKNO_INFO SET BANK_STATUS = '0' WHERE BANK_NO = '" + bankNo + "' ";
			CommonFunction.getCommQueryDAO().excute(sql4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.SUCCESS_CODE;
	}
	
	private String refuse() {
		System.out.println("银行编号bankNo:"+ bankNo+" 银行名称updOprId:"+updOprId);
		if (operator.getOprId().equals(updOprId)) {
			return "提交人与审核人不能是同一个人";
		}
		try {
			String sql1 = "UPDATE TBL_BANKNO_INFO_TMP SET BANK_STATUS = '4' WHERE BANK_NO = '" + bankNo + "' ";
			CommonFunction.getCommQueryDAO().excute(sql1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.SUCCESS_CODE;
	}

	private String bankNo;
	private String bankAccount;//银行账号
	private String accountName;//账户户名
	private String bankName;//银行名称
	private String updOprId;
	private String region;
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getUpdOprId() {
		return updOprId;
	}

	public void setUpdOprId(String updOprId) {
		this.updOprId = updOprId;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	

}
