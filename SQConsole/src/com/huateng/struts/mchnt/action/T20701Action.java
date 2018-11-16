package com.huateng.struts.mchnt.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.mchnt.T20701BO;
import com.huateng.common.StringUtil;
import com.huateng.po.mchnt.TblHisDiscAlgoPK;
import com.huateng.po.mchnt.TblHisDiscAlgoTmp;
import com.huateng.po.mchnt.TblInfDiscAlgo;
import com.huateng.po.mchnt.TblInfDiscCdTmp;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.JSONBean;

/**
 * Title:商户计费算法维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-06-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20701Action extends BaseSupport {
	
	private T20701BO t20701BO = (T20701BO) ContextUtil.getBean("T20701BO");
//	private T20706BO t20706BO = (T20706BO) ContextUtil.getBean("T20706BO");
	private String checkIds;
    public String getCheckIds() {
		return checkIds;
	}

	public void setCheckIds(String checkIds) {
		this.checkIds = checkIds;
	}
	
	/**
	 * 新增计费算法
	 * 
	 * @return
	 * 2011-8-8下午04:26:41
	 */
	public String add(){
		try {
			TblInfDiscCdTmp inf = new TblInfDiscCdTmp();
			
			String oprBrhId = getOperator().getOprBrhId();
			int max = 1;
			
			//判断是否存在序号为0001的ID
		//	String sql = "select count(1) from tbl_inf_disc_cd_temp where trim(DISC_CD) = '" + "JF" + oprBrhId.substring(0,2) + "0001" + "'" ;
			String sql = "select count(1) from tbl_inf_disc_cd_temp where trim(DISC_CD) = '" + "JF"  + "000001" + "'" ;
			BigDecimal c = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
			if (c.intValue() != 0) {
				/*sql = "select nvl(MIN(SUBSTR(DISC_CD,5,4) + 1),1) from tbl_inf_disc_cd_temp " +
					"where (SUBSTR(DISC_CD,5,4) + 1) not in (select (SUBSTR(DISC_CD,5,4) + 0) " +
					"from tbl_inf_disc_cd_temp where substr(DISC_CD,3,2) = '" + oprBrhId.substring(0,2) + "') " +
					"and substr(DISC_CD,3,2) = '" + oprBrhId.substring(0,2) + "'";*/
				sql = "select nvl(MIN(SUBSTR(DISC_CD,3,6) + 1),1) from tbl_inf_disc_cd_temp " +
						"where lpad((SUBSTR(DISC_CD,3,6) + 1),6,0) not in (select (SUBSTR(DISC_CD,3,6)) " +
						"from tbl_inf_disc_cd_temp ) " ;
				BigDecimal bg = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
				max = bg.intValue();
				if(max > 999999) {
					return returnService("该机构的计费算法已满");
				}
			}
			discCd = "JF"/* + oprBrhId.substring(0,2)*/ + CommonFunction.fillString(String.valueOf(max), '0', 6, false);
			
			inf.setDiscCd(discCd);
			inf.setDiscOrg(oprBrhId);
			inf.setDiscNm(discNm);
			inf.setLastOperIn("0");
			inf.setCre_id(operator.getOprId());
			inf.setRecCrtTs(CommonFunction.getCurrentDateTime());
			inf.setSastate(CommonFunction.ADD_TO_CHECK);
			
			List<TblInfDiscAlgo> list = new ArrayList<TblInfDiscAlgo>();
			List<TblHisDiscAlgoTmp> listHis = new ArrayList<TblHisDiscAlgoTmp>();
			
			buildDiscAlgo(list, listHis);
			
			rspCode = t20701BO.createArith(list, inf, listHis);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	/**
	 * 更新计费算法
	 * 
	 * @return
	 * 2011-8-8下午04:26:32
	 */
	@SuppressWarnings("unchecked")
	public String update(){
		try {
			TblInfDiscCdTmp inf = t20701BO.getTblInfDiscCd(discCd);
			String state=inf.getSastate();
			if (null == inf) {
				return returnService("没有找到计费算法信息，请刷新后重试！");
			}
			if(checkIds.equals("T")){
			String sql2= "select * from tbl_his_disc_algo2_tmp where FEE_TYPE = '" + discCd + "' and sa_satute<>'1' and mcht_no Is Not Null" ;
			List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql2);
			if(dataList.size() > 0) {
				return returnService("CZ");
			}
			}
			inf.setDiscNm(discNm);
			inf.setCre_id(getOperator().getOprId());
			inf.setRecCrtTs(CommonFunction.getCurrentDateTime());
			if(("2").equals(state)){
				inf.setCre_id(operator.getOprId());
				inf.setRecCrtTs(CommonFunction.getCurrentDateTime());
				inf.setSastate(CommonFunction.MODIFY_TO_CHECK);
			}
			List<TblInfDiscAlgo> list = new ArrayList<TblInfDiscAlgo>();
			List<TblHisDiscAlgoTmp> listHis = new ArrayList<TblHisDiscAlgoTmp>();
			
			buildDiscAlgo(list, listHis);
			
			rspCode = t20701BO.updateArith(list, inf, listHis);
			
			return returnService(rspCode);
			
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	/**
	 * 删除计费算法
	 * @return
	 * 2011-8-8下午04:26:23
	 */
	@SuppressWarnings("unchecked")
	public String delete(){
		try {
				String sql2= "select * from tbl_his_disc_algo2_tmp where FEE_TYPE = '" + discCd + "' and sa_satute<>'1' ";
				List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql2);
				if(dataList.size() > 0) {
					return returnService("该计算费率已经被商户手续费使用不能删除");
				}
				TblInfDiscCdTmp inf = t20701BO.getTblInfDiscCd(discCd);
				String statue=inf.getSastate();
			if(CommonFunction.ADD_TO_CHECK.equals(statue)){
				rspCode = t20701BO.deleteArith(discCd);
			}
		    if(CommonFunction.NORMAL.equals(statue)){
		    	inf.setCre_id(getOperator().getOprId());
				inf.setRecCrtTs(CommonFunction.getCurrentDateTime());
				inf.setSastate(CommonFunction.DELETE_TO_CHECK);
		  }
		    List<TblInfDiscAlgo> list = new ArrayList<TblInfDiscAlgo>();
		    List<TblHisDiscAlgoTmp> tblHisDiscAlgoTmpList=getDiscAlgoTmpByDiscId(discCd);		
			//buildDiscAlgo(list, tblHisDiscAlgoTmpList);	
			rspCode = t20701BO.updateArith(list, inf, tblHisDiscAlgoTmpList);
			//rspCode = t20701BO.deleteArith(discCd);
			return returnService(rspCode);
		} catch (Exception e) {
			return returnService(rspCode, e);
		}
	}

	public void buildDiscAlgo(List<TblInfDiscAlgo> list, List<TblHisDiscAlgoTmp> listHis){
		JSONBean jsonBean = new JSONBean();
		jsonBean.parseJSONArrayData(data);
		int len = jsonBean.getArray().size();

		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			
			//最低交易金额
			String minCapital = jsonBean.getJSONDataAt(i).getString("floorMount");
			//回佣值
			String rate = jsonBean.getJSONDataAt(i).getString("feeValue");
			//回佣类型
			String flag = jsonBean.getJSONDataAt(i).getString("flag");
			//最低手续费
			String minTax = "";
			//最高手续费
			String maxTax = "";
			
			if ("2".equals(flag) && jsonBean.getJSONDataAt(i).containsKey("minFee") 
					&& !StringUtil.isNull(jsonBean.getJSONDataAt(i).getString("minFee"))) {
				minTax = jsonBean.getJSONDataAt(i).getString("minFee");
			} else {
				minTax = "0.01";
			}
			
			if ("2".equals(flag) && jsonBean.getJSONDataAt(i).containsKey("maxFee")
					&& !StringUtil.isNull(jsonBean.getJSONDataAt(i).getString("maxFee"))) {
				maxTax = jsonBean.getJSONDataAt(i).getString("maxFee");
			} else {
				maxTax = "999999999";
			}
			if(maxTax.equals("0")||"0.00".equals(maxTax))
				maxTax = "999999999";
			//交易代码
			String txnCode = jsonBean.getJSONDataAt(i).getString("txnNum");
			
			TblHisDiscAlgoTmp temp = new TblHisDiscAlgoTmp();
			TblHisDiscAlgoPK key = new TblHisDiscAlgoPK();
			key.setDiscId(discCd);
			key.setIndexNum(i);
			temp.setId(key);
			temp.setMinFee(new BigDecimal(minTax));
			temp.setMaxFee(new BigDecimal(maxTax));
			temp.setTxnNum(txnCode);
			temp.setFeeValue(new BigDecimal(rate));
			temp.setFloorMount(BigDecimal.valueOf(CommonFunction.getDValue(minCapital, _defaultDoutbl)));
			temp.setFlag(CommonFunction.getInt(flag, -1));
			temp.setUpperMount(new BigDecimal("0"));
			temp.setRecCrtTs(getOperator().getOprId());
			//temp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			temp.setRecCrtTs(CommonFunction.getCurrentDateTime());
			
			listHis.add(temp);
		}
	}
	/**
	 * 根据临时表计费代码获取计费算法信息
	 * @param discId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TblHisDiscAlgoTmp> getDiscAlgoTmpByDiscId(String discId){
		String sql="select DISC_ID,min_fee,max_fee,floor_amount,flag,fee_value,INDEX_NUM  from tbl_his_disc_algo_tmp where DISC_ID='"+discId+"' order by INDEX_NUM";
		List<Object[]> list=commQueryDAO.findBySQLQuery(sql);
		List<TblHisDiscAlgoTmp> algoList=new ArrayList<TblHisDiscAlgoTmp>();
		for(Object[] obj:list){
			TblHisDiscAlgoTmp algo=new TblHisDiscAlgoTmp();
			algo.setMinFee((BigDecimal)obj[1]);
			algo.setMaxFee((BigDecimal)obj[2]);
			algo.setFloorMount((BigDecimal)obj[3]);
			algo.setFlag(((BigDecimal)obj[4]).intValue());
			algo.setFeeValue((BigDecimal)obj[5]);
			algo.setId(new TblHisDiscAlgoPK(String.valueOf(obj[0]),((BigDecimal)obj[6]).intValue()));
			algoList.add(algo);
		}
		return algoList;
	}
	
	private final static Double _defaultDoutbl = new Double(0);	
	
	public TblHisDiscAlgoPK id;

	public java.math.BigDecimal minFee;
	public java.math.BigDecimal maxFee;
	public java.math.BigDecimal floorMount;
	public java.math.BigDecimal upperMount;
	public java.lang.Integer flag;
	public java.math.BigDecimal feeValue;
	public java.lang.String recUpdUsrId;
	public java.lang.String txnNum;
	
	public String data;
	
	//手续费类型IDdisc_cd
	public java.lang.String discCd;
	//手续费名称
	public java.lang.String discNm;
	//手续费所属机构
	public java.lang.String discOrg;	
	//最后操作状态
	public java.lang.String recUpdUserId;

	public TblHisDiscAlgoPK getId() {
		return id;
	}

	public void setId(TblHisDiscAlgoPK id) {
		this.id = id;
	}

	public java.math.BigDecimal getMinFee() {
		return minFee;
	}

	public void setMinFee(java.math.BigDecimal minFee) {
		this.minFee = minFee;
	}

	public java.math.BigDecimal getMaxFee() {
		return maxFee;
	}

	public void setMaxFee(java.math.BigDecimal maxFee) {
		this.maxFee = maxFee;
	}

	public java.math.BigDecimal getFloorMount() {
		return floorMount;
	}

	public void setFloorMount(java.math.BigDecimal floorMount) {
		this.floorMount = floorMount;
	}

	public java.math.BigDecimal getUpperMount() {
		return upperMount;
	}

	public void setUpperMount(java.math.BigDecimal upperMount) {
		this.upperMount = upperMount;
	}

	public java.lang.Integer getFlag() {
		return flag;
	}

	public void setFlag(java.lang.Integer flag) {
		this.flag = flag;
	}

	public java.math.BigDecimal getFeeValue() {
		return feeValue;
	}

	public void setFeeValue(java.math.BigDecimal feeValue) {
		this.feeValue = feeValue;
	}

	public java.lang.String getRecUpdUsrId() {
		return recUpdUsrId;
	}

	public void setRecUpdUsrId(java.lang.String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
	}

	public java.lang.String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(java.lang.String txnNum) {
		this.txnNum = txnNum;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public java.lang.String getDiscCd() {
		return discCd;
	}

	public void setDiscCd(java.lang.String discCd) {
		this.discCd = discCd;
	}

	public java.lang.String getDiscNm() {
		return discNm;
	}

	public void setDiscNm(java.lang.String discNm) {
		this.discNm = discNm;
	}

	public java.lang.String getDiscOrg() {
		return discOrg;
	}

	public void setDiscOrg(java.lang.String discOrg) {
		this.discOrg = discOrg;
	}

	public java.lang.String getRecUpdUserId() {
		return recUpdUserId;
	}

	public void setRecUpdUserId(java.lang.String recUpdUserId) {
		this.recUpdUserId = recUpdUserId;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}
}
