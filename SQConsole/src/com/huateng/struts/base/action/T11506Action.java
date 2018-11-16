package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.huateng.bo.base.T10110BO;
import com.huateng.bo.base.T11502BO;
import com.huateng.bo.base.T11503BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Constants;
import com.huateng.po.base.AgencyFeeLub;
import com.huateng.po.base.AgencyFeeLubTmp;
import com.huateng.po.base.TblAgentDivide;
import com.huateng.po.base.TblAgentDivideTmp;
import com.huateng.po.risk.TblRiskRefuse;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T11506Action extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private static final String ADD_TO_CHECK = "0";//新增未审核
	private static final String DELETE = "1";  //删除
	private static final String NORMAL = "2";//正常
	private static final String MODIFY_TO_CHECK = "3";//修改未审核
	private static final String DELETE_TO_CHECK = "4";//删除未审核
	//private T10110BO t10110BO = (T10110BO) ContextUtil.getBean("T10110BO");
	private T11502BO t11502BO = (T11502BO) ContextUtil.getBean("T11502BO");//正式表
	private T11503BO t11503BO = (T11503BO) ContextUtil.getBean("T11503BO");//临时表
	
	T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	private String refuseInfo;//拒绝原因
	private String uuidList; //批量审核
	
	private Map<String,String> map = new HashMap<String,String>();
	

	
	public String getUuidList() {
		return uuidList;
	}

	public void setUuidList(String uuidList) {
		this.uuidList = uuidList;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

	@Override
	protected String subExecute() throws Exception {
				
		/*
		 * 批量审核
		 * by shiyiwen 20141020
		 */
		
		jsonBean.parseJSONArrayData(getUuidList());
		int len = jsonBean.getArray().size();
		//List<AgencyFeeLubTmp> list = new ArrayList<AgencyFeeLubTmp>();
		List<TblAgentDivideTmp> list = new ArrayList<TblAgentDivideTmp>();
		
	
		//将同一代理商中的第一条记录的agentNo和uuid保存进map
		for(int j = 0;j<len;j++){
			String uuid =  jsonBean.getJSONDataAt(j).getString("uuid");	
			TblAgentDivideTmp tblAgentDivideTmp = t11503BO.query(uuid);
			String agentNo = tblAgentDivideTmp.getAgentNo().trim();
			
		/*	if(map.get(agentNo)==null){
				map.put(agentNo, uuid);
			}*/
			if(!map.containsKey(agentNo))
				map.put(agentNo, uuid);
			
		}
/*		Set keys = map.keySet();
		Iterator it = keys.iterator();
		while(it.hasNext()){
			Object key = it.next();
			System.out.println(key+":"+map.get(key));
		}*/
		
		
		
		for(int i = 0;i<len;i++){
			String uuid = jsonBean.getJSONDataAt(i).getString("uuid");			
		//	AgencyFeeLubTmp agencyFeeLub = t10110BO.getTmp(this.getId());
		//	AgencyFeeLubTmp agencyFeeLub = t10110BO.getTmp(feeId);
			TblAgentDivideTmp tblAgentDivideTmp = t11503BO.query(uuid);
			
			
			
			String checkID = tblAgentDivideTmp.getCrtPer();
			if(operator.getOprId().equals(checkID)){
				return "代理商编号"+tblAgentDivideTmp.getAgentNo()+"：同一操作员不能审核！";
			}
					
			
			try {
				if("accept".equals(getMethod())) {
					
					//判断下限值上限值区间是否相同有重叠
					//	double minValueNew = tblAgentDivideTmp.getMinValue();
					//	double maxValueNew = tblAgentDivideTmp.getMaxValue();
					    int len2 = jsonBean.getArray().size();
					    String agentNo = tblAgentDivideTmp.getAgentNo().trim();
					    List<TblAgentDivideTmp> listNew = new ArrayList<TblAgentDivideTmp>();
					    TblAgentDivideTmp t = null;
					    for(int m=0;m<len2;m++){
					       String uuid2 = jsonBean.getJSONDataAt(m).getString("uuid");
					       String agentNo2 = t11503BO.query(uuid2).getAgentNo().trim();
			               if(agentNo.equals(agentNo2)){
			            	   t = t11503BO.query(uuid2);
			            	   listNew.add(t);
			               }
					    	
					    }
											    
					
					//	String sql2 = "select min_value,max_value from tbl_agent_divide_tmp where agent_no = '"+tblAgentDivideTmp.getAgentNo().trim()+"' and state = '0'";
					//	List<Object[]> list2 = CommonFunction.getCommQueryDAO().findBySQLQuery(sql2);
						if(listNew.size()!=0 ){
					
							//将同一代理商之间的上下限值相互比较
							for(int k=0;k<listNew.size();k++){
								double minValueNew = Double.valueOf(listNew.get(k).getMinValue());
								double maxValueNew = Double.valueOf(listNew.get(k).getMaxValue());
								BigDecimal minNew = new BigDecimal(minValueNew);
								BigDecimal maxNew = new BigDecimal(maxValueNew);
						//		System.out.println(minNew+"--------"+maxNew);
								for(int j = 0;j<listNew.size()-k-1;j++){
									double minValueOld = Double.valueOf(listNew.get(k+j+1).getMinValue());
									double maxValueOld = Double.valueOf(listNew.get(k+j+1).getMaxValue());
									BigDecimal minOld = new BigDecimal(minValueOld);
									BigDecimal maxOld = new BigDecimal(maxValueOld);
					
									if( (minNew.compareTo(minOld)>0 && minNew.compareTo(maxOld)<0 )	||
								    	    (maxNew.compareTo(minOld)>0 && maxNew.compareTo(maxOld)<0)  || 
								    	    (minOld.compareTo(minNew)>0 && minOld.compareTo(maxNew)<0 ) ||
								    	    (maxOld.compareTo(minNew)>0 && maxOld.compareTo(maxNew)<0) ){
								    		return "代理商"+tblAgentDivideTmp.getAgentNo()+"存在下限值上限值区间冲突";
								    	}
									
								}				
								
							}
							
						}
					
					rspCode = accept(uuid);			
				} else if("refuse".equals(getMethod())) {
					rspCode = refuse(uuid);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log("操作员编号：" + operator.getOprId()+ "，对代理商为"+tblAgentDivideTmp.getAgentNo()+"的审核操作" + getMethod() + "失败，失败原因为："+e.getMessage());
			}
		
		}
		return rspCode;
	}
	
	private String accept(String id) {//新增机构分润的审核
		//获得表的这条数据
	
		TblAgentDivide tblAgentDivide = t11502BO.query(id);
		if(tblAgentDivide == null)
			tblAgentDivide = new TblAgentDivide();
		TblAgentDivideTmp tblAgentDivideTmp = t11503BO.query(id);
		String state = tblAgentDivideTmp.getState();
		
		
	    
		if((Constants.ADD_TO_CHECK).equals(state)){//通过新增待审核
		    String agentNo = tblAgentDivideTmp.getAgentNo().trim();
		    String agentNoSql = "select count(*) from tbl_agent_divide where AGENT_NO ='"+agentNo+"'";
			String agentNoCount = CommonFunction.getCommQueryDAO().findCountBySQLQuery(agentNoSql);
			if(!"0".equals(agentNoCount)){   //如果正式表里已存在该代理商，就先删除正式表里的该代理商，再删除临时表里的该代理商
		//		System.out.println(id);
		//		System.out.println(map.get(agentNo));
				if(id.equals(map.get(agentNo))){
					String delSql = "delete from tbl_agent_divide where AGENT_NO ='"+agentNo+"' and state!='1' ";
					CommonFunction.getCommQueryDAO().excute(delSql);
					
					String delSql2= "delete from tbl_agent_divide_tmp where AGENT_NO ='"+agentNo+"' and state='2' ";
					CommonFunction.getCommQueryDAO().excute(delSql2);
				}
			}
		    
			
			tblAgentDivideTmp.setState(NORMAL);
		}else if((MODIFY_TO_CHECK).equals(state)){//通过修改待审核
			
			tblAgentDivideTmp.setState(NORMAL);
		}else if((DELETE_TO_CHECK).equals(state)){//通过删除待审核
			tblAgentDivideTmp.setState(DELETE);
		}
		//20120806，更新审核人和审核时间字段
		//agencyFeeLub.setUP_OPR_ID(operator.getOprId());
		//agencyFeeLub.setUP_OPR_DATE(CommonFunction.getCurrentDateTime());
		tblAgentDivideTmp.setUpPer(operator.getOprId());
		tblAgentDivideTmp.setUpDate(CommonFunction.getCurrentDateTime());
		
		//修改状态改dao表
		List<TblAgentDivideTmp> tblAgentDivideTmpList=new ArrayList<TblAgentDivideTmp>();
		List<TblAgentDivide> tblAgentDivideList=new ArrayList<TblAgentDivide>();
	
		try {
			BeanUtils.copyProperties(tblAgentDivide,tblAgentDivideTmp);
			tblAgentDivideTmpList.add(tblAgentDivideTmp);
			t11503BO.saveOrUpdate(tblAgentDivideTmpList);
			tblAgentDivideList.add(tblAgentDivide);
			t11502BO.saveOrUpdate(tblAgentDivideList);
			return Constants.SUCCESS_CODE;
		}  catch (Exception e) {
			e.printStackTrace();
			return "审核失败！";
		}
	}
	
	private String refuse(String id) throws IllegalAccessException, InvocationTargetException {//审核拒绝，只需处理临时表，不需改动正式表
	//	AgencyFeeLubTmp agencyFeeLub = t10110BO.getTmp(id);
	//	AgencyFeeLub  feeLub = t10110BO.get(id);
		TblAgentDivideTmp tblAgentDivideTmp = t11503BO.query(id);
		TblAgentDivide tblAgentDivide = t11502BO.query(id);
		String state = tblAgentDivideTmp.getState();
		
		String minSql = "select min_value/10000 from tbl_agent_Divide_tmp where uuid ='"+tblAgentDivideTmp.getUuid()+"'";
		String  min_Value = CommonFunction.getCommQueryDAO().findBySQLQuery(minSql).get(0).toString();
		
		String maxSql = "select max_value/10000 from tbl_agent_Divide_tmp where uuid ='"+tblAgentDivideTmp.getUuid()+"'";
		String  max_Value = CommonFunction.getCommQueryDAO().findBySQLQuery(maxSql).get(0).toString();
		
		
		//保存拒绝原因
		TblRiskRefuse riskRefuse = new TblRiskRefuse();
		
		//新增拒绝,直接删除数据
		if((ADD_TO_CHECK).equals(state)){
			//tblAgentDivideTmp.setState(DELETE);
			t11503BO.delete(id);
			riskRefuse.setRefuseType("0");   //新增审核拒绝
		}
		//修改拒绝临时表不变，真实表也不变
		if((MODIFY_TO_CHECK).equals(state)){
			BeanUtils.copyProperties(tblAgentDivideTmp,tblAgentDivide);
			riskRefuse.setRefuseType("3");   //修改审核拒绝
		//	agencyFeeLub.setSTATUE(Constants.NORMAL);
			tblAgentDivideTmp.setUpPer(operator.getOprId());
			tblAgentDivideTmp.setUpDate(CommonFunction.getCurrentDateTime());
			
			List<TblAgentDivideTmp> tblAgentDividetTmpList = new ArrayList<TblAgentDivideTmp>();
			tblAgentDividetTmpList.add(tblAgentDivideTmp);
			t11503BO.update(tblAgentDividetTmpList);
		}
		//删除拒绝
		if((DELETE_TO_CHECK).equals(state)){
			BeanUtils.copyProperties(tblAgentDivideTmp,tblAgentDivide);
	//		agencyFeeLub.setSTATUE(Constants.NORMAL);
			riskRefuse.setRefuseType("4");   //删除审核拒绝
			tblAgentDivideTmp.setUpPer(operator.getOprId());
			tblAgentDivideTmp.setUpDate(CommonFunction.getCurrentDateTime());
			
			List<TblAgentDivideTmp> tblAgentDividetTmpList = new ArrayList<TblAgentDivideTmp>();
			tblAgentDividetTmpList.add(tblAgentDivideTmp);
			t11503BO.update(tblAgentDividetTmpList);
		}
		
		//修改状态改到临时表
	//	List<AgencyFeeLubTmp> agencyFeeInfoList = new ArrayList<AgencyFeeLubTmp>();
	//	agencyFeeInfoList.add(agencyFeeLub);
	//	t10110BO.updateTmp(agencyFeeInfoList);
		
		//20120807添加保存审核拒绝原因
		riskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());  //拒绝时间
		riskRefuse.setParam1(tblAgentDivideTmp.getAgentNo());//代理商编号
		riskRefuse.setParam2(tblAgentDivideTmp.getDivideType());//分润方式
		
		
		
		riskRefuse.setParam3(min_Value);//下限值
	//	System.out.println(new BigDecimal(tblAgentDivideTmp.getMinValue()/1000).toString());
		riskRefuse.setParam4(max_Value);//上限值
		riskRefuse.setParam5(tblAgentDivideTmp.getProfit());//分润参数
		riskRefuse.setOprId(operator.getOprId());   //操作员 
		riskRefuse.setRefuseInfo(refuseInfo);    //拒绝原因
		riskRefuse.setFlag("17");		
		try {
			t40206bo.saveRefuseInfo(riskRefuse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.SUCCESS_CODE;
	}
}
