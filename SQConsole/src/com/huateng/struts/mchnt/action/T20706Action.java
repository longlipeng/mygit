package com.huateng.struts.mchnt.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;


import com.huateng.bo.mchnt.T20701BO;
import com.huateng.bo.mchnt.T20706BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;
import com.huateng.po.mchnt.TblHisDiscAlgo1;
import com.huateng.po.mchnt.TblHisDiscAlgoPK;
import com.huateng.po.mchnt.TblHisDiscAlgoTmp;
import com.huateng.po.mchnt.TblInfDiscAlgo;
import com.huateng.po.mchnt.TblInfDiscCd;
import com.huateng.po.mchnt.TblHisDiscAlgo;
import com.huateng.po.mchnt.TblInfDiscCdTmp;
import com.huateng.po.risk.TblRiskRefuse;

public class T20706Action extends BaseAction{
	private static final long serialVersionUID = 1L;
	private String discCd;
	private String refuseInfo;
	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	public String getDiscCd() {
		return discCd;
	}

	public void setDiscCd(String discCd) {
		this.discCd = discCd;
	}

	private T20701BO t20701BO = (T20701BO) ContextUtil.getBean("T20701BO");
	private T20706BO t20706BO = (T20706BO) ContextUtil.getBean("T20706BO");
	private T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		String sql = "SELECT CRE_ID FROM tbl_inf_disc_cd_temp WHERE DISC_CD= '" + discCd + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}
		try {
			if("accept".equals(getMethod())) {			
					rspCode = accept();			
			} else if("refuse".equals(getMethod())) {
				rspCode = refuse();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对计费算法审核操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	@SuppressWarnings("unchecked")
	private String accept() throws Exception {
		//新增
		//获得临时表的这条数据
		
		TblInfDiscCdTmp tblInfDiscCdTmp=t20701BO.getTblInfDiscCd(discCd);
		String state=tblInfDiscCdTmp.getSastate();
		tblInfDiscCdTmp.setRecUpdUserId(operator.getOprId());
		tblInfDiscCdTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
	//新增审核
		if(("0").equals(state)){
			tblInfDiscCdTmp.setSastate(CommonFunction.NORMAL);
			//修改状态改到临时表
			List<TblInfDiscCdTmp> tblInfDiscCdTmpList=new ArrayList<TblInfDiscCdTmp>();
			tblInfDiscCdTmpList.add(tblInfDiscCdTmp);
			List<TblInfDiscAlgo> list = new ArrayList<TblInfDiscAlgo>();
			List<TblHisDiscAlgoTmp> tblHisDiscAlgoTmpList=getDiscAlgoTmpByDiscId(discCd);//直接用jdbc查询查询数据		
			t20701BO.updateArith(list, tblInfDiscCdTmp, tblHisDiscAlgoTmpList);//临时表			
			//从临时表中获得数据到实际表
			// 复制新的信息
			TblInfDiscCd tblInfDiscCd=new TblInfDiscCd(); 
			TblHisDiscAlgo tblHisDiscAlgo=new TblHisDiscAlgo();
			BeanUtils.copyProperties(tblInfDiscCdTmp,tblInfDiscCd);	
			t20706BO.add(tblInfDiscCd);
			for(int i=0;i<tblHisDiscAlgoTmpList.size();i++){//把临时表查出来的额每条数据都放到真实表里面去
			TblHisDiscAlgoTmp tblHisDiscAlgoTmp=tblHisDiscAlgoTmpList.get(i);
			BeanUtils.copyProperties(tblHisDiscAlgoTmp,tblHisDiscAlgo);	
			t20706BO.add(tblHisDiscAlgo);
			}
		}
		//修改审核
		if(("3").equals(state)){
			List<TblHisDiscAlgoTmp> tblHisDiscAlgoTmpList=getDiscAlgoTmpByDiscId(discCd);
			//判断该计费算法是否已经被使用
			String countSql="select count(*) from tbl_his_disc_algo1 where substr(misc_1,7)='"+discCd+"'";
			String count=commQueryDAO.findCountBySQLQuery(countSql);
			//修改状态改到临时表
			List<TblInfDiscAlgo> list = new ArrayList<TblInfDiscAlgo>();
			tblInfDiscCdTmp.setSastate(CommonFunction.NORMAL);
			t20701BO.updateArith(list, tblInfDiscCdTmp, tblHisDiscAlgoTmpList);
			//修改数据到真实表
			TblInfDiscCd tblInfDiscCd=new TblInfDiscCd(); 
			TblHisDiscAlgo tblHisDiscAlgo=new TblHisDiscAlgo();
			BeanUtils.copyProperties(tblInfDiscCdTmp,tblInfDiscCd);	
			t20706BO.update(tblInfDiscCd);
			if(!"0".equals(count)){//如果计费算法已被使用则更新相关费率
				//查询修改前的计费详细梯级
				List<TblHisDiscAlgoTmp> tblHisDiscAlgoList=getDiscAlgoByDiscId(discCd);
				if(tblHisDiscAlgoList.size()==tblHisDiscAlgoTmpList.size()){//如果修改前后梯次数量不变则 直接更新
					for(TblHisDiscAlgoTmp tmp:tblHisDiscAlgoTmpList){
						 String updateSql="update tbl_his_disc_algo1 set MIN_FEE="+tmp.getMinFee().doubleValue()
						 +",MAX_FEE="+tmp.getMaxFee()+",floor_amount="+tmp.getFloorMount().doubleValue()+",fee_value="+tmp.getFeeValue().doubleValue()
						 +",flag='"+tmp.getFlag()+"' where INDEX_NUM="+tmp.getId().getIndexNum()+" and substr(misc_1,7)='"+tmp.getId().getDiscId()+"'";
						 commQueryDAO.excute(updateSql);
						}	
				}else if(tblHisDiscAlgoList.size()>tblHisDiscAlgoTmpList.size()){//如果现有的梯次数量大于变更后的梯次数量则 更新变更后的梯次，并删除现有的多于梯次
					for(TblHisDiscAlgoTmp tmp:tblHisDiscAlgoTmpList){//更新变更后的梯次
						 String updateSql="update tbl_his_disc_algo1 set MIN_FEE="+tmp.getMinFee().doubleValue()
						 +",MAX_FEE="+tmp.getMaxFee()+",floor_amount="+tmp.getFloorMount().doubleValue()+",fee_value="+tmp.getFeeValue().doubleValue()
						 +",flag='"+tmp.getFlag()+"' where INDEX_NUM="+tmp.getId().getIndexNum()+" and substr(misc_1,7)='"+tmp.getId().getDiscId()+"'";
						 commQueryDAO.excute(updateSql);
						}	
					 String delete="delete from tbl_his_disc_algo1 where substr(misc_1,7)='"+tblHisDiscAlgoTmpList.get(tblHisDiscAlgoTmpList.size()-1).getId().getDiscId()
					 +"' and INDEX_NUM>"+tblHisDiscAlgoTmpList.get(tblHisDiscAlgoTmpList.size()-1).getId().getIndexNum();
					 commQueryDAO.excute(delete);//删除多于的梯次
					 delete="delete from tbl_his_disc_algo where disc_id='"+discCd+"' and INDEX_NUM>"+tblHisDiscAlgoTmpList.get(tblHisDiscAlgoTmpList.size()-1).getId().getIndexNum();
					 commQueryDAO.excute(delete);
				}else{//更新现有的梯次，插入新增的梯次
					int i=0;
					for(;i<tblHisDiscAlgoList.size();i++){//更新变更后的梯次
						TblHisDiscAlgoTmp tmp=tblHisDiscAlgoTmpList.get(i);
						 String updateSql="update tbl_his_disc_algo1 set MIN_FEE="+tmp.getMinFee().doubleValue()
						 +",MAX_FEE="+tmp.getMaxFee()+",floor_amount="+tmp.getFloorMount().doubleValue()+",fee_value="+tmp.getFeeValue().doubleValue()
						 +",flag='"+tmp.getFlag()+"' where INDEX_NUM="+tmp.getId().getIndexNum()+" and substr(misc_1,7)='"+tmp.getId().getDiscId()+"'";
						 commQueryDAO.excute(updateSql);
						}
					String discAlgo1Sql="select MCHT_NO,TERM_ID,CITY_CODE,TO_BRCH_NO,FK_BRCH_NO,CARD_TYPE,CHANNEL_NO,BUSINESS_TYPE,TXN_NUM,misc_1, Max(INDEX_NUM) " +
							"from TBL_HIS_DISC_ALGO1 where substr(misc_1,7)='"+discCd+"' Group By  MCHT_NO,TERM_ID,CITY_CODE,TO_BRCH_NO,FK_BRCH_NO,CARD_TYPE,CHANNEL_NO,BUSINESS_TYPE,TXN_NUM,misc_1";
					List<Object[]> discAlgo1List = commQueryDAO.findBySQLQuery(discAlgo1Sql);
					String disc_id=GenerateNextId.getALGO1Id();
					boolean isNext=false;
					for(;i<tblHisDiscAlgoTmpList.size();i++){//插入新增的梯次
						TblHisDiscAlgoTmp tmp=tblHisDiscAlgoTmpList.get(i);
						for(Object[] obj:discAlgo1List){
							TblHisDiscAlgo1 algo1=new TblHisDiscAlgo1();
							algo1.setDISC_ID(getNextId(disc_id,isNext));
							algo1.setINDEX_NUM(tmp.getId().getIndexNum());
							algo1.setMCHT_NO((String)obj[0]);
							algo1.setTERM_ID((String)obj[1]);
							algo1.setCITY_CODE((String)obj[2]);
							algo1.setTO_BRCH_NO((String)obj[3]);
							algo1.setFK_BRCH_NO((String)obj[4]);
							algo1.setCARD_TYPE((String)obj[5]);
							algo1.setCHANNEL_NO((String)obj[6]);
							algo1.setBUSINESS_TYPE((String)obj[7]);
							algo1.setTXN_NUM((String)obj[8]);
							algo1.setFLAG(tmp.getFlag().toString());
							algo1.setMIN_FEE(tmp.getMinFee().doubleValue());
							algo1.setMAX_FEE(tmp.getMaxFee().doubleValue());
							algo1.setFEE_VALUE(tmp.getFeeValue().doubleValue());
							algo1.setFLOOR_AMOUNT(tmp.getFloorMount().doubleValue());
							algo1.setSA_SATUTE("2");//正常状态
							algo1.setREC_CRT_TS(CommonFunction.getCurrentDateTime());
							algo1.setREC_UPD_TS(CommonFunction.getCurrentDateTime());
							algo1.setMisc_1((String)obj[9]);
							t20706BO.saveOrUpdate(algo1);
							isNext=true;
							disc_id=algo1.getDISC_ID();
						}
						
					}
				}
				
			}
			
			for(int i=0;i<tblHisDiscAlgoTmpList.size();i++){
				TblHisDiscAlgoTmp tblHisDiscAlgoTmp=tblHisDiscAlgoTmpList.get(i);
				BeanUtils.copyProperties(tblHisDiscAlgoTmp,tblHisDiscAlgo);	
				t20706BO.saveOrUpdate(tblHisDiscAlgo);
				}
			
		}
		//删除审核
     if(CommonFunction.DELETE_TO_CHECK.equals(state)){
			List<TblHisDiscAlgoTmp> tblHisDiscAlgoTmpList=getDiscAlgoTmpByDiscId(discCd);
			//修改状态改到临时表
			List<TblInfDiscAlgo> list = new ArrayList<TblInfDiscAlgo>();
			tblInfDiscCdTmp.setSastate(CommonFunction.DELETE);
			t20701BO.updateArith(list, tblInfDiscCdTmp, tblHisDiscAlgoTmpList);
			//修改数据到真实表
			TblInfDiscCd tblInfDiscCd=new TblInfDiscCd(); 
			TblHisDiscAlgo tblHisDiscAlgo=new TblHisDiscAlgo();
			BeanUtils.copyProperties(tblInfDiscCdTmp,tblInfDiscCd);	
			t20706BO.update(tblInfDiscCd);
			for(int i=0;i<tblHisDiscAlgoTmpList.size();i++){
				TblHisDiscAlgoTmp tblHisDiscAlgoTmp=tblHisDiscAlgoTmpList.get(i);
				BeanUtils.copyProperties(tblHisDiscAlgoTmp,tblHisDiscAlgo);	
				t20706BO.update(tblHisDiscAlgo);
				}
		}
		return Constants.SUCCESS_CODE;
	}
	public String getNextId(String id,boolean isNext){
		if(isNext){
			int s=100000001;
			s+=Integer.valueOf(id);
			id=String.valueOf(s).substring(1);
		}
		return id;
	}
	private String refuse() throws Exception {
		// TODO Auto-generated method stub
		TblRiskRefuse tblRiskRefuse=new TblRiskRefuse();
		TblInfDiscCdTmp tblInfDiscCdTmp=t20701BO.getTblInfDiscCd(discCd);
		String state=tblInfDiscCdTmp.getSastate();
		tblInfDiscCdTmp.setRecUpdUserId(operator.getOprId());
		tblInfDiscCdTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		//新增拒绝，就直接删除
		if(("0").equals(state)){
			t20701BO.deleteArith(discCd);
		}
		//修改拒绝临时表变成正常状态
		if(("3").equals(state)){
			TblInfDiscCd tblInfDiscCd=t20706BO.get(discCd);
			List<TblHisDiscAlgoTmp> tblHisDiscAlgoTmpList=getDiscAlgoByDiscId(discCd);
			//恢复原来的数据
			List<TblInfDiscAlgo> list = new ArrayList<TblInfDiscAlgo>();
			BeanUtils.copyProperties(tblInfDiscCd,tblInfDiscCdTmp);	
			tblInfDiscCdTmp.setSastate(CommonFunction.NORMAL);
			t20701BO.updateArith(list, tblInfDiscCdTmp, tblHisDiscAlgoTmpList);	
		}
		//删除拒绝临时表变成正常状态
		if(CommonFunction.DELETE_TO_CHECK.equals(state)){
			TblInfDiscCd tblInfDiscCd=t20706BO.get(discCd);
			List<TblHisDiscAlgoTmp> tblHisDiscAlgoTmpList=getDiscAlgoByDiscId(discCd);
			//恢复原来的数据
			List<TblInfDiscAlgo> list = new ArrayList<TblInfDiscAlgo>();
			BeanUtils.copyProperties(tblInfDiscCd,tblInfDiscCdTmp);	
			tblInfDiscCdTmp.setSastate(CommonFunction.NORMAL);
			t20701BO.updateArith(list, tblInfDiscCdTmp, tblHisDiscAlgoTmpList);	
		}
		tblRiskRefuse.setRefuseType(state);
		tblRiskRefuse.setRefuseInfo(getRefuseInfo());
		tblRiskRefuse.setOprId(operator.getOprId());
		tblRiskRefuse.setBrhId(operator.getOprBrhId());
		tblRiskRefuse.setParam1(tblInfDiscCdTmp.getDiscCd());
		tblRiskRefuse.setParam2(tblInfDiscCdTmp.getDiscNm());
		tblRiskRefuse.setParam3(tblInfDiscCdTmp.getDiscOrg());
		tblRiskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		tblRiskRefuse.setFlag("93");
		t40206bo.saveRefuseInfo(tblRiskRefuse);
		return Constants.SUCCESS_CODE;
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
	
	/**
	 * 根据真实表计费代码获取计费算法信息
	 * @param discId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TblHisDiscAlgoTmp> getDiscAlgoByDiscId(String discId){
		String sql="select DISC_ID,min_fee,max_fee,floor_amount,flag,fee_value,INDEX_NUM  from tbl_his_disc_algo where DISC_ID='"+discId+"' order by INDEX_NUM";
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
}
