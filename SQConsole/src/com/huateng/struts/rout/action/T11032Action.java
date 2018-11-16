package com.huateng.struts.rout.action;

import com.huateng.bo.base.T10106BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.bo.rout.T11015BO;
import com.huateng.common.Constants;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.po.rout.TblTermChannelInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T11032Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T11015BO t11015BO = (T11015BO)ContextUtil.getBean("T11015BO");
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	//机构信息维护
	private T10106BO t10106BO = (T10106BO) ContextUtil.getBean("T10106BO");
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	//primary key
	private String id;
	//拒绝原因
	private String refuseInfo;
	
	private String infList;
	
	
	public String getInfList() {
		return infList;
	}

	public void setInfList(String infList) {
		this.infList = infList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		jsonBean.parseJSONArrayData(getInfList());
		
		int len = jsonBean.getArray().size();
		for(int i = 0;i < len;i++){
			String idkey = jsonBean.getJSONDataAt(i).getString("id");
			TblTermChannelInf tblTermChannelInf = t11015BO.get(idkey);
			String state = tblTermChannelInf.getStat();
			if("accept".equals(method)) {
				if(state.equals(ADD_TO_CHECK)){
					log("审核通过新增待审核的终端通道配置信息");
					rspCode = acceptAdd();
				}
				if(state.equals(DELETE_TO_CHECK)){
					log("审核通过删除待审核的终端通道配置信息");
					rspCode = acceptDelete();
				}
				if(state.equals(MODIFY_TO_CHECK)){
					log("审核通过修改待审核的终端通道配置信息");
					rspCode = acceptModify();
				}
			} else if("refuse".equals(method)) {
				if(state.equals(ADD_TO_CHECK)){
					log("审核拒绝新增待审核的终端通道配置信息");
					rspCode = refuseAdd();
				}
				if(state.equals(DELETE_TO_CHECK)){
					log("审核拒绝删除待审核的终端通道配置信息");
					rspCode = refuseDelete();
				}
				if(state.equals(MODIFY_TO_CHECK)){
					log("审核拒绝修改待审核的终端通道配置信息");
					rspCode = refuseModify();
				}
			}
		}
		
		
		return rspCode;
	}

	/**
	 * 审核通过新增待审核的终端通道配置信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptAdd() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			jsonBean.parseJSONArrayData(getInfList());
			
			int len = jsonBean.getArray().size();
			for(int i = 0;i < len;i++){
				String idkey = jsonBean.getJSONDataAt(i).getString("id");
				TblTermChannelInf tblTermChannelInf = t11015BO.get(idkey);
				tblTermChannelInf.setCheckTime(CommonFunction.getCurrentDateTime());
				tblTermChannelInf.setCheckOprId(operator.getOprId());
				tblTermChannelInf.setStat(NORMAL);
				rspCode = t11015BO.update(tblTermChannelInf);
				
//				String instName = t10106BO.get(tblTermChannelInf.getTermIns()).getAGEN_NAME();
				String instName = "";
				//判断签到信息是否存在
				String countsql = "SELECT COUNT(*) from TBL_SIGN_INF where MCHT_ID='" + tblTermChannelInf.getInsMcht().trim() 
						+ "' and TERM_ID='" + tblTermChannelInf.getInsTerm().trim() 
						+ "' and trim(INST_ID)='" + tblTermChannelInf.getTermIns().trim() + "'";
				if("0".equals(commQueryDAO.findCountBySQLQuery(countsql))){//不存在
					//插入签到表和密钥表的商户号和终端号应该是机构商户号和机构终端号
					String sql = "INSERT INTO TBL_SIGN_INF (INST_ID,INST_NAME,MCHT_ID,TERM_ID,SIGNED_FLAG,SIGN_TIME,TERMID_VALID) VALUES('"
						+ tblTermChannelInf.getTermIns() + "','"+instName+"','" + tblTermChannelInf.getInsMcht() + "','" 
						+ tblTermChannelInf.getInsTerm().trim() + "','0','" + CommonFunction.getCurrentDateTime() + "','1')";
					commQueryDAO.excute(sql);
				}
				
				//判断终端主密钥是否存在
				String countsql2 = "SELECT COUNT(*) from TBL_INST_KEY_INF where MERCH_ID='" + tblTermChannelInf.getInsMcht().trim()
						 + "' and TERM_ID='" + tblTermChannelInf.getInsTerm().trim() 
						 + "' and INST_ID='" +tblTermChannelInf.getTermIns()+ "'";
				if("0".equals(commQueryDAO.findCountBySQLQuery(countsql2))){//不存在
					//主密钥可以为空，索引值不能为空
					if(tblTermChannelInf.getReserve01()!=null && !"".equals(tblTermChannelInf.getReserve01())){
						String lmk = "";
						if(tblTermChannelInf.getLmk()!=null && !"".equals(tblTermChannelInf.getLmk().trim())){
							lmk = tblTermChannelInf.getLmk().trim();
						}
						String zmakSql = "INSERT INTO TBL_INST_KEY_INF (MERCH_ID,TERM_ID,ZTMK,INST_ID,INST_IDX) VALUES ('"
							+ tblTermChannelInf.getInsMcht().trim() + "','" + tblTermChannelInf.getInsTerm().trim() + "','" 
							+ lmk + "','" + tblTermChannelInf.getTermIns() + "','"+ tblTermChannelInf.getReserve01() + "')";
						commQueryDAO.excute(zmakSql);
					}
				}
			}
			
				}
			return rspCode;
			
	}
	
	/**
	 * 审核拒绝新增待审核的终端通道配置信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd() throws Exception {
		TblTermChannelInf tblTermChannelInf = new TblTermChannelInf();
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			tblTermChannelInf =  t11015BO.get(this.id);
			t11015BO.delete(this.id);
		}
		//保存拒绝原因
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblTermChannelInf.getTermIns());
		riskRefuse.setParam2(tblTermChannelInf.getMchtMcc());
		riskRefuse.setParam3(tblTermChannelInf.getMchtId());
		riskRefuse.setParam4(tblTermChannelInf.getMchtTermId());
		riskRefuse.setParam5(tblTermChannelInf.getInsMcc());
		riskRefuse.setParam6(tblTermChannelInf.getInsMcht());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(ADD_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("15");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过删除待审核的终端通道配置信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptDelete() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblTermChannelInf tblTermChannelInf = t11015BO.get(id);
			tblTermChannelInf.setCheckTime(CommonFunction.getCurrentDateTime());
			tblTermChannelInf.setCheckOprId(operator.getOprId());
			tblTermChannelInf.setStat(DELETE);
			rspCode = t11015BO.update(tblTermChannelInf);
			
			String delete = "delete from TBL_SIGN_INF where MCHT_ID='" + tblTermChannelInf.getInsMcht().trim()
				+ "' and TERM_ID='" + tblTermChannelInf.getInsTerm().trim() + "' and INST_ID='" + tblTermChannelInf.getTermIns() + "'";
			commQueryDAO.excute(delete);
			delete = "delete from tbl_inst_key_inf where MERCH_ID='" + tblTermChannelInf.getInsMcht().trim()
				+ "' and TERM_ID='" + tblTermChannelInf.getInsTerm().trim() + "' and INST_ID='" + tblTermChannelInf.getTermIns() + "'";
			commQueryDAO.excute(delete);
		}
		return rspCode;
	}
	
	/**
	 * 审核拒绝删除待审核的终端通道配置信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseDelete() throws Exception {
		TblTermChannelInf tblTermChannelInf = new TblTermChannelInf();
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			tblTermChannelInf = t11015BO.get(id);
			tblTermChannelInf.setCheckTime(CommonFunction.getCurrentDateTime());
			tblTermChannelInf.setCheckOprId(operator.getOprId());
			tblTermChannelInf.setStat(NORMAL);
			rspCode = t11015BO.update(tblTermChannelInf);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblTermChannelInf.getTermIns());
		riskRefuse.setParam2(tblTermChannelInf.getMchtMcc());
		riskRefuse.setParam3(tblTermChannelInf.getMchtId());
		riskRefuse.setParam4(tblTermChannelInf.getMchtTermId());
		riskRefuse.setParam5(tblTermChannelInf.getInsMcc());
		riskRefuse.setParam6(tblTermChannelInf.getInsMcht());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(DELETE_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("15");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核通过修改待审核的终端通道配置信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String acceptModify() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblTermChannelInf tblTermChannelInf = t11015BO.get(id);
			
			if(!tblTermChannelInf.getInsMcht().equals(tblTermChannelInf.getInsMchtOld()) 
					|| !tblTermChannelInf.getTermIns().equals(tblTermChannelInf.getTermInsOld())
					|| !tblTermChannelInf.getInsTerm().equals(tblTermChannelInf.getInsTermOld())){
				String upSignSql = "";
				String delete = "";
//				String instName = t10106BO.get(tblTermChannelInf.getTermIns()).getAGEN_NAME();
				String instName = "";
				//判断签到信息是否有其他终端使用(除当前终端信息外的其他终端)
				if("1".equals(checkUse(tblTermChannelInf.getInsMchtOld().trim(),tblTermChannelInf.getInsTermOld().trim()
						,tblTermChannelInf.getTermInsOld().trim()))){//不存在
					delete = "delete from TBL_SIGN_INF where trim(MCHT_ID)='" + tblTermChannelInf.getInsMchtOld().trim()
						+ "' and trim(TERM_ID)='" + tblTermChannelInf.getInsTermOld().trim() + "' and trim(INST_ID)='" + tblTermChannelInf.getTermInsOld() + "'";
					commQueryDAO.excute(delete);//20140212修改
				}
				//判断签到信息是否存在
				if("0".equals(checkSign(tblTermChannelInf.getInsMcht().trim(),tblTermChannelInf.getInsTerm().trim()
						,tblTermChannelInf.getTermIns().trim()))){//不存在
					upSignSql = "INSERT INTO TBL_SIGN_INF (INST_ID,INST_NAME,MCHT_ID,TERM_ID,SIGNED_FLAG,SIGN_TIME,TERMID_VALID) VALUES('"
						+ tblTermChannelInf.getTermIns().trim() + "','"+instName+"','" + tblTermChannelInf.getInsMcht() + "','" 
						+ tblTermChannelInf.getInsTerm().trim() + "','0','" + CommonFunction.getCurrentDateTime() + "','1')";
					commQueryDAO.excute(upSignSql);//20121105修改
				}
			}
			//20121016单独维护修改的主密钥:reserve02保存修改前的lmk，20121105密钥可能为空，故加上非空判断
			if(tblTermChannelInf.getLmk() != tblTermChannelInf.getReserve02()
					||!tblTermChannelInf.getInsMcht().equals(tblTermChannelInf.getInsMchtOld()) 
					|| !tblTermChannelInf.getTermIns().equals(tblTermChannelInf.getTermInsOld())
					|| !tblTermChannelInf.getInsTerm().equals(tblTermChannelInf.getInsTermOld())){
				String upTMKSql = "";
				String delete = "";
				String lmk = "";
				if(tblTermChannelInf.getLmk()!=null && !"".equals(tblTermChannelInf.getLmk().trim())){
					lmk = tblTermChannelInf.getLmk().trim();
				}
				//判断终端主密钥是否有其他终端使用(除当前终端信息外的其他终端)
				if("1".equals(checkUse(tblTermChannelInf.getInsMchtOld().trim(),tblTermChannelInf.getInsTermOld().trim()
						,tblTermChannelInf.getTermInsOld().trim()))){//不存在
					delete = "delete from tbl_inst_key_inf where trim(MERCH_ID)='" + tblTermChannelInf.getInsMchtOld().trim()
						+ "' and trim(TERM_ID)='" + tblTermChannelInf.getInsTermOld().trim() + "' and trim(INST_ID)='" + tblTermChannelInf.getTermInsOld() + "'";
					commQueryDAO.excute(delete);//20140212修改
				}
				//判断终端主密钥是否存在
				if("0".equals(checkTermZMK(tblTermChannelInf.getInsMcht().trim(),tblTermChannelInf.getInsTerm().trim()
						,tblTermChannelInf.getTermIns().trim()))){//不存在
					upTMKSql = "INSERT INTO TBL_INST_KEY_INF (MERCH_ID,TERM_ID,ZTMK,INST_ID,INST_IDX) VALUES ('"
						+ tblTermChannelInf.getInsMcht().trim() + "','" + tblTermChannelInf.getInsTerm().trim() + "','"
						+ lmk + "','" + tblTermChannelInf.getTermIns() + "','" 
						+ tblTermChannelInf.getReserve01() + "')";
				}else{//修改时更新终端主密钥
					upTMKSql = "UPDATE TBL_INST_KEY_INF SET ZTMK='" + lmk + "' WHERE MERCH_ID='"
						+ tblTermChannelInf.getInsMcht().trim() + "' and TERM_ID='" + tblTermChannelInf.getInsTerm().trim() 
						+ "' and INST_ID='" + tblTermChannelInf.getTermIns() +"'";
				}
				commQueryDAO.excute(upTMKSql);
			}
			
			tblTermChannelInf.setCheckTime(CommonFunction.getCurrentDateTime());
			tblTermChannelInf.setCheckOprId(operator.getOprId());
			//将修改后的字段覆盖原字段
			tblTermChannelInf.setTermInsOld(tblTermChannelInf.getTermIns());
			tblTermChannelInf.setMchtMccOld(tblTermChannelInf.getMchtMcc());
			tblTermChannelInf.setMchtIdOld(tblTermChannelInf.getMchtId());
			tblTermChannelInf.setMchtTermIdOld(tblTermChannelInf.getMchtTermId());
			tblTermChannelInf.setInsMccOld(tblTermChannelInf.getInsMcc());
			tblTermChannelInf.setInsMchtOld(tblTermChannelInf.getInsMcht());
			tblTermChannelInf.setInsTermOld(tblTermChannelInf.getInsTerm());
			tblTermChannelInf.setReserve02(tblTermChannelInf.getLmk());
			tblTermChannelInf.setStat(NORMAL);
			rspCode = t11015BO.update(tblTermChannelInf);
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 审核拒绝修改待审核的终端通道配置信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseModify() throws Exception {
		TblTermChannelInf tblTermChannelInf = new TblTermChannelInf();
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			tblTermChannelInf = t11015BO.get(this.getId());
			tblTermChannelInf.setCheckTime(CommonFunction.getCurrentDateTime());
			tblTermChannelInf.setCheckOprId(operator.getOprId());
			tblTermChannelInf.setStat(NORMAL);
			//用原字段值覆盖修改后的值
			tblTermChannelInf.setTermIns(tblTermChannelInf.getTermInsOld());
			tblTermChannelInf.setMchtMcc(tblTermChannelInf.getMchtMccOld());
			tblTermChannelInf.setMchtId(tblTermChannelInf.getMchtIdOld());
			tblTermChannelInf.setMchtTermId(tblTermChannelInf.getMchtTermIdOld());
			tblTermChannelInf.setInsMcc(tblTermChannelInf.getInsMccOld());
			tblTermChannelInf.setInsMcht(tblTermChannelInf.getInsMchtOld());
			tblTermChannelInf.setInsTerm(tblTermChannelInf.getInsTermOld());
			tblTermChannelInf.setLmk(tblTermChannelInf.getReserve02());
			rspCode = t11015BO.update(tblTermChannelInf);
		}
		
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		riskRefuse.setParam1(tblTermChannelInf.getTermIns());
		riskRefuse.setParam2(tblTermChannelInf.getMchtMcc());
		riskRefuse.setParam3(tblTermChannelInf.getMchtId());
		riskRefuse.setParam4(tblTermChannelInf.getMchtTermId());
		riskRefuse.setParam5(tblTermChannelInf.getInsMcc());
		riskRefuse.setParam6(tblTermChannelInf.getInsMcht());
		riskRefuse.setBrhId(operator.getOprBrhId());
		riskRefuse.setOprId(operator.getOprId());
		riskRefuse.setRefuseType(MODIFY_TO_CHECK);
		riskRefuse.setRefuseInfo(refuseInfo);
		riskRefuse.setFlag("15");
		
		t40206bo.saveRefuseInfo(riskRefuse);
		return Constants.SUCCESS_CODE;
	}
	
	//判断终端通道配置的操作人和审核人是否相同
	private boolean checkOperator()throws Exception {
		TblTermChannelInf tblTermChannelInf = t11015BO.get(id);
		String oprID = tblTermChannelInf.getModiOprId();
		if(operator.getOprId().equals(oprID))
			return true;//相同
		else
			return false;//不同
	}
	
	/** 判断签到信息是否存在 */
	private String checkSign(String mchtId,String termId, String InstId) throws Exception{
		String countsql = "SELECT COUNT(*) from TBL_SIGN_INF where MCHT_ID='" + mchtId.trim() 
			+ "' and TERM_ID='" + termId.trim() + "' and trim(INST_ID)='" + InstId.trim() + "'";
		String result = commQueryDAO.findCountBySQLQuery(countsql);
		return result;
	}
	
	/** 判断签到信息/终端主密钥是否有其他终端使用 */
	private String checkUse(String mchtId,String termId, String InstId) throws Exception{
		String countsql = "SELECT COUNT(*) from TBL_TERM_CHANNEL_INF where INS_MCHT_OLD='" + mchtId.trim() 
			+ "' and INS_TERM_OLD='" + termId.trim() + "' and trim(TERM_INS_OLD)='" + InstId.trim() + "'";
		String result = commQueryDAO.findCountBySQLQuery(countsql);
		return result;
	}
	
	/** 判断终端主密钥是否存在 */
	private String checkTermZMK(String mchtId,String termId, String InstId) throws Exception{
		String countsql = "SELECT COUNT(*) from TBL_INST_KEY_INF where MERCH_ID='" + mchtId.trim()
			 + "' and TERM_ID='" + termId.trim() + "' and INST_ID='" + InstId+ "'";
		String result = commQueryDAO.findCountBySQLQuery(countsql);
		return result;
	}
}
