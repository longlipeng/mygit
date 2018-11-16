package com.huateng.struts.rout.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import sqlj.javac.ThisClassNode;

import com.huateng.bo.rout.T11015BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.dao.impl.rout.TblTermChannelInfDAO;
import com.huateng.po.rout.TblTermChannelInf;
import com.huateng.po.rout.TblTermChannelInfTmp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * 终端通道配置维护
 * @author crystal
 *
 */
public class T11015Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T11015BO t11015BO = (T11015BO)ContextUtil.getBean("T11015BO");
	
	public T11015BO getT11015BO() {
		return t11015BO;
	}

	public void setT11015BO(T11015BO t11015bo) {
		t11015BO = t11015bo;
	}

	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	private String id;
	private String termIns;//所属机构号
	private String mchtMcc;//商户MCC码
	private String mchtId;//商户号
	private String mchtTermId;//商户终端号
	private String insMcc;//机构MCC码
	private String insMcht;//机构商户号
	private String insTerm;//机构终端号
	private String reserve01;//索引值
	private String stat;//状态
	private String modiTime;//修改时间
	private String modiOprId;//修改人
	private String checkTime;//审核时间
	private String checkOprId;//审核人
	private String lmk;//终端主密钥
	private String parameterList;
	private String infList;
	private String channelType;
	private String minAmt;  //交易金额下限
	private String maxAmt;  //交易金额上限
	
	public String getMinAmt() {
		return minAmt;
	}
	
	public void setMinAmt(String minAmt) {
		this.minAmt = minAmt;
	}

	public String getMaxAmt() {
		return maxAmt;
	}
	
	public void setMaxAmt(String maxAmt) {
		this.maxAmt = maxAmt;
	}
	
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getReserve01() {
		return reserve01;
	}

	public void setReserve01(String reserve01) {
		this.reserve01 = reserve01;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTermIns() {
		return termIns;
	}

	public void setTermIns(String termIns) {
		this.termIns = termIns;
	}

	public String getMchtMcc() {
		return mchtMcc;
	}

	public void setMchtMcc(String mchtMcc) {
		this.mchtMcc = mchtMcc;
	}

	public String getMchtId() {
		return mchtId.split("-")[0];
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	public String getMchtTermId() {
		return mchtTermId;
	}

	public void setMchtTermId(String mchtTermId) {
		this.mchtTermId = mchtTermId;
	}

	public String getInsMcc() {
		return insMcc;
	}

	public void setInsMcc(String insMcc) {
		this.insMcc = insMcc;
	}

	public String getInsMcht() {
		return insMcht;
	}

	public void setInsMcht(String insMcht) {
		this.insMcht = insMcht;
	}

	public String getInsTerm() {
		return insTerm;
	}

	public void setInsTerm(String insTerm) {
		this.insTerm = insTerm;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getModiTime() {
		return modiTime;
	}

	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}

	public String getModiOprId() {
		return modiOprId;
	}

	public void setModiOprId(String modiOprId) {
		this.modiOprId = modiOprId;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckOprId() {
		return checkOprId;
	}

	public void setCheckOprId(String checkOprId) {
		this.checkOprId = checkOprId;
	}

	public String getLmk() {
		return lmk;
	}

	public void setLmk(String lmk) {
		this.lmk = lmk;
	}

	public String getParameterList() {
		return parameterList;
	}

	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}

	public String getInfList() {
		return infList;
	}


	public void setInfList(String infList) {
		this.infList = infList;
	}
	
	@Override
	protected String subExecute() throws Exception {
		try {
			if("add".equals(method)) {
				rspCode = add();			
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			}else if("deleteall".equals(method)) {
				rspCode = deleteall();
			} 
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "，维护终端通道配置操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 添加新的终端通道配置
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		String[] ArrayMinAmt = minAmt.split("\\.");
		String[] ArrayMaxAmt = maxAmt.split("\\.");
		long minAmtL = Long.parseLong(ArrayMinAmt[0]+ArrayMinAmt[1]);
	    long maxAmtL = Long.parseLong(ArrayMaxAmt[0]+ArrayMaxAmt[1]);
	    if(minAmtL>maxAmtL){
	    	return "交易金额下限大于上限，请重新输入！";
	    }
	    System.out.println(minAmtL+"........." +maxAmtL);
//		String sql = "select count(*) from TBL_TERM_CHANNEL_INF_TMP where STAT <> '1' and term_ins='"+this.getTermIns()+"' and mcht_mcc='"+this.getMchtMcc()
//			+"' and mcht_id='"+this.getMchtId()+"' and mcht_term_id='"+this.getMchtTermId()+"'";
			//+" and ins_mcc='"+this.getInsMcc() +"' and ins_mcht='"+this.getInsMcht() + "' and ins_term='"+this.getInsTerm() + "'";
		//20121120添加对主密钥唯一性的检验
		/*if(this.getLmk()!=null && !"".equals(this.getLmk().trim())){
			sql += " and lmk='" + this.getLmk().trim() + "'";
		}*/
	    StringBuffer sqlBuffer=new StringBuffer();
	    sqlBuffer.append("select count(*) from TBL_TERM_CHANNEL_INF_TMP where STAT <> '1' and term_ins='")
			.append(this.getTermIns()).append("' ")
			.append(" and mcht_mcc='").append(this.getMchtMcc()).append("' ")
			.append(" and mcht_id='").append(this.getMchtId()).append("' ")
			.append(" and mcht_term_id='").append(this.getMchtTermId()).append("' ")
			.append(" and INS_MCC='").append(this.getInsMcc()).append("' ")
			.append(" and INS_MCHT='").append(this.getInsMcht()).append("' ")
			.append(" and INS_TERM='").append(this.getInsTerm()).append("' ");
		String cnt = commQueryDAO.findCountBySQLQuery(sqlBuffer.toString());
		if(!"0".equals(cnt)) {
			return "您所添加的终端通道配置已经存在！";
		}else{
			sqlBuffer.setLength(0);
			sqlBuffer.append("select count(*) from TBL_TERM_CHANNEL_INF_TMP where STAT <> '1' and term_ins='")
				.append(this.getTermIns()).append("' ")
				.append(" and mcht_mcc='").append(this.getMchtMcc()).append("' ")
				.append(" and mcht_id='").append(this.getMchtId()).append("' ")
				.append(" and mcht_term_id='").append(this.getMchtTermId()).append("' ")
				.append(" and ((").append(minAmtL).append("<=min_amt and min_amt<=").append(maxAmtL).append(") or (")
				.append(minAmtL).append("<=max_amt and max_amt<=").append(maxAmtL).append(") or (min_amt<=")
				.append(minAmtL).append(" and ").append(maxAmtL).append("<=max_amt))");
			
			String count = commQueryDAO.findCountBySQLQuery(sqlBuffer.toString());
			if(!"0".equals(count)) {
				return "您所添加的终端通道配置交易金额区间重叠！";
			}
		}
		
		
 		TblTermChannelInfTmp tblTermChannelInfTmp = new TblTermChannelInfTmp();
 		tblTermChannelInfTmp.setId(StringUtil.getUUID());
 		tblTermChannelInfTmp.setTermIns(this.getTermIns());
 		tblTermChannelInfTmp.setMchtMcc(this.getMchtMcc());
 		tblTermChannelInfTmp.setMchtId(this.getMchtId());
 		tblTermChannelInfTmp.setMchtTermId(this.getMchtTermId());
 		tblTermChannelInfTmp.setInsMcc(this.getInsMcc());
 		tblTermChannelInfTmp.setInsMcht(this.getInsMcht());
 		tblTermChannelInfTmp.setInsTerm(this.getInsTerm());
 		tblTermChannelInfTmp.setChannelType(this.getChannelType());
 		System.out.println("getChannelType ="+this.getChannelType());
 		tblTermChannelInfTmp.setLmk(this.getLmk());//主密钥
 		tblTermChannelInfTmp.setReserve02(this.getLmk());
// 		tblTermChannelInfTmp.setReserve01(this.reserve01);//索引值
 		
 		//modify by zxc 20130510 转换成16进制存入数据库 start 
 		tblTermChannelInfTmp.setReserve01(DecToHex(this.reserve01));//索引值
 		//modify by zxc 20130510 转换成16进制存入数据库 end
 		
 		
 		tblTermChannelInfTmp.setModiOprId(operator.getOprId());
 		tblTermChannelInfTmp.setModiTime(CommonFunction.getCurrentDateTime());
		tblTermChannelInfTmp.setStat(ADD_TO_CHECK);
		/*tblTermChannelInfTmp.setTermInsOld(this.getTermIns());
 		tblTermChannelInfTmp.setMchtMccOld(this.getMchtMcc());
 		tblTermChannelInfTmp.setMchtIdOld(this.getMchtId());
 		tblTermChannelInfTmp.setMchtTermIdOld(this.getMchtTermId());
 		tblTermChannelInfTmp.setInsMccOld(this.getInsMcc());
 		tblTermChannelInfTmp.setInsMchtOld(this.getInsMcht());
 		tblTermChannelInfTmp.setInsTermOld(this.getInsTerm());*/
 		tblTermChannelInfTmp.setCreTime(CommonFunction.getCurrentDateTime());
 		tblTermChannelInfTmp.setCreOprId(operator.getOprId());
		tblTermChannelInfTmp.setMaxAmt(maxAmtL);
		tblTermChannelInfTmp.setMinAmt(minAmtL);
 		
		t11015BO.add(tblTermChannelInfTmp);
		return Constants.SUCCESS_CODE;
	}
	
	/**删除终端通道配置
	 * delete 
	 * @return
	 */
	public String delete() {
		TblTermChannelInfTmp tblTermChannelInfTmp = t11015BO.getTmp(this.getId());
		if(tblTermChannelInfTmp == null) {
			return "没有找到要删除的终端通道配置信息";
		}
		tblTermChannelInfTmp.setModiTime(CommonFunction.getCurrentDateTime());
		tblTermChannelInfTmp.setModiOprId(operator.getOprId());
		tblTermChannelInfTmp.setStat(DELETE_TO_CHECK);
		t11015BO.update(tblTermChannelInfTmp);
		return Constants.SUCCESS_CODE;
	}
	
	/** 批量删除终端通道配置
	 * 
	 * @return
	 */
	
	private String deleteall() {
		jsonBean.parseJSONArrayData(getInfList());
		int len = jsonBean.getArray().size();
		List<TblTermChannelInfTmp> TblTermChannelInflistTmp = new ArrayList<TblTermChannelInfTmp>(len);
		try {
			for (int i = 0; i < len; i++) {
				jsonBean.setObject(jsonBean.getJSONDataAt(i));
				TblTermChannelInfTmp TblTermChannelInfTmp = t11015BO.getTmp(jsonBean.getJSONDataAt(i).getString("id"));
				BeanUtils.setObjectWithPropertiesValue(TblTermChannelInfTmp, jsonBean,false);
				TblTermChannelInfTmp.setModiOprId(operator.getOprId());
				TblTermChannelInfTmp.setModiTime(CommonFunction.getCurrentDateTime());
				TblTermChannelInfTmp.setStat(DELETE_TO_CHECK);
				TblTermChannelInflistTmp.add(TblTermChannelInfTmp);

//				TblTermChannelInf tblTermChannelInf = t11015BO.get(this.getId());
//				if(tblTermChannelInf == null) {
//					return "没有找到要删除的终端通道配置信息";
//				}
//				tblTermChannelInf.setModiTime(CommonFunction.getCurrentDateTime());
//				tblTermChannelInf.setModiOprId(operator.getOprId());
//				tblTermChannelInf.setStat(DELETE_TO_CHECK);
				t11015BO.updateTmp(TblTermChannelInflistTmp);
			}
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}
	/** 修改终端通道配置
	 * 
	 * @return
	 */
	public String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		jsonBean.parseJSONArrayData(getParameterList());
		   int len = jsonBean.getArray().size();
		   List<TblTermChannelInfTmp> tblTermChannelInfListTmp = new ArrayList<TblTermChannelInfTmp>(len);	
			for(int i = 0; i < len; i++) {					   
			    jsonBean.setObject(jsonBean.getJSONDataAt(i));
			    String id = jsonBean.getJSONDataAt(i).getString("id");
			    
			    TblTermChannelInfTmp tblTermChannelInfTmp = t11015BO.getTmp(id);
				BeanUtils.setObjectWithPropertiesValue(tblTermChannelInfTmp, jsonBean, false);
				tblTermChannelInfTmp.setTermIns(tblTermChannelInfTmp.getTermIns().split("-")[0]);
				tblTermChannelInfTmp.setModiOprId(operator.getOprId());
				tblTermChannelInfTmp.setModiTime(CommonFunction.getCurrentDateTime());
				if(tblTermChannelInfTmp.getStat().equals(NORMAL)){
					tblTermChannelInfTmp.setStat(MODIFY_TO_CHECK);
				}
							
//				String sql = "select count(*) from TBL_TERM_CHANNEL_INF_TMP where STAT <> '1' and term_ins='" + tblTermChannelInfTmp.getTermIns()
//					+"' and mcht_mcc='" + tblTermChannelInfTmp.getMchtMcc() + "' and mcht_id='" + tblTermChannelInfTmp.getMchtId()
//					+"' and mcht_term_id='" + tblTermChannelInfTmp.getMchtTermId() + "' and id != '"+tblTermChannelInfTmp.getId()+"'";
/*				String wheresql = "select count(*) from TBL_TERM_CHANNEL_INF_TMP where STAT <> '1' and term_ins='" + tblTermChannelInfTmp.getTermIns()
				+"' and mcht_mcc='" + tblTermChannelInfTmp.getMchtMcc() + "' and mcht_id='" + tblTermChannelInfTmp.getMchtId()
				+ "' and id != '"+tblTermChannelInfTmp.getId()+"'";*/
					//+" and ins_mcc='" + tblTermChannelInf.getInsMcc() 
					//+"' and ins_mcht='" + tblTermChannelInf.getInsMcht() + "' and ins_term='" + tblTermChannelInf.getInsTerm() + "'";
				//20121120添加对主密钥唯一性的检验
				/*if(tblTermChannelInf.getLmk()!=null && !"".equals(tblTermChannelInf.getLmk().trim())){
					sql += " and lmk='" + tblTermChannelInf.getLmk().trim() + "'";
				}*/
				
				 StringBuffer sqlBuffer=new StringBuffer();
				 sqlBuffer.append("select count(*) from TBL_TERM_CHANNEL_INF_TMP where STAT <> '1' and term_ins='")
					.append(tblTermChannelInfTmp.getTermIns()).append("' ")
					.append(" and mcht_mcc='").append(tblTermChannelInfTmp.getMchtMcc()).append("' ")
					.append(" and mcht_id='").append(tblTermChannelInfTmp.getMchtId()).append("' ")
					.append(" and mcht_term_id='").append(tblTermChannelInfTmp.getMchtTermId()).append("' ")
					.append(" and INS_MCC='").append(tblTermChannelInfTmp.getInsMcc()).append("' ")
					.append(" and INS_MCHT='").append(tblTermChannelInfTmp.getInsMcht()).append("' ")
					.append(" and INS_TERM='").append(tblTermChannelInfTmp.getInsTerm()).append("' ")
					.append(" and id != '").append(tblTermChannelInfTmp.getId()).append("'");
				String cnt = commQueryDAO.findCountBySQLQuery(sqlBuffer.toString());
				if(!"0".equals(cnt)) {
					return "您所修改的终端通道配置已经存在！";
				}else{
					sqlBuffer.setLength(0);
					sqlBuffer.append("select count(*) from TBL_TERM_CHANNEL_INF_TMP where STAT <> '1' and term_ins='")
						.append(tblTermChannelInfTmp.getTermIns()).append("' ")
						.append(" and mcht_mcc='").append(tblTermChannelInfTmp.getMchtMcc()).append("' ")
						.append(" and mcht_id='").append(tblTermChannelInfTmp.getMchtId()).append("' ")
						.append(" and mcht_term_id='").append(tblTermChannelInfTmp.getMchtTermId()).append("' ")
						.append(" and ((").append(tblTermChannelInfTmp.getMinAmt()).append("<=min_amt and min_amt<=").append(tblTermChannelInfTmp.getMaxAmt()).append(") or (")
						.append(tblTermChannelInfTmp.getMinAmt()).append("<=max_amt and max_amt<=").append(tblTermChannelInfTmp.getMaxAmt()).append(") or (min_amt<=")
						.append(tblTermChannelInfTmp.getMinAmt()).append(" and ").append(tblTermChannelInfTmp.getMaxAmt()).append("<=max_amt))")
						.append(" and id != '").append(tblTermChannelInfTmp.getId()).append("'");
					String count = commQueryDAO.findCountBySQLQuery(sqlBuffer.toString());
					if(!"0".equals(count)) {
						return "您所修改的终端通道配置交易金额区间有重叠！";
					}
				}
				 
				/*if(!"0".equals(count1)) {
					return "您所修改的终端通道配置已经存在！";
				}*/
				//判断集合中是否已包含相同内容的记录20121129
				if(tblTermChannelInfListTmp!=null && tblTermChannelInfListTmp.size()>0){
					for(int j=0 ; j<tblTermChannelInfListTmp.size() ; j++){
						TblTermChannelInfTmp temp = tblTermChannelInfListTmp.get(j);
						if(tblTermChannelInfTmp.getTermIns().equals(temp.getTermIns()) && tblTermChannelInfTmp.getMchtMcc().equals(temp.getMchtMcc()) 
								&& tblTermChannelInfTmp.getMchtId().equals(temp.getMchtId()) && tblTermChannelInfTmp.getMchtTermId().equals(temp.getMchtTermId()) 
								&& tblTermChannelInfTmp.getInsMcc().equals(temp.getInsMcc()) && tblTermChannelInfTmp.getInsMcht().equals(temp.getInsMcht())
								&& tblTermChannelInfTmp.getInsTerm().equals(temp.getInsTerm())
								){//只要文件中有两条数据重复，即提示失败信息
							return "您所提交的终端通道配置有重复！";
						}
					}
				}
				tblTermChannelInfListTmp.add(tblTermChannelInfTmp);
			}
			t11015BO.updateTmp(tblTermChannelInfListTmp);
			return Constants.SUCCESS_CODE;
	}
	
	
	/**
	 * 10进制索引值转成16进制保存到DB
	 * @param s
	 * @return
	 * 2013-5-10 上午11:51:03
	 * @author zhao_xingcai
	 */
	public String DecToHex(String s) {
		Integer i1 = 0;
	    Integer i2 = 0;
	    String s1;
	    String s2;
	    
		if(s.length() == 1 ) {
			i1 = Integer.valueOf(s);
		}else if(s.length() == 2) {
			i1 = Integer.valueOf(s.substring(0, 2));
		}else if(s.length() == 3) {
			i1 = Integer.valueOf(s.substring(0, 1));
			i2 = Integer.valueOf(s.substring(1, 3));
		}else if(s.length() == 4) {
			i1 = Integer.valueOf(s.substring(0, 2));
			i2 = Integer.valueOf(s.substring(2, 4));
		}
		
		s1 = String.valueOf(Integer.toHexString(i1));
		s2 = String.valueOf(Integer.toHexString(i2));
		
		//补足位数
		if(s1.length() < 2) {
			s1 = "0" + s1;
		}
		if(s2.length() < 2) {
			s2 = "0" + s2;
		}
		return s1+s2;
	}
	
	/**
	 * 把16进制转成10进制
	 * @param s
	 * @return
	 * 2013-5-10 下午01:29:17
	 * @author zhao_xingcai
	 */
	public String HexToDec(String s) {
		
		Integer i1 = 0;
		Integer i2 = 0;
		
		if(s.length() == 4) {
			i1 = Integer.parseInt(s.substring(0, 2), 16);
			i2 = Integer.parseInt(s.substring(2, 4), 16);
		}
		return String.valueOf(i1) + String.valueOf(i2);
	}
	
	
}
