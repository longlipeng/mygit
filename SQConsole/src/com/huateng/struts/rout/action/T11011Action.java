package com.huateng.struts.rout.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.rout.T11011BO;
import com.huateng.bo.rout.T11012BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.rout.TblRouteInfo;
import com.huateng.po.rout.TblRouteInfoTemp;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T11011Action extends BaseAction {
	private T11011BO t11011BO = (T11011BO)ContextUtil.getBean("T11011BO");
	private T11012BO t11012BO = (T11012BO)ContextUtil.getBean("T11012BO");
	public static String ADD_TO_CHECK = "0";
	public static String DELETE = "1";
	public static String NORMAL = "2";
	public static String MODIFY_TO_CHECK = "3";
	public static String DELETE_TO_CHECK = "4";
	
	
	private String cardBin;//卡BIN——改为发卡机构 20130715
	private String bussType;//业务类型
	private String txnNum;//交易类型
	private String channel;//渠道
	private String areaNo;//地区
	private String mchntId;//受理商户号
	private String reserved;//保留域primary key
	private String destInstId;//目的机构
	private String parameterList;
	private String cardType;	//卡类型
	private String minAmt;  //最低金额
	private String maxAmt;  //最高金额
	private String mchtMcc;  //mcc码

	public String getCardBin() {
		return cardBin.split("-")[0];
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getBussType() {
		return bussType.split("-")[0];
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getTxnNum() {
		return txnNum.split("-")[0];
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getChannel() {
		return channel.split("-")[0];
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAreaNo() {
		return areaNo.split("-")[0];
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getMchntId() {
		return mchntId.split("-")[0];
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getDestInstId() {
		return destInstId.split("-")[0];
	}

	public void setDestInstId(String destInstId) {
		this.destInstId = destInstId;
	}

	public String getParameterList() {
		return parameterList;
	}

	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public T11011BO getT11011BO() {
		return t11011BO;
	}
	
	public void setT11011BO(T11011BO t11011BO) {
		this.t11011BO = t11011BO;
	}
	
	
	public T11012BO getT11012BO() {
		return t11012BO;
	}

	public void setT11012BO(T11012BO t11012bo) {
		t11012BO = t11012bo;
	}

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

	public String getMchtMcc() {
		return mchtMcc;
	}

	public void setMchtMcc(String mchtMcc) {
		this.mchtMcc = mchtMcc;
	}

	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) {
				rspCode = add();			
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "，维护路由操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	public String add() throws Exception{//添加新的路由信息
		
		long minAmtL = (long) (Double.valueOf(minAmt)*100);
		long maxAmtL = (long) (Double.valueOf(maxAmt)*100);
		
		//若 卡bin、业务类型、交易代码、渠道、地区码、受理商户号、卡类型已存在并且状态不是已删除，则提示已存在
		String sql = "select count(*) from TBL_TXN_ROUTE_INF_TEMP where sa_state<>'1' and card_bin='" + this.getCardBin() 
			+ "' and buss_type='" + this.getBussType() + "' and txn_num='" + this.getTxnNum() 
			+ "' and channel='" + this.channel + "' and area_no='" + this.getAreaNo() + "' and merch_id='"
			+ this.getMchntId()+"' and trim(card_type)='"+ this.getCardType().trim()+"' and MCHT_MCC ='"+this.getMchtMcc()+"'"
		//	+ " and dest_inst_id='" + this.getDestInstId()
			+" and min_amt = '"+minAmtL+"' and max_amt='"+maxAmtL+"'"; 
		String count = commQueryDAO.findCountBySQLQuery(sql);
	//	System.out.println(this.getDestInstId());
		if(!"0".equals(count)) {
			return "您所添加的路由系统中已经存在";
		}
		
		//判断tmp区间是否重叠
		String sql2 = "select min_amt,max_amt from TBL_TXN_ROUTE_INF_TEMP where sa_state<>'1' and card_bin='" + this.getCardBin() 
				+ "' and buss_type='" + this.getBussType() + "' and txn_num='" + this.getTxnNum() 
				+ "' and channel='" + this.channel + "' and area_no='" + this.getAreaNo() + "' and merch_id='"
				+ this.getMchntId()+"' and trim(card_type)='"+ this.getCardType().trim()+"' and MCHT_MCC ='"+this.getMchtMcc()+"' ";
			//	+ " and dest_inst_id='" + this.getDestInstId()+"'"; 
		List<Object[]> list =  CommonFunction.getCommQueryDAO().findBySQLQuery(sql2);
		if(list.size()!=0){
			for(int i=0;i<list.size();i++){
				long min_AmtOld = Long.parseLong(list.get(i)[0].toString());
				long max_AmtOld = Long.parseLong(list.get(i)[1].toString());
				if(  (minAmtL>=min_AmtOld && minAmtL<=max_AmtOld )||
						(maxAmtL>=min_AmtOld && maxAmtL<=max_AmtOld ) || 
						(min_AmtOld>=minAmtL && min_AmtOld <= maxAmtL ) ||
						(max_AmtOld >= minAmtL && max_AmtOld <= maxAmtL ) ){
					return "添加的区间存在上下限重叠";
				}
			}
		}
		
		
	
//		String[] ArrayMinAmt = minAmt.split("\\.");
//		String[] ArrayMaxAmt = maxAmt.split("\\.");
//		long minAmtL = Long.parseLong(ArrayMinAmt[0]+ArrayMinAmt[1]);
//	    long maxAmtL = Long.parseLong(ArrayMaxAmt[0]+ArrayMaxAmt[1]);
		
		
		
		
 		TblRouteInfoTemp tblRouteInfoTemp = new TblRouteInfoTemp();
 		tblRouteInfoTemp.setReserved(StringUtil.getUUID());
 		//FIXME
		System.out.println(tblRouteInfoTemp.getReserved());
 		tblRouteInfoTemp.setCardBin(this.getCardBin());
 		tblRouteInfoTemp.setBussType(this.getBussType());
 		tblRouteInfoTemp.setTxnNum(this.getTxnNum());
 		tblRouteInfoTemp.setChannel(this.getChannel());
 		tblRouteInfoTemp.setAreaNo(this.getAreaNo());
 		tblRouteInfoTemp.setMchntId(this.getMchntId());
 		tblRouteInfoTemp.setCardType(this.getCardType());
		tblRouteInfoTemp.setCreatorId(operator.getOprId());//存储的为记录修改人
		tblRouteInfoTemp.setUpdateTime(CommonFunction.getCurrentDateTime());
		tblRouteInfoTemp.setSaState(ADD_TO_CHECK);
		tblRouteInfoTemp.setDestInstId(getDestInstId());
		tblRouteInfoTemp.setCreTime(CommonFunction.getCurrentDateTime());
		tblRouteInfoTemp.setCreOprId(operator.getOprId());//创建人
		tblRouteInfoTemp.setMaxAmt(maxAmtL);
		tblRouteInfoTemp.setMinAmt(minAmtL);
		tblRouteInfoTemp.setMchtMcc(mchtMcc);
		
		t11011BO.add(tblRouteInfoTemp);
		return Constants.SUCCESS_CODE;
	}
	
	/**删除
	 * delete city code
	 * @return
	 */
	public String delete() {
		TblRouteInfoTemp tblRouteInfoTemp = t11011BO.get(this.getReserved());
		if(tblRouteInfoTemp == null) {
			return "没有找到要删除的路由信息";
		}
//		TblRouteInfoTemp tblRouteInfoTemp = t11011BO.get(id);
		tblRouteInfoTemp.setCheckTime(CommonFunction.getCurrentDateTime());
		tblRouteInfoTemp.setCheckId(operator.getOprId());
		tblRouteInfoTemp.setSaState(DELETE);
		t11011BO.update(tblRouteInfoTemp);
		
		TblRouteInfo tblRouteInfo = new TblRouteInfo();
		tblRouteInfo = (TblRouteInfo) tblRouteInfoTemp.clone(tblRouteInfo);
		rspCode = t11012BO.update(tblRouteInfo);
		log("审核通过删除待审核的路由信息");
		/*tblRouteInfoTemp.setUpdateTime(CommonFunction.getCurrentDateTime());
		tblRouteInfoTemp.setCreatorId(operator.getOprId());
		tblRouteInfoTemp.setSaState(DELETE_TO_CHECK);
		t11011BO.update(tblRouteInfoTemp);*/
		return Constants.SUCCESS_CODE;
	}
	
	/** 修改
	 * 
	 * @return
	 */
	public String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {	
		jsonBean.parseJSONArrayData(getParameterList());
		   int len = jsonBean.getArray().size();
		   List<TblRouteInfoTemp> tblRouteInfoTempList = new ArrayList<TblRouteInfoTemp>(len);	
			for(int i = 0; i < len; i++) {					   
			    jsonBean.setObject(jsonBean.getJSONDataAt(i));
			    String reserved = jsonBean.getJSONDataAt(i).getString("reserved");
			    //最大金额 和 最小金额要拎出来 *100再存进数据库
			    String minAmt = jsonBean.getJSONDataAt(i).getString("minAmt");
			    String maxAmt = jsonBean.getJSONDataAt(i).getString("maxAmt");
			    long minAmtL = (long) (Double.valueOf(minAmt)*100);
			    long maxAmtL = (long) (Double.valueOf(maxAmt)*100);
			    
			    
			    
			    
			    
			    TblRouteInfoTemp tblRouteInfoTemp = t11011BO.get(reserved);
				BeanUtils.setObjectWithPropertiesValue(tblRouteInfoTemp, jsonBean, false);
				tblRouteInfoTemp.setCreatorId(operator.getOprId());
				tblRouteInfoTemp.setUpdateTime(CommonFunction.getCurrentDateTime());
				tblRouteInfoTemp.setMinAmt(minAmtL);
				tblRouteInfoTemp.setMaxAmt(maxAmtL);
				if(tblRouteInfoTemp.getSaState().equals(NORMAL))
					tblRouteInfoTemp.setSaState(MODIFY_TO_CHECK);
				//20121130添加唯一性验证
//				String sql = "select count(*) from TBL_TXN_ROUTE_INF_TEMP where sa_state<>'1' and card_bin='" + tblRouteInfoTemp.getCardBin() 
//					+ "' and buss_type='" + tblRouteInfoTemp.getBussType() + "' and txn_num='" + tblRouteInfoTemp.getTxnNum() 
//					+ "' and channel='" + tblRouteInfoTemp.getChannel() + "' and area_no='" + tblRouteInfoTemp.getAreaNo() + "' and merch_id='"
//					+ tblRouteInfoTemp.getMchntId() + "' and dest_inst_id='" + tblRouteInfoTemp.getDestInstId() + "'";
			
				//modify by zxc 受理商户号，界面获取值，只获取商户ID
				String sql = "select count(*) from TBL_TXN_ROUTE_INF_TEMP where sa_state<>'1' and card_bin='" + tblRouteInfoTemp.getCardBin() 
				+ "' and buss_type='" + tblRouteInfoTemp.getBussType() + "' and txn_num='" + tblRouteInfoTemp.getTxnNum() 
				+ "' and channel='" + tblRouteInfoTemp.getChannel() + "' and area_no='" + tblRouteInfoTemp.getAreaNo() + "' and merch_id='"
				+ tblRouteInfoTemp.getMchntId().split("-")[0] + "' and trim(card_type)='" +tblRouteInfoTemp.getCardType().trim() 
				+ "' and RESERVED !='"+reserved+"' and MCHT_MCC='"+tblRouteInfoTemp.getMchtMcc()+"'"
			//	+ " and dest_inst_id='" + tblRouteInfoTemp.getDestInstId() 
				+ " and min_amt = '"+minAmtL+"' and max_amt = '"+maxAmtL+"'";
		
				//更改tblRouteInfoTemp对象里面getMchntId
				tblRouteInfoTemp.setMchntId(tblRouteInfoTemp.getMchntId().split("-")[0]);
				
				
				String count = commQueryDAO.findCountBySQLQuery(sql);
				if(!"0".equals(count)) {
					return "您所修改的路由信息系统中已经存在";
				}
				//判断集合中是否已包含相同内容的记录20121129
				if(tblRouteInfoTempList!=null && tblRouteInfoTempList.size()>0){
					for(int j=0 ; j<tblRouteInfoTempList.size() ; j++){
						TblRouteInfoTemp temp = tblRouteInfoTempList.get(j);
						if(tblRouteInfoTemp.getCardBin().equals(temp.getCardBin()) && tblRouteInfoTemp.getBussType().equals(temp.getBussType()) 
								&& tblRouteInfoTemp.getTxnNum().equals(temp.getTxnNum()) && tblRouteInfoTemp.getChannel().equals(temp.getChannel()) 
								&& tblRouteInfoTemp.getAreaNo().equals(temp.getAreaNo()) && tblRouteInfoTemp.getMchntId().equals(temp.getMchntId()) 
								&& tblRouteInfoTemp.getCardType().trim().equals(temp.getCardType().trim())
								&&  tblRouteInfoTemp.getMchtMcc().trim().equals(temp.getMchtMcc().trim())
						//		&& tblRouteInfoTemp.getDestInstId().trim().equals(temp.getDestInstId().trim())
								&& tblRouteInfoTemp.getMinAmt() == temp.getMinAmt()
								&& tblRouteInfoTemp.getMaxAmt() == temp.getMaxAmt()
								/*&& destInstId.equals(temp.getDestInstId())*/){//只要文件中有两条数据重复，即提示失败信息
							return "您所修改的路由信息有重复";
						}
					}
				}
				
				//判断区间重叠
				StringBuffer sqlBuffer=new StringBuffer();
				sqlBuffer.append("select count(*) from tbl_txn_route_inf_temp where sa_state<>'1' and card_bin='")
				.append(tblRouteInfoTemp.getCardBin()).append("' ")
				.append(" and buss_type='").append( tblRouteInfoTemp.getBussType()).append("' ")
				.append(" and txn_num='").append(tblRouteInfoTemp.getTxnNum()).append("' ")
				
				.append(" and area_no='").append(tblRouteInfoTemp.getAreaNo()).append("' ")
				.append(" and merch_id='").append(tblRouteInfoTemp.getMchntId().split("-")[0]).append("' ")
				.append(" and trim(card_type)='").append(tblRouteInfoTemp.getCardType().trim()).append("' ")
				.append(" and MCHT_MCC='").append(tblRouteInfoTemp.getMchtMcc()).append("' ")
			//	.append(" and dest_inst_id='").append(tblRouteInfoTemp.getDestInstId()).append("' ")
				
				.append(" and channel='").append(tblRouteInfoTemp.getChannel() ).append("' ")
				.append(" and ((").append(tblRouteInfoTemp.getMinAmt()).append("<=min_amt and min_amt<=").append(tblRouteInfoTemp.getMaxAmt()).append(") or (")
				.append(tblRouteInfoTemp.getMinAmt()).append("<=max_amt and max_amt<=").append(tblRouteInfoTemp.getMaxAmt()).append(") or (min_amt<=")
				.append(tblRouteInfoTemp.getMinAmt()).append(" and ").append(tblRouteInfoTemp.getMaxAmt()).append("<=max_amt))")
				.append(" and RESERVED != '").append(reserved).append("'");
				
				String count2 = commQueryDAO.findCountBySQLQuery(sqlBuffer.toString());
				if(!"0".equals(count2)) {
					return "您所修改的路由信息交易金额区间有重叠！";
				}
				tblRouteInfoTempList.add(tblRouteInfoTemp);
			}
			t11011BO.update(tblRouteInfoTempList);
			return Constants.SUCCESS_CODE;
	}
	
}
