package com.huateng.struts.risk.action;


import com.huateng.bo.risk.T40206BO;
import com.huateng.bo.risk.T41201BO;
import com.huateng.bo.risk.T41202BO;
import com.huateng.common.StringUtil;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.po.risk.TblWhiteList;
import com.huateng.po.risk.TblWhiteListTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T41202Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	T41202BO t41202BO = (T41202BO) ContextUtil.getBean("T41202BO");  //白名单临时表
	T41201BO t41201BO = (T41201BO) ContextUtil.getBean("T41201BO");   //白名单正式表
	T40206BO  t40206BO = (T40206BO) ContextUtil.getBean("T40206BO");

	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		TblWhiteListTmp tblWhiteListTmp = t41202BO.get(uuid);
		String state = tblWhiteListTmp.getState();
		if("accept".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核通过新增待审核的商户白名单");
				rspCode = acceptAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核通过删除待审核的商户白名单");
				rspCode = acceptDelete();
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核通过修改待审核的商户白名单");
				rspCode = acceptModify();
			}
		} else if("refuse".equals(method)) {
			if(state.equals(ADD_TO_CHECK)){
				log("审核拒绝新增待审核的商户白名单");
				rspCode = refuseAdd();
			}
			if(state.equals(DELETE_TO_CHECK)){
				log("审核拒绝删除待审核的商户白名单");
				rspCode = refuseDelete();
			}
			if(state.equals(MODIFY_TO_CHECK)){
				log("审核拒绝删除待审核的商户白名单");
				rspCode = refuseModify();
			}
		}
		return rspCode;
	}

	/**
	 * 审核通过  新增待审核的商户黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd() throws Exception {
		
		TblWhiteListTmp tblWhiteListTmp = t41202BO.get(uuid);
		
		if(this.checkOperator())
			return "操作人与审核人不能是同一人。";
		else{
			
			tblWhiteListTmp.setUpdOpr(operator.getOprId());
	//		tblWhiteListTmp.setUpdDt(CommonFunction.getCurrentDateTime());           
			tblWhiteListTmp.setState(NORMAL);
			
			t41202BO.update(tblWhiteListTmp);
			
			TblWhiteList tblWhiteList = new TblWhiteList();
			BeanUtils.copyProperties(tblWhiteList,tblWhiteListTmp);	
			t41201BO.add(tblWhiteList);
	
		
		//商户白名单审核信息表
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间		
		riskRefuse.setOprId(operator.getOprId());  //审核人
		riskRefuse.setRefuseType("5");             //操作类型
		riskRefuse.setRefuseInfo(refuseInfo.trim());      //审核备注
		riskRefuse.setFlag("3"); //标志
		riskRefuse.setParam1(tblWhiteList.getMchtNo());             //商户编号
		riskRefuse.setParam2(tblWhiteList.getInsOpr());   //申请人
		riskRefuse.setParam3(tblWhiteList.getInsDt());    //申请时间
		riskRefuse.setParam4(tblWhiteList.getState());   //当前状态
		riskRefuse.setParam5(tblWhiteList.getAddType());     //添加方式
		riskRefuse.setReserve1(tblWhiteList.getAppRemark());   //申请备注
		
		
		rspCode=t40206BO.saveRefuseInfo(riskRefuse);
		
		//更新所有该卡号的状态
		String sqlUpdate = "update  tbl_risk_refuse set PARAM4 = '"+tblWhiteList.getState()+"' where FLAG='3' and PARAM1 ='"+tblWhiteList.getMchtNo()+"'";
		CommonFunction.getCommQueryDAO().excute(sqlUpdate);
		
		return rspCode;
		}

	}
	
	/**
	 * 审核拒绝  新增待审核的商户黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd() throws Exception {
		
		
		TblWhiteListTmp tblWhiteListTmp = t41202BO.get(uuid);

		if(this.checkOperator())
			return "操作人与审核人不能是同一人";
		else{
			tblWhiteListTmp.setUpdOpr(operator.getOprId());
			tblWhiteListTmp.setUpdDt(CommonFunction.getCurrentDateTime());           
			tblWhiteListTmp.setState(DELETE);
			
			t41202BO.update(tblWhiteListTmp);
		
		
			//商户白名单审核信息表
			TblRiskRefuse riskRefuse = new TblRiskRefuse();
			riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间		
			riskRefuse.setOprId(operator.getOprId());  //审核人
			riskRefuse.setRefuseType("0");             //操作类型
			riskRefuse.setRefuseInfo(refuseInfo.trim());      //审核备注
			riskRefuse.setFlag("3"); //标志
			riskRefuse.setParam1(tblWhiteListTmp.getMchtNo());             //商户编号
			riskRefuse.setParam2(tblWhiteListTmp.getInsOpr());   //申请人
			riskRefuse.setParam3(tblWhiteListTmp.getInsDt());    //申请时间
			riskRefuse.setParam4(tblWhiteListTmp.getState());   //当前状态
			riskRefuse.setParam5(tblWhiteListTmp.getAddType());     //添加方式
			riskRefuse.setReserve1(tblWhiteListTmp.getAppRemark());   //申请备注
			
			
			rspCode=t40206BO.saveRefuseInfo(riskRefuse);
		
			//更新所有该卡号的状态
			String sqlUpdate = "update  tbl_risk_refuse set PARAM4 = '"+tblWhiteListTmp.getState()+"' where FLAG='3' and PARAM1 ='"+tblWhiteListTmp.getMchtNo()+"'";
			CommonFunction.getCommQueryDAO().excute(sqlUpdate);
				
		return rspCode;
		}
	}
	
	/**
	 * 审核通过  删除待审核的商户黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete() throws Exception {
		
		TblWhiteListTmp tblWhiteListTmp = t41202BO.get(uuid);
		if(this.checkOperator())
			return "操作人与审核人不能是同一人";
		else{
			
			tblWhiteListTmp.setUpdOpr(operator.getOprId());
	//		tblWhiteListTmp.setSaModiTime(CommonFunction.getCurrentDateTime());
			tblWhiteListTmp.setState(DELETE);
			
		    t41202BO.update(tblWhiteListTmp);
		
		    TblWhiteList tblWhiteList = new TblWhiteList();
			BeanUtils.copyProperties(tblWhiteList,tblWhiteListTmp);	
		    
		    t41201BO.update(tblWhiteList);
			
		
			//商户白名单审核信息表
			TblRiskRefuse riskRefuse = new TblRiskRefuse();
			riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间		
			riskRefuse.setOprId(operator.getOprId());  //审核人
			riskRefuse.setRefuseType("6");             //操作类型
			riskRefuse.setRefuseInfo(refuseInfo.trim());      //审核备注
			riskRefuse.setFlag("3"); //标志
			riskRefuse.setParam1(tblWhiteList.getMchtNo());             //商户编号
			riskRefuse.setParam2(tblWhiteList.getInsOpr());   //申请人
			riskRefuse.setParam3(tblWhiteList.getInsDt());    //申请时间
			riskRefuse.setParam4(tblWhiteList.getState());   //当前状态
			riskRefuse.setParam5(tblWhiteList.getAddType());     //添加方式
			riskRefuse.setReserve1(tblWhiteList.getAppRemark());   //申请备注
			
			
			rspCode=t40206BO.saveRefuseInfo(riskRefuse);
			
			//更新所有该卡号的状态
			String sqlUpdate = "update  tbl_risk_refuse set PARAM4 = '"+tblWhiteList.getState()+"' where FLAG='3' and PARAM1 ='"+tblWhiteList.getMchtNo()+"'";
			CommonFunction.getCommQueryDAO().excute(sqlUpdate);
				
				
		return rspCode;
		}
	}
	
	/**
	 * 审核通过  修改待审核的商户白名单
	 * @return
	 * 
	 * @throws Exception 
	 */
     private String acceptModify() throws Exception{
    	 
    	 TblWhiteListTmp tblWhiteListTmp = t41202BO.get(uuid);
 		
 		if(this.checkOperator())
 			return "操作人与审核人不能是同一人。";
 		else{
 			
 			tblWhiteListTmp.setUpdOpr(operator.getOprId());
 	//		tblWhiteListTmp.setUpdDt(CommonFunction.getCurrentDateTime());           
 			tblWhiteListTmp.setState(NORMAL);
 			
 			t41202BO.update(tblWhiteListTmp);
 			
 			TblWhiteList tblWhiteList = new TblWhiteList();
 			BeanUtils.copyProperties(tblWhiteList,tblWhiteListTmp);	
 			t41201BO.update(tblWhiteList);
 	
 		
 		//商户白名单审核信息表
 		TblRiskRefuse riskRefuse = new TblRiskRefuse();
 		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间		
 		riskRefuse.setOprId(operator.getOprId());  //审核人
 		riskRefuse.setRefuseType("7");             //操作类型
 		riskRefuse.setRefuseInfo(refuseInfo.trim());      //审核备注
 		riskRefuse.setFlag("3"); //标志
 		riskRefuse.setParam1(tblWhiteList.getMchtNo());             //商户编号
 		riskRefuse.setParam2(tblWhiteList.getInsOpr());   //申请人
 		riskRefuse.setParam3(tblWhiteList.getInsDt());    //申请时间
 		riskRefuse.setParam4(tblWhiteList.getState());   //当前状态
 		riskRefuse.setParam5(tblWhiteList.getAddType());     //添加方式
 		riskRefuse.setReserve1(tblWhiteList.getAppRemark());   //申请备注
 		
 		
 		rspCode=t40206BO.saveRefuseInfo(riskRefuse);
 		
 		//更新所有该卡号的状态
 		String sqlUpdate = "update  tbl_risk_refuse set PARAM4 = '"+tblWhiteList.getState()+"' where FLAG='3' and PARAM1 ='"+tblWhiteList.getMchtNo()+"'";
 		CommonFunction.getCommQueryDAO().excute(sqlUpdate);
 		
 		return rspCode;
 		}
    	 
    	 
    	
    	 
     }
	/**
	 * 审核拒绝  删除待审核的商户黑名单
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete() throws Exception {
		
		TblWhiteListTmp tblWhiteListTmp = t41202BO.get(uuid);
		if(this.checkOperator())
			return "操作人与审核人不能是同一人";
		else{
		
			tblWhiteListTmp.setUpdOpr(operator.getOprId());
			tblWhiteListTmp.setState(NORMAL);
			t41202BO.update(tblWhiteListTmp);
			

		    TblWhiteList tblWhiteList = new TblWhiteList();
			BeanUtils.copyProperties(tblWhiteList,tblWhiteListTmp);	
			t41201BO.update(tblWhiteList);
		
		
			//商户白名单审核信息表
			TblRiskRefuse riskRefuse = new TblRiskRefuse();
			riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间		
			riskRefuse.setOprId(operator.getOprId());  //审核人
			riskRefuse.setRefuseType("4");             //操作类型
			riskRefuse.setRefuseInfo(refuseInfo.trim());      //审核备注
			riskRefuse.setFlag("3"); //标志
			riskRefuse.setParam1(tblWhiteList.getMchtNo());             //商户编号
			riskRefuse.setParam2(tblWhiteList.getInsOpr());   //申请人
			riskRefuse.setParam3(tblWhiteList.getInsDt());    //申请时间
			riskRefuse.setParam4(tblWhiteList.getState());   //当前状态
			riskRefuse.setParam5(tblWhiteList.getAddType());     //添加方式
			riskRefuse.setReserve1(tblWhiteList.getAppRemark());   //申请备注
			
			
			rspCode=t40206BO.saveRefuseInfo(riskRefuse);
			
			//更新所有该卡号的状态
			String sqlUpdate = "update  tbl_risk_refuse set PARAM4 = '"+tblWhiteList.getState()+"' where FLAG='3' and PARAM1 ='"+tblWhiteList.getMchtNo()+"'";
			CommonFunction.getCommQueryDAO().excute(sqlUpdate);
				
		return rspCode;
		}
	}
	
	
	/**
	 * 审核拒绝  修改待审核的商户白名单
	 * @return
	 * 
	 * @throws Exception 
	 */
     private String refuseModify()  throws Exception{
    	 
    	 TblWhiteListTmp tblWhiteListTmp = t41202BO.get(uuid);
    	 TblWhiteList tblWhiteList = t41201BO.get(uuid);
  		
  		if(this.checkOperator())
  			return "操作人与审核人不能是同一人。";
  		else{
  			
  			tblWhiteListTmp.setUpdOpr(operator.getOprId());
  			tblWhiteListTmp.setUpdDt(CommonFunction.getCurrentDateTime());
  			tblWhiteListTmp.setState(NORMAL);	
  			
  			tblWhiteListTmp.setBeginDt(tblWhiteList.getBeginDt());
  			tblWhiteListTmp.setValidity(tblWhiteList.getValidity());
  			/*t41201BO.update(tblWhiteListtblWhiteList;
  			
  			
  			BeanUtils.copyProperties(tblWhiteListTmp,tblWhiteList);	*/
  			t41202BO.update(tblWhiteListTmp);
  	
  		
  		//商户白名单审核信息表
  		TblRiskRefuse riskRefuse = new TblRiskRefuse();
  		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //审核时间		
  		riskRefuse.setOprId(operator.getOprId());  //审核人
  		riskRefuse.setRefuseType("3");             //操作类型
  		riskRefuse.setRefuseInfo(refuseInfo.trim());      //审核备注
  		riskRefuse.setFlag("3"); //标志
  		riskRefuse.setParam1(tblWhiteListTmp.getMchtNo());             //商户编号
  		riskRefuse.setParam2(tblWhiteListTmp.getInsOpr());   //申请人
  		riskRefuse.setParam3(tblWhiteListTmp.getInsDt());    //申请时间
  		riskRefuse.setParam4(tblWhiteListTmp.getState());   //当前状态
  		riskRefuse.setParam5(tblWhiteListTmp.getAddType());     //添加方式
  		riskRefuse.setReserve1(tblWhiteListTmp.getAppRemark());   //申请备注
  		
  		
  		rspCode=t40206BO.saveRefuseInfo(riskRefuse);
  		
  		//更新所有该卡号的状态
  		String sqlUpdate = "update  tbl_risk_refuse set PARAM4 = '"+tblWhiteList.getState()+"' where FLAG='3' and PARAM1 ='"+tblWhiteList.getMchtNo()+"'";
  		CommonFunction.getCommQueryDAO().excute(sqlUpdate);
  		
  		return rspCode;
  		}
    	 
     }
	
	//判断商户黑名单的操作人和审核人是否相同
	private boolean checkOperator()throws Exception {
		TblWhiteListTmp tblWhiteListTmp = t41202BO.get(uuid);
		String oprID = tblWhiteListTmp.getInsOpr();//操作人
		if(operator.getOprId().equals(oprID))
			return true;//相同
		else
			return false;//不同
	}
	
	
	private String uuid;   //主键
	private String refuseInfo; //拒绝原因
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	
	
}
