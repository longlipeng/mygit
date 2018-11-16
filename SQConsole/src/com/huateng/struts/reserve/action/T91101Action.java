package com.huateng.struts.reserve.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.bo.reserve.T91101BO;
import com.huateng.common.Constants;
import com.huateng.po.reserve.TblSettleRosterInf;
import com.huateng.po.reserve.TblSettleRosterInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T91101Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	T91101BO t91101BO = (T91101BO) ContextUtil.getBean("T91101BO");
	
	private String rosterId;     //id
	private String rosterBankCard;  //收款方开户行行号
	private String rosterAccount;  //收款方账号
	private String rosterAccountName;  //收款方账户名称
	
	private String infList;
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("添加白名单信息");
			rspCode = add();
		}else if("delete".equals(method)){
			log("删除白名单信息");
			rspCode = delete();
		}else if("update".equals(method)){
			log("修改白名单信息");
			rspCode = update();
		}else if("rosterAccept".equals(method)){
			log("审核通过");
			rspCode = rosterAccept();
		}else if("redempRefuse".equals(method)){
			log("审核拒绝");
			rspCode = redempRefuse();
		}
		return rspCode;
	}
	
	/**
	 * 新增白名单信息
	 * @return
	 */
	public String add(){
		
		try {
			TblSettleRosterInfTmp tblSettleRosterInfTmp = new TblSettleRosterInfTmp();
			
			String sql = "select max(ROSTER_ID) from TBL_SETTLE_ROSTER_INF_TMP";
			String rosterMax = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
			String rosterNo;
			if (rosterMax == "") {
				rosterNo = "90000001";
			}else {
				int i = Integer.parseInt(rosterMax);
				i = i + 1;
				//如5在前面补0  直到凑够6位数  000005
				rosterNo = String.format("%08d", i);
			}
			
			tblSettleRosterInfTmp.setRosterId(rosterNo);
			tblSettleRosterInfTmp.setRosterBankCard(rosterBankCard);
			tblSettleRosterInfTmp.setRosterAccount(rosterAccount);
			tblSettleRosterInfTmp.setRosterAccountName(rosterAccountName);
			//3新增待审核
			tblSettleRosterInfTmp.setRosterStatus("3");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			//更新发起时间
			tblSettleRosterInfTmp.setRosterLaunchTime(sdf.format(date));
			//更新发起人
			tblSettleRosterInfTmp.setRosterLaunchName(operator.getOprId());
			
			t91101BO.addRoster(tblSettleRosterInfTmp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "添加白名单信息失败";
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除白名单信息
	 * @return
	 */
	public String delete(){
		
		try {
			TblSettleRosterInfTmp tblSettleRosterInfTmp = t91101BO.getRoster(rosterId);
			if(tblSettleRosterInfTmp==null){
				return "该条记录已删除";
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			//更新发起时间
			tblSettleRosterInfTmp.setRosterLaunchTime(sdf.format(date));
			//更新发起人
			tblSettleRosterInfTmp.setRosterLaunchName(operator.getOprId());
			//删除待审核
			tblSettleRosterInfTmp.setRosterStatus("2");
			
			t91101BO.upRoster(tblSettleRosterInfTmp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "删除白名单失败";
		}
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改白名单信息
	 * @return
	 */
	public String update(){
		
		try {
			TblSettleRosterInfTmp tblSettleRosterInfTmp = t91101BO.getRoster(rosterId);
			
			tblSettleRosterInfTmp.setRosterBankCard(rosterBankCard);
			tblSettleRosterInfTmp.setRosterAccount(rosterAccount);
			tblSettleRosterInfTmp.setRosterAccountName(rosterAccountName);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			//更新发起时间
			tblSettleRosterInfTmp.setRosterLaunchTime(sdf.format(date));
			//更新发起人
			tblSettleRosterInfTmp.setRosterLaunchName(operator.getOprId());
			//1修改
			tblSettleRosterInfTmp.setRosterStatus("1");
			
			t91101BO.upRoster(tblSettleRosterInfTmp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "修改白名单失败";
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过
	 * @return
	 */
	public String rosterAccept(){
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		int l = 0;
		
		TblSettleRosterInf tblSettleRosterInf;  //正
		TblSettleRosterInfTmp tblSettleRosterInfTmp;  //临
		for (int i = 0; i < len; i++) {
			String rosterId = jsonBean.getJSONDataAt(i).getString("rosterId");
			String rosterBankCard = jsonBean.getJSONDataAt(i).getString("rosterBankCard");
			String rosterAccount = jsonBean.getJSONDataAt(i).getString("rosterAccount");
			String rosterAccountName = jsonBean.getJSONDataAt(i).getString("rosterAccountName");
			String rosterStatus = jsonBean.getJSONDataAt(i).getString("rosterStatus");
			String rosterLaunchTime = jsonBean.getJSONDataAt(i).getString("rosterLaunchTime");
			String rosterLaunchName = jsonBean.getJSONDataAt(i).getString("rosterLaunchName");
			String rosterAuditTime = jsonBean.getJSONDataAt(i).getString("rosterAuditTime");
			String rosterAuditName = jsonBean.getJSONDataAt(i).getString("rosterAuditName");
			
			System.out.println("发起人:"+rosterLaunchName +"		发起时间:"+rosterLaunchTime);
			try{
				if (operator.getOprId().equals(rosterLaunchName)) {
					l++ ;
					return "发起人与审核人同一用户";
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			//1修改2删除3新增
			if(rosterStatus.equals("1")){
				
				try {
					tblSettleRosterInfTmp = t91101BO.getRoster(rosterId);
					//0正常
					tblSettleRosterInfTmp.setRosterStatus("0");
					tblSettleRosterInfTmp.setRosterAccount(rosterAccount);
					tblSettleRosterInfTmp.setRosterAccountName(rosterAccountName);
					tblSettleRosterInfTmp.setRosterBankCard(rosterBankCard);
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date date = new Date();
					//更新审核时间
					tblSettleRosterInfTmp.setRosterAuditTime(sdf.format(date));
					//更新审核人
					tblSettleRosterInfTmp.setRosterAuditName(operator.getOprId());
					
					String sql = "delete from TBL_SETTLE_ROSTER_INF where ROSTER_ID = '" + rosterId + "'";
					CommonFunction.getCommQueryDAO().excute(sql);
					
					String sql1 = "insert into TBL_SETTLE_ROSTER_INF(ROSTER_ID, ROSTER_BANK_CARD, ROSTER_ACCOUNT, ROSTER_ACCOUNT_NAME, ROSTER_STATUS, ROSTER_LAUNCH_TIME, ROSTER_LAUNCH_NAME, ROSTER_AUDIT_TIME, ROSTER_AUDIT_NAME) "
						      	+ "values('" + tblSettleRosterInfTmp.getRosterId() + "','" + tblSettleRosterInfTmp.getRosterBankCard() + "','" + tblSettleRosterInfTmp.getRosterAccount() + "'"
						      	+ ",'" + tblSettleRosterInfTmp.getRosterAccountName() + "','0','" + tblSettleRosterInfTmp.getRosterLaunchTime() + "','" + tblSettleRosterInfTmp.getRosterLaunchName() + "'"
						      	+ ",'" + operator.getOprId() + "','" + sdf.format(date) + "')";
					CommonFunction.getCommQueryDAO().excute(sql1);
					
					t91101BO.upRoster(tblSettleRosterInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else if(rosterStatus.equals("2")){
				try {
					t91101BO.delRosterInf(rosterId);
					t91101BO.delRoster(rosterId);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else if(rosterStatus.equals("3")){
				
				try {
					tblSettleRosterInfTmp = t91101BO.getRoster(rosterId);
					tblSettleRosterInfTmp.setRosterStatus("0");
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date date = new Date();
					//更新审核时间
					tblSettleRosterInfTmp.setRosterAuditTime(sdf.format(date));
					//更新审核人
					tblSettleRosterInfTmp.setRosterAuditName(operator.getOprId());
					
					String sql1 = "insert into TBL_SETTLE_ROSTER_INF(ROSTER_ID, ROSTER_BANK_CARD, ROSTER_ACCOUNT, ROSTER_ACCOUNT_NAME, ROSTER_STATUS, ROSTER_LAUNCH_TIME, ROSTER_LAUNCH_NAME, ROSTER_AUDIT_TIME, ROSTER_AUDIT_NAME) "
					      	    + "values('" + tblSettleRosterInfTmp.getRosterId() + "','" + tblSettleRosterInfTmp.getRosterBankCard() + "','" + tblSettleRosterInfTmp.getRosterAccount() + "'"
					      	    + ",'" + tblSettleRosterInfTmp.getRosterAccountName() + "','0','" + tblSettleRosterInfTmp.getRosterLaunchTime() + "','" + tblSettleRosterInfTmp.getRosterLaunchName() + "'"
					      	    + ",'" + operator.getOprId() + "','" + sdf.format(date) + "')";
					CommonFunction.getCommQueryDAO().excute(sql1);
					
					t91101BO.upRoster(tblSettleRosterInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核拒绝
	 * @return
	 */
	public String redempRefuse(){
		//获取数据
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		int l = 0;
		
		TblSettleRosterInf tblSettleRosterInf;  //正
		TblSettleRosterInfTmp tblSettleRosterInfTmp;  //临
		for (int i = 0; i < len; i++) {
			String rosterId = jsonBean.getJSONDataAt(i).getString("rosterId");
			String rosterBankCard = jsonBean.getJSONDataAt(i).getString("rosterBankCard");
			String rosterAccount = jsonBean.getJSONDataAt(i).getString("rosterAccount");
			String rosterAccountName = jsonBean.getJSONDataAt(i).getString("rosterAccountName");
			String rosterStatus = jsonBean.getJSONDataAt(i).getString("rosterStatus");
			String rosterLaunchTime = jsonBean.getJSONDataAt(i).getString("rosterLaunchTime");
			String rosterLaunchName = jsonBean.getJSONDataAt(i).getString("rosterLaunchName");
			String rosterAuditTime = jsonBean.getJSONDataAt(i).getString("rosterAuditTime");
			String rosterAuditName = jsonBean.getJSONDataAt(i).getString("rosterAuditName");
			
			System.out.println("发起人:"+rosterLaunchName +"		发起时间:"+rosterLaunchTime);
			try{
				if (operator.getOprId().equals(rosterLaunchName)) {
					l++ ;
					return "发起人与审核人同一用户";
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			//1修改2删除3新增
			if(rosterStatus.equals("1")){
				
				try {
					tblSettleRosterInfTmp = t91101BO.getRoster(rosterId);
					tblSettleRosterInf = t91101BO.getRosterInf(rosterId);
					
					tblSettleRosterInfTmp.setRosterBankCard(tblSettleRosterInf.getRosterBankCard());
					tblSettleRosterInfTmp.setRosterAccount(tblSettleRosterInf.getRosterAccount());
					tblSettleRosterInfTmp.setRosterAccountName(tblSettleRosterInf.getRosterAccountName());
					//0正常
					tblSettleRosterInfTmp.setRosterStatus("0");
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date date = new Date();
					//更新审核时间
					tblSettleRosterInfTmp.setRosterAuditTime(sdf.format(date));
					//更新审核人
					tblSettleRosterInfTmp.setRosterAuditName(operator.getOprId());
					
					
					t91101BO.upRoster(tblSettleRosterInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else if(rosterStatus.equals("2")){
				
				try {
					tblSettleRosterInfTmp = t91101BO.getRoster(rosterId);
					//0正常
					tblSettleRosterInfTmp.setRosterStatus("0");
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date date = new Date();
					//更新审核时间
					tblSettleRosterInfTmp.setRosterAuditTime(sdf.format(date));
					//更新审核人
					tblSettleRosterInfTmp.setRosterAuditName(operator.getOprId());
					
					t91101BO.upRoster(tblSettleRosterInfTmp);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else if(rosterStatus.equals("3")){
				try {
					t91101BO.delRoster(rosterId);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return Constants.SUCCESS_CODE;
	}
	
	
	
	
	
	public String getRosterId() {
		return rosterId;
	}
	public void setRosterId(String rosterId) {
		this.rosterId = rosterId;
	}
	public String getRosterBankCard() {
		return rosterBankCard;
	}
	public void setRosterBankCard(String rosterBankCard) {
		this.rosterBankCard = rosterBankCard;
	}
	public String getRosterAccount() {
		return rosterAccount;
	}
	public void setRosterAccount(String rosterAccount) {
		this.rosterAccount = rosterAccount;
	}
	public String getRosterAccountName() {
		return rosterAccountName;
	}
	public void setRosterAccountName(String rosterAccountName) {
		this.rosterAccountName = rosterAccountName;
	}
	public String getInfList() {
		return infList;
	}
	public void setInfList(String infList) {
		this.infList = infList;
	}
	
	
}	
