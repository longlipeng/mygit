package com.huateng.struts.rout.action;

import com.huateng.bo.base.T10106BO;
import com.huateng.bo.base.T10108BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.bo.rout.T11015BO;
import com.huateng.bo.rout.T11018BO;
import com.huateng.common.Constants;
import com.huateng.po.base.AgencyInfoPK;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.po.rout.TblTermChannelInf;
import com.huateng.po.rout.TblTermChannelInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T11016Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T11015BO t11015BO = (T11015BO)ContextUtil.getBean("T11015BO");
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	//机构信息维护
	private T10106BO t10106BO = (T10106BO) ContextUtil.getBean("T10106BO");
	private T11018BO t11018BO = (T11018BO)ContextUtil.getBean("T11018BO");
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
	
	

	public T11018BO getT11018BO() {
		return t11018BO;
	}

	public void setT11018BO(T11018BO t11018bo) {
		t11018BO = t11018bo;
	}

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
			TblTermChannelInfTmp tblTermChannelInfTmp = t11015BO.getTmp(idkey);
			String state = tblTermChannelInfTmp.getStat();
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
//		if(this.checkOperator())
//			return "提交人与审核人不能是同一个人";
//		else{
			
			jsonBean.parseJSONArrayData(getInfList());
			
			int len = jsonBean.getArray().size();
			for(int i = 0;i < len;i++){
				jsonBean.setObject(jsonBean.getJSONDataAt(i));
				TblTermChannelInfTmp tblTermChannelInfTmp = t11015BO.getTmp(jsonBean.getJSONDataAt(i).getString("id"));
				BeanUtils.setObjectWithPropertiesValue(tblTermChannelInfTmp, jsonBean,false);
//				String idkey = jsonBean.getJSONDataAt(i).getString("id");
//				
//				TblTermChannelInfTmp tblTermChannelInfTmp = t11015BO.getTmp(idkey);
//				TblTermChannelInf tblTermChannelInf = t11015BO.get(id);
				tblTermChannelInfTmp.setCheckTime(CommonFunction.getCurrentDateTime());
				tblTermChannelInfTmp.setCheckOprId(operator.getOprId());
				tblTermChannelInfTmp.setStat(NORMAL);
				
				TblTermChannelInf tblTermChannelInf = new TblTermChannelInf();
				BeanUtils.copyProperties(tblTermChannelInf, tblTermChannelInfTmp);
				rspCode = t11015BO.update(tblTermChannelInfTmp);
				rspCode = t11018BO.add(tblTermChannelInf);

			
/*				StringBuffer sb =new StringBuffer();
				sb.append("SELECT TRAN_TYPE FROM TBL_AGENCY_INFO WHERE AGEN_ID ='").append(tblTermChannelInfTmp.getTermIns()).append("'");
				String trantype =(String)commQueryDAO.findBySQLQuery(sb.toString()).get(0);
				AgencyInfoPK agencyinfopk = new AgencyInfoPK();
				agencyinfopk.setAGEN_ID(tblTermChannelInfTmp.getTermIns());
				agencyinfopk.setTRAN_TYPE(trantype);
				String instName = t10106BO.get(agencyinfopk).getAGEN_NAME();*/
				
				/*//判断签到信息是否存在
				String countsql = "SELECT COUNT(*) from TBL_SIGN_INF where MCHT_ID='" + tblTermChannelInfTmp.getInsMcht().trim() 
						+ "' and TERM_ID='" + tblTermChannelInfTmp.getInsTerm().trim() 
						+ "' and trim(INST_ID)='" + tblTermChannelInfTmp.getTermIns().trim() + "'";
				if("0".equals(commQueryDAO.findCountBySQLQuery(countsql))){//不存在
					//插入签到表和密钥表的商户号和终端号应该是机构商户号和机构终端号
					String sql = "INSERT INTO TBL_SIGN_INF (INST_ID,INST_NAME,MCHT_ID,TERM_ID,SIGNED_FLAG,SIGN_TIME,TERMID_VALID) VALUES('"
						+ tblTermChannelInfTmp.getTermIns() + "','"+instName+"','" + tblTermChannelInfTmp.getInsMcht() + "','" 
						+ tblTermChannelInfTmp.getInsTerm().trim() + "','0','" + CommonFunction.getCurrentDateTime() + "','1')";
					commQueryDAO.excute(sql);
				}
				
				//判断终端主密钥是否存在
				String countsql2 = "SELECT COUNT(*) from TBL_INST_KEY_INF where MERCH_ID='" + tblTermChannelInfTmp.getInsMcht().trim()
						 + "' and TERM_ID='" + tblTermChannelInfTmp.getInsTerm().trim() 
						 + "' and INST_ID='" +tblTermChannelInfTmp.getTermIns()+ "'";
				if("0".equals(commQueryDAO.findCountBySQLQuery(countsql2))){//不存在
					//主密钥可以为空，索引值不能为空
					if(tblTermChannelInfTmp.getReserve01()!=null && !"".equals(tblTermChannelInfTmp.getReserve01())){
						String lmk = "";
						if(tblTermChannelInfTmp.getLmk()!=null && !"".equals(tblTermChannelInfTmp.getLmk().trim())){
							lmk = tblTermChannelInfTmp.getLmk().trim();
						}
						String zmakSql = "INSERT INTO TBL_INST_KEY_INF (MERCH_ID,TERM_ID,ZTMK,INST_ID,INST_IDX) VALUES ('"
							+ tblTermChannelInfTmp.getInsMcht().trim() + "','" + tblTermChannelInfTmp.getInsTerm().trim() + "','" 
							+ lmk + "','" + tblTermChannelInfTmp.getTermIns() + "','"+ tblTermChannelInfTmp.getReserve01() + "')";
						commQueryDAO.excute(zmakSql);
					}
				}*/
			}
//			}
			
			
		return rspCode;
	}
	
	/**
	 * 审核拒绝新增待审核的终端通道配置信息
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuseAdd() throws Exception {
		
		jsonBean.parseJSONArrayData(getInfList());
		
		int len = jsonBean.getArray().size();
		for(int i = 0;i < len;i++){
			String idkey = jsonBean.getJSONDataAt(i).getString("id");
			TblTermChannelInfTmp tblTermChannelInfTmp = t11015BO.getTmp(idkey);
			if(this.checkOperator())
				return "提交人与审核人不能是同一个人";
			else{
//				tblTermChannelInf =  t11015BO.get(this.id);
				t11015BO.delete(idkey);
			}
			//保存拒绝原因
			TblRiskRefuse riskRefuse = new TblRiskRefuse();
			riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
			riskRefuse.setParam1(tblTermChannelInfTmp.getTermIns());
			riskRefuse.setParam2(tblTermChannelInfTmp.getMchtMcc());
			riskRefuse.setParam3(tblTermChannelInfTmp.getMchtId());
			riskRefuse.setParam4(tblTermChannelInfTmp.getMchtTermId());
			riskRefuse.setParam5(tblTermChannelInfTmp.getInsMcc());
			riskRefuse.setParam6(tblTermChannelInfTmp.getInsMcht());
			riskRefuse.setBrhId(operator.getOprBrhId());
			riskRefuse.setOprId(operator.getOprId());
			riskRefuse.setRefuseType(ADD_TO_CHECK);
			riskRefuse.setRefuseInfo(refuseInfo);
			riskRefuse.setFlag("15");
			
			t40206bo.saveRefuseInfo(riskRefuse);
			}


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
			jsonBean.parseJSONArrayData(getInfList());
			
			int len = jsonBean.getArray().size();
			for(int i = 0;i < len;i++){
				jsonBean.setObject(jsonBean.getJSONDataAt(i));
				TblTermChannelInfTmp tblTermChannelInfTmp = t11015BO.getTmp(jsonBean.getJSONDataAt(i).getString("id"));
				BeanUtils.setObjectWithPropertiesValue(tblTermChannelInfTmp, jsonBean,false);
//				String idkey = jsonBean.getJSONDataAt(i).getString("id");
//				TblTermChannelInfTmp tblTermChannelInfTmp = t11015BO.getTmp(idkey);
				tblTermChannelInfTmp.setCheckTime(CommonFunction.getCurrentDateTime());
				tblTermChannelInfTmp.setCheckOprId(operator.getOprId());
				tblTermChannelInfTmp.setStat(DELETE);
				rspCode = t11015BO.update(tblTermChannelInfTmp);
				rspCode = t11018BO.delete(jsonBean.getJSONDataAt(i).getString("id"));
				
				/*String delete = "delete from TBL_SIGN_INF where MCHT_ID='" + tblTermChannelInfTmp.getInsMcht().trim()
					+ "' and TERM_ID='" + tblTermChannelInfTmp.getInsTerm().trim() + "' and INST_ID='" + tblTermChannelInfTmp.getTermIns() + "'";
				commQueryDAO.excute(delete);
				delete = "delete from tbl_inst_key_inf where MERCH_ID='" + tblTermChannelInfTmp.getInsMcht().trim()
					+ "' and TERM_ID='" + tblTermChannelInfTmp.getInsTerm().trim() + "' and INST_ID='" + tblTermChannelInfTmp.getTermIns() + "'";
				commQueryDAO.excute(delete);*/
				}
			
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
		jsonBean.parseJSONArrayData(getInfList());
		
		int len = jsonBean.getArray().size();
		for(int i = 0;i < len;i++){
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			TblTermChannelInfTmp tblTermChannelInfTmp = t11015BO.getTmp(jsonBean.getJSONDataAt(i).getString("id"));
			BeanUtils.setObjectWithPropertiesValue(tblTermChannelInfTmp, jsonBean,false);
//			String idkey = jsonBean.getJSONDataAt(i).getString("id");
//			TblTermChannelInf tblTermChannelInf = t11015BO.get(idkey);
			if(this.checkOperator())
				return "提交人与审核人不能是同一个人";
			else{

//				tblTermChannelInf = t11015BO.get(id);
				tblTermChannelInfTmp.setCheckTime(CommonFunction.getCurrentDateTime());
				tblTermChannelInfTmp.setCheckOprId(operator.getOprId());
				tblTermChannelInfTmp.setStat(NORMAL);
				TblTermChannelInf tblTermChannelInf = new TblTermChannelInf();
				BeanUtils.copyProperties(tblTermChannelInf, tblTermChannelInfTmp);
				rspCode = t11015BO.update(tblTermChannelInfTmp);
				rspCode = t11018BO.update(tblTermChannelInf);
				
			}
			
			TblRiskRefuse riskRefuse = new TblRiskRefuse();
			riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
			riskRefuse.setParam1(tblTermChannelInfTmp.getTermIns());
			riskRefuse.setParam2(tblTermChannelInfTmp.getMchtMcc());
			riskRefuse.setParam3(tblTermChannelInfTmp.getMchtId());
			riskRefuse.setParam4(tblTermChannelInfTmp.getMchtTermId());
			riskRefuse.setParam5(tblTermChannelInfTmp.getInsMcc());
			riskRefuse.setParam6(tblTermChannelInfTmp.getInsMcht());
			riskRefuse.setBrhId(operator.getOprBrhId());
			riskRefuse.setOprId(operator.getOprId());
			riskRefuse.setRefuseType(DELETE_TO_CHECK);
			riskRefuse.setRefuseInfo(refuseInfo);
			riskRefuse.setFlag("15");
			
			t40206bo.saveRefuseInfo(riskRefuse);
			}

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
			jsonBean.parseJSONArrayData(getInfList());
			
			int len = jsonBean.getArray().size();
			for(int i = 0;i < len;i++){
//				String idkey = jsonBean.getJSONDataAt(i).getString("id");
//				TblTermChannelInf tblTermChannelInf = t11015BO.get(idkey);
				jsonBean.setObject(jsonBean.getJSONDataAt(i));
				TblTermChannelInfTmp tblTermChannelInfTmp = t11015BO.getTmp(jsonBean.getJSONDataAt(i).getString("id"));
				BeanUtils.setObjectWithPropertiesValue(tblTermChannelInfTmp, jsonBean,false);
				
				TblTermChannelInf tblTermChannelInf = t11018BO.get(jsonBean.getJSONDataAt(i).getString("id"));
				BeanUtils.setObjectWithPropertiesValue(tblTermChannelInf, jsonBean,false);
				
				if(!tblTermChannelInfTmp.getInsMcht().equals(tblTermChannelInf.getInsMcht()) 
						|| !tblTermChannelInfTmp.getTermIns().equals(tblTermChannelInf.getTermIns())
						|| !tblTermChannelInfTmp.getInsTerm().equals(tblTermChannelInf.getInsTerm())){
					String upSignSql = "";
					String delete = "";
					/*StringBuffer sb =new StringBuffer();
					sb.append("SELECT TRAN_TYPE FROM TBL_AGENCY_INFO WHERE AGEN_ID ='").append(tblTermChannelInfTmp.getTermIns()).append("'");
					String trantype =(String)commQueryDAO.findBySQLQuery(sb.toString()).get(0);
					AgencyInfoPK agencyinfopk = new AgencyInfoPK();
					agencyinfopk.setAGEN_ID(tblTermChannelInfTmp.getTermIns());
					agencyinfopk.setTRAN_TYPE(trantype);
					String instName = t10106BO.get(agencyinfopk).getAGEN_NAME();*/
//					String instName = t10106BO.get(tblTermChannelInf.getTermIns()).getAGEN_NAME();
					//判断签到信息是否有其他终端使用(除当前终端信息外的其他终端)
					/*if("1".equals(checkUse(tblTermChannelInf.getInsMcht().trim(),tblTermChannelInf.getInsTerm().trim()
							,tblTermChannelInf.getTermIns().trim()))){//不存在
						delete = "delete from TBL_SIGN_INF where trim(MCHT_ID)='" + tblTermChannelInf.getInsMcht().trim()
							+ "' and trim(TERM_ID)='" + tblTermChannelInf.getInsTerm().trim() + "' and trim(INST_ID)='" + tblTermChannelInf.getTermIns() + "'";
						commQueryDAO.excute(delete);//20140212修改
					}*/
					//判断签到信息是否存在
					/*if("0".equals(checkSign(tblTermChannelInfTmp.getInsMcht().trim(),tblTermChannelInfTmp.getInsTerm().trim()
							,tblTermChannelInfTmp.getTermIns().trim()))){//不存在
						upSignSql = "INSERT INTO TBL_SIGN_INF (INST_ID,INST_NAME,MCHT_ID,TERM_ID,SIGNED_FLAG,SIGN_TIME,TERMID_VALID) VALUES('"
							+ tblTermChannelInfTmp.getTermIns().trim() + "','"+instName+"','" + tblTermChannelInfTmp.getInsMcht() + "','" 
							+ tblTermChannelInfTmp.getInsTerm().trim() + "','0','" + CommonFunction.getCurrentDateTime() + "','1')";
						commQueryDAO.excute(upSignSql);//20121105修改
					}*/
				}
				//20121016单独维护修改的主密钥:reserve02保存修改前的lmk，20121105密钥可能为空，故加上非空判断
				/*if(tblTermChannelInfTmp.getLmk() != tblTermChannelInfTmp.getReserve02()
						||!tblTermChannelInfTmp.getInsMcht().equals(tblTermChannelInf.getInsMcht()) 
						|| !tblTermChannelInfTmp.getTermIns().equals(tblTermChannelInf.getTermIns())
						|| !tblTermChannelInfTmp.getInsTerm().equals(tblTermChannelInf.getInsTerm())){
					String upTMKSql = "";
					String delete = "";
					String lmk = "";
					if(tblTermChannelInfTmp.getLmk()!=null && !"".equals(tblTermChannelInfTmp.getLmk().trim())){
						lmk = tblTermChannelInfTmp.getLmk().trim();
					}
					//判断终端主密钥是否有其他终端使用(除当前终端信息外的其他终端)
					if("1".equals(checkUse(tblTermChannelInf.getInsMcht().trim(),tblTermChannelInf.getInsTerm().trim()
							,tblTermChannelInf.getTermIns().trim()))){//不存在
						delete = "delete from tbl_inst_key_inf where trim(MERCH_ID)='" + tblTermChannelInf.getInsMcht().trim()
							+ "' and trim(TERM_ID)='" + tblTermChannelInf.getInsTerm().trim() + "' and trim(INST_ID)='" + tblTermChannelInf.getTermIns() + "'";
						commQueryDAO.excute(delete);//20140212修改
					}
					//判断终端主密钥是否存在
					if("0".equals(checkTermZMK(tblTermChannelInfTmp.getInsMcht().trim(),tblTermChannelInfTmp.getInsTerm().trim()
							,tblTermChannelInfTmp.getTermIns().trim()))){//不存在
						upTMKSql = "INSERT INTO TBL_INST_KEY_INF (MERCH_ID,TERM_ID,ZTMK,INST_ID,INST_IDX) VALUES ('"
							+ tblTermChannelInfTmp.getInsMcht().trim() + "','" + tblTermChannelInfTmp.getInsTerm().trim() + "','"
							+ lmk + "','" + tblTermChannelInfTmp.getTermIns() + "','" 
							+ tblTermChannelInfTmp.getReserve01() + "')";
					}else{//修改时更新终端主密钥
						upTMKSql = "UPDATE TBL_INST_KEY_INF SET ZTMK='" + lmk + "' WHERE MERCH_ID='"
							+ tblTermChannelInfTmp.getInsMcht().trim() + "' and TERM_ID='" + tblTermChannelInfTmp.getInsTerm().trim() 
							+ "' and INST_ID='" + tblTermChannelInfTmp.getTermIns() +"'";
					}
					commQueryDAO.excute(upTMKSql);
				}*/
				
				tblTermChannelInfTmp.setCheckTime(CommonFunction.getCurrentDateTime());
				tblTermChannelInfTmp.setCheckOprId(operator.getOprId());
				tblTermChannelInfTmp.setReserve02(tblTermChannelInfTmp.getLmk());
				tblTermChannelInfTmp.setStat(NORMAL);
				
				
//				tblTermChannelInf.setCheckTime(CommonFunction.getCurrentDateTime());
//				tblTermChannelInf.setCheckOprId(operator.getOprId());
//				tblTermChannelInf.setReserve02(tblTermChannelInfTmp.getLmk());
//				tblTermChannelInf.setStat(NORMAL);
//				TblTermChannelInf tblTermChannelInf = new TblTermChannelInf();
				BeanUtils.copyProperties(tblTermChannelInf, tblTermChannelInfTmp);
				rspCode = t11015BO.update(tblTermChannelInfTmp);
				rspCode = t11018BO.update(tblTermChannelInf);
				}
			
			
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

		jsonBean.parseJSONArrayData(getInfList());
		
		int len = jsonBean.getArray().size();
		for(int i = 0;i < len;i++){
//			String idkey = jsonBean.getJSONDataAt(i).getString("id");
//			TblTermChannelInf tblTermChannelInf = t11015BO.get(idkey);
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			TblTermChannelInf tblTermChannelInf = t11018BO.get(jsonBean.getJSONDataAt(i).getString("id"));
			BeanUtils.setObjectWithPropertiesValue(tblTermChannelInf, jsonBean,false);
			if(this.checkOperator())
				return "提交人与审核人不能是同一个人";
			else{
//				tblTermChannelInf = t11015BO.get(this.getId());
				tblTermChannelInf.setCheckTime(CommonFunction.getCurrentDateTime());
				tblTermChannelInf.setCheckOprId(operator.getOprId());
				tblTermChannelInf.setStat(NORMAL);
				//用原字段值覆盖修改后的值
//				tblTermChannelInf.setTermIns(tblTermChannelInf.getTermInsOld());
//				tblTermChannelInf.setMchtMcc(tblTermChannelInf.getMchtMccOld());
//				tblTermChannelInf.setMchtId(tblTermChannelInf.getMchtIdOld());
//				tblTermChannelInf.setMchtTermId(tblTermChannelInf.getMchtTermIdOld());
//				tblTermChannelInf.setInsMcc(tblTermChannelInf.getInsMccOld());
//				tblTermChannelInf.setInsMcht(tblTermChannelInf.getInsMchtOld());
//				tblTermChannelInf.setInsTerm(tblTermChannelInf.getInsTermOld());
				tblTermChannelInf.setLmk(tblTermChannelInf.getReserve02());
				TblTermChannelInfTmp tblTermChannelInfTmp = new TblTermChannelInfTmp();
				BeanUtils.copyProperties(tblTermChannelInfTmp,tblTermChannelInf );
				rspCode = t11015BO.update(tblTermChannelInfTmp);
				rspCode = t11018BO.update(tblTermChannelInf);
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
			}

		return Constants.SUCCESS_CODE;
	}
	
	//判断终端通道配置的操作人和审核人是否相同
	private boolean checkOperator()throws Exception {
		int len = jsonBean.getArray().size();
		for(int i = 0;i < len;i++){
			String idkey = jsonBean.getJSONDataAt(i).getString("id");
			TblTermChannelInfTmp tblTermChannelInfTmp = t11015BO.getTmp(idkey);
//			TblTermChannelInf tblTermChannelInf = t11015BO.get(id);
			String oprID = tblTermChannelInfTmp.getModiOprId();
			if(operator.getOprId().equals(oprID))
				return true;//相同
			else
				return false;//不同
			}
		return true;
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
		String countsql = "SELECT COUNT(*) from TBL_TERM_CHANNEL_INF_TMP where INS_MCHT_OLD='" + mchtId.trim() 
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
