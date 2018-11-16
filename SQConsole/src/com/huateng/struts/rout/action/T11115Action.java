package com.huateng.struts.rout.action;

import org.apache.log4j.Logger;

import com.huateng.common.Constants;
import com.huateng.po.TblCtlCardInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
/**
 * 卡Bin地区路由配置
 * @author Li
 *
 */
public class T11115Action extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(T11115Action.class);
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		if("add".equals(method)) {
			log("添加卡Bin地区路由配置信息");
			rspCode = add();
		} else if("update".equals(method)) {
			log("更新卡Bin地区路由配置信息");
			rspCode = update();
		} else if("delete".equals(method)) {
			log("删除卡Bin地区路由配置信息");
			rspCode = delete();
		} else if("accept".equals(method)) {
			log("删除卡Bin地区路由配置信息");
			rspCode = accept();
		} else if("refuse".equals(method)) {
			log("删除卡Bin地区路由配置信息");
			rspCode = refuse();
		} else {
			return "未知的交易类型";
		}
		return rspCode;
	}
	
	private String refuse() throws Exception {
		log.info("卡Bin地区路由配置信息审核拒绝");
		try {
			try {
				String refuseTmpSql = "update tbl_cardbin_area_route_inf_tmp set state = '5', card_bin = '"+card_bin+"', area_no = '"+area_no+"', inst_code = '"+inst_code+"',upt_per = '"+operator.getOprId()+"', upt_date='"+CommonFunction.getCurrentDateTime()+"'  where card_bin = '"+card_bin+"' and area_no = '"+area_no+"' ";
				commQueryDAO.excute(refuseTmpSql);
				
			} catch (Exception e) {
				log.error("卡Bin地区路由配置信息审核拒绝错误TMP", e);
			}
			String refuseSql = "update tbl_cardbin_area_route_inf set state = '5', card_bin = '"+card_bin+"', area_no = '"+area_no+"', inst_code = '"+inst_code+"',upt_per = '"+operator.getOprId()+"', upt_date='"+CommonFunction.getCurrentDateTime()+"'  where card_bin = '"+card_bin+"' and area_no = '"+area_no+"' ";
			commQueryDAO.excute(refuseSql);
		} catch (Exception e) {
			log.error("卡Bin地区路由配置信息审核拒绝错误", e);
		}
		return Constants.SUCCESS_CODE;
	}
	private String accept() throws Exception {
		log.info("卡Bin地区路由配置信息审核通过");
		try {
			try {
				String acceptTmpSql = "update tbl_cardbin_area_route_inf_tmp set state = '2', card_bin = '"+card_bin+"', area_no = '"+area_no+"', inst_code = '"+inst_code+"',upt_per = '"+operator.getOprId()+"', upt_date='"+CommonFunction.getCurrentDateTime()+"' where card_bin = '"+card_bin+"' and area_no = '"+area_no+"' ";
				commQueryDAO.excute(acceptTmpSql);
			} catch (Exception e) {
				log.error("卡Bin地区路由配置信息审核通过错误TMP", e);
			}
			String acceptSql = "update tbl_cardbin_area_route_inf_tmp set state = '2', card_bin = '"+card_bin+"', area_no = '"+area_no+"', inst_code = '"+inst_code+"',upt_per = '"+operator.getOprId()+"', upt_date='"+CommonFunction.getCurrentDateTime()+"' where card_bin = '"+card_bin+"' and area_no = '"+area_no+"' ";
			commQueryDAO.excute(acceptSql);
		} catch (Exception e) {
			log.error("卡Bin地区路由配置信息审核通过错误", e);
		}
		return Constants.SUCCESS_CODE;
	}
	private String add() throws Exception {
		try {
			log.info("添加卡Bin地区路由配置信息");
			String addStr = "INSERT INTO TBL_CARDBIN_AREA_ROUTE_INF_TMP (CARD_BIN, AREA_NO, INST_CODE, STATE, CRT_PER, CRT_DATE, UPT_PER, UPT_DATE) VALUES ('"+card_bin+"', '"+area_no+"', '"+inst_code+"', '0', '"+operator.getOprId()+"', '"+CommonFunction.getCurrentDateTime()+"', '', '')";
			commQueryDAO.excute(addStr);
		} catch (Exception e) {
			log.error("添加卡Bin地区路由配置信息失败！", e);
		}
		return Constants.SUCCESS_CODE;
	}
	private String update() throws Exception {
		jsonBean.parseJSONArrayData(getParameterList());
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			try {
				jsonBean.setObject(jsonBean.getJSONDataAt(i));
				String card_bin = (String) jsonBean.getJSONDataAt(i).get("card_bin");
				String area_no = (String) jsonBean.getJSONDataAt(i).get("area_no");
				String inst_code = (String) jsonBean.getJSONDataAt(i).get("inst_code");
//				try {
					String delTmpSql = "update tbl_cardbin_area_route_inf_tmp set state = '3', card_bin = '"+card_bin+"', area_no = '"+area_no+"', inst_code = '"+inst_code+"' where card_bin = '"+card_bin+"' and area_no = '"+area_no+"' ";
					commQueryDAO.excute(delTmpSql);
//				} catch (Exception e) {
//					log.error("删除卡Bin地区路由配置信息错误TMP表", e);
//				}
//				String delSql = "update tbl_cardbin_area_route_inf set state = '3' where card_bin = '"+card_bin+"' and area_no = '"+area_no+"' ";
//				commQueryDAO.excute(delSql);
			} catch (Exception e) {
				String delSql = "update tbl_cardbin_area_route_inf_tmp set state = '"+state+"' where card_bin = '"+card_bin+"' and area_no = '"+area_no+"' ";
				commQueryDAO.excute(delSql);
				log.error("更新卡Bin地区路由配置信息错误", e);
				return "更新卡Bin地区路由配置信息错误";
			}
		}
		return Constants.SUCCESS_CODE;
	}
	
	private String delete() throws Exception {
		try {
			try {
				String delTmpSql = "update tbl_cardbin_area_route_inf_tmp set state = '4' where card_bin = '"+card_bin+"' and area_no = '"+area_no+"' ";
				commQueryDAO.excute(delTmpSql);
			} catch (Exception e) {
				log.error("删除卡Bin地区路由配置信息错误TMP表", e);
			}
//			String delSql = "update tbl_cardbin_area_route_inf set state = '1' where card_bin = '"+card_bin+"' and area_no = '"+area_no+"' ";
//			commQueryDAO.excute(delSql);
		} catch (Exception e) {
//			String delSql = "update tbl_cardbin_area_route_inf_tmp set state = '"+state+"' where card_bin = '"+card_bin+"' and area_no = '"+area_no+"' ";
//			commQueryDAO.excute(delSql);
			log.error("删除卡Bin地区路由配置信息错误", e);
		}
		return Constants.SUCCESS_CODE;
	}
	
	private String parameterList;
	private String card_bin;
	private String area_no;
	private String inst_code;
	private String state;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getInst_code() {
		return inst_code;
	}
	public void setInst_code(String inst_code) {
		this.inst_code = inst_code;
	}
	public String getCard_bin() {
		return card_bin;
	}
	public void setCard_bin(String card_bin) {
		this.card_bin = card_bin;
	}
	public String getArea_no() {
		return area_no;
	}
	public void setArea_no(String area_no) {
		this.area_no = area_no;
	}
	public String getParameterList() {
		return parameterList;
	}

	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}
}
