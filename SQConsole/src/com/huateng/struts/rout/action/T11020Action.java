package com.huateng.struts.rout.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.rout.T11020BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.rout.TblTermChannelInf2;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * 交行终端通道配置维护
 * @author crystal
 *
 */
public class T11020Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T11020BO t11020BO = (T11020BO)ContextUtil.getBean("T11020BO");
	
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
	private String cardType;//卡类型
	private String insMcht2;//机构交易商户号
	private String insTerm2;//机构交易终端号
	private String insMcht;//机构核心商户号
	private String insTerm;//机构核心终端号
	private String reserve01;//索引值
	private String stat;//状态
	private String modiTime;//修改时间
	private String modiOprId;//修改人
	private String checkTime;//审核时间
	private String checkOprId;//审核人
	private String lmk;//终端主密钥
	private String parameterList;
	
	
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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getInsMcht2() {
		return insMcht2;
	}

	public void setInsMcht2(String insMcht2) {
		this.insMcht2 = insMcht2;
	}

	public String getInsTerm2() {
		return insTerm2;
	}

	public void setInsTerm2(String insTerm2) {
		this.insTerm2 = insTerm2;
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
			}
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "，维护交行终端通道配置操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 添加新的交行终端通道配置
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		String sql = "select count(*) from TBL_TERM_CHANNEL_INF_2 where STAT <> '1' and term_ins='"+this.getTermIns()+"' and mcht_mcc='"+this.getMchtMcc()
			+"' and mcht_id='"+this.getMchtId()+"' and mcht_term_id='"+this.getMchtTermId()+"' and trim(card_type)='"+ this.getCardType().trim()+"'";;
			
		String count = commQueryDAO.findCountBySQLQuery(sql);
		if(!"0".equals(count)) {
			return "您所添加的交行终端通道配置已经存在！";
		}
		
 		TblTermChannelInf2 tblTermChannelInf = new TblTermChannelInf2();
 		tblTermChannelInf.setId(StringUtil.getUUID());
 		tblTermChannelInf.setTermIns(this.getTermIns());
 		tblTermChannelInf.setMchtMcc(this.getMchtMcc());
 		tblTermChannelInf.setMchtId(this.getMchtId());
 		tblTermChannelInf.setMchtTermId(this.getMchtTermId());
 		tblTermChannelInf.setInsMcc(this.getInsMcc());
 		tblTermChannelInf.setInsMcht(this.getInsMcht());//对应核心商户号
 		tblTermChannelInf.setInsTerm(this.getInsTerm());//对应核心终端号
 		tblTermChannelInf.setCardType(this.getCardType());//卡类型
 		tblTermChannelInf.setInsMcht2(this.getInsMcht2());//交易商户号
 		tblTermChannelInf.setInsTerm2(this.getInsTerm2());//交易终端号
 		tblTermChannelInf.setLmk(this.getLmk());//主密钥
 		tblTermChannelInf.setReserve02(this.getLmk());
// 		tblTermChannelInf.setReserve01(this.reserve01);//索引值
 		
 		//modify by zxc 20130510 转换成16进制存入数据库 start 
 		tblTermChannelInf.setReserve01(DecToHex(this.reserve01));//索引值
 		//modify by zxc 20130510 转换成16进制存入数据库 end
 		
 		
 		tblTermChannelInf.setModiOprId(operator.getOprId());
 		tblTermChannelInf.setModiTime(CommonFunction.getCurrentDateTime());
		tblTermChannelInf.setStat(ADD_TO_CHECK);
		tblTermChannelInf.setTermInsOld(this.getTermIns());
 		tblTermChannelInf.setMchtMccOld(this.getMchtMcc());
 		tblTermChannelInf.setMchtIdOld(this.getMchtId());
 		tblTermChannelInf.setMchtTermIdOld(this.getMchtTermId());
 		tblTermChannelInf.setInsMccOld(this.getInsMcc());
 		tblTermChannelInf.setInsMchtOld(this.getInsMcht());//对应核心商户号
 		tblTermChannelInf.setInsTermOld(this.getInsTerm());//对应核心终端号
 		tblTermChannelInf.setCardTypeOld(this.getCardType());//卡类型
 		tblTermChannelInf.setInsMcht2Old(this.getInsMcht2());//交易商户号
 		tblTermChannelInf.setInsTerm2Old(this.getInsTerm2());//交易终端号
 		tblTermChannelInf.setCreTime(CommonFunction.getCurrentDateTime());
 		tblTermChannelInf.setCreOprId(operator.getOprId());
		
		t11020BO.add(tblTermChannelInf);
		return Constants.SUCCESS_CODE;
	}
	
	/**删除交行终端通道配置
	 * delete 
	 * @return
	 */
	public String delete() {
		TblTermChannelInf2 tblTermChannelInf = t11020BO.get(this.getId());
		if(tblTermChannelInf == null) {
			return "没有找到要删除的交行终端通道配置信息";
		}
		tblTermChannelInf.setModiTime(CommonFunction.getCurrentDateTime());
		tblTermChannelInf.setModiOprId(operator.getOprId());
		tblTermChannelInf.setStat(DELETE_TO_CHECK);
		t11020BO.update(tblTermChannelInf);
		return Constants.SUCCESS_CODE;
	}
	
	/** 修改交行终端通道配置
	 * 
	 * @return
	 */
	public String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		jsonBean.parseJSONArrayData(getParameterList());
		   int len = jsonBean.getArray().size();
		   List<TblTermChannelInf2> tblTermChannelInfList = new ArrayList<TblTermChannelInf2>(len);	
			for(int i = 0; i < len; i++) {					   
			    jsonBean.setObject(jsonBean.getJSONDataAt(i));
			    String id = jsonBean.getJSONDataAt(i).getString("id");
			    
			    TblTermChannelInf2 tblTermChannelInf = t11020BO.get(id);
				BeanUtils.setObjectWithPropertiesValue(tblTermChannelInf, jsonBean, false);
				tblTermChannelInf.setTermIns(tblTermChannelInf.getTermIns().split("-")[0]);
				tblTermChannelInf.setModiOprId(operator.getOprId());
				tblTermChannelInf.setModiTime(CommonFunction.getCurrentDateTime());
				if(tblTermChannelInf.getStat().equals(NORMAL)){
					tblTermChannelInf.setStat(MODIFY_TO_CHECK);
				}
							
				String sql = "select count(*) from TBL_TERM_CHANNEL_INF_2 where STAT <> '1' and term_ins='" + tblTermChannelInf.getTermIns()
					+"' and mcht_mcc='" + tblTermChannelInf.getMchtMcc() + "' and mcht_id='" + tblTermChannelInf.getMchtId()
					+"' and mcht_term_id='" + tblTermChannelInf.getMchtTermId() + "' and trim(card_type)='"+ tblTermChannelInf.getCardType().trim()+"'"
					+" and id != '"+tblTermChannelInf.getId()+"'";
				String count = commQueryDAO.findCountBySQLQuery(sql);
				if(!"0".equals(count)) {
					return "您所修改的交行终端通道配置已经存在！";
				}
				//判断集合中是否已包含相同内容的记录20121129
				if(tblTermChannelInfList!=null && tblTermChannelInfList.size()>0){
					for(int j=0 ; j<tblTermChannelInfList.size() ; j++){
						TblTermChannelInf2 temp = tblTermChannelInfList.get(j);
						if(tblTermChannelInf.getTermIns().equals(temp.getTermIns()) && tblTermChannelInf.getMchtMcc().equals(temp.getMchtMcc()) 
								&& tblTermChannelInf.getMchtId().equals(temp.getMchtId()) && tblTermChannelInf.getMchtTermId().equals(temp.getMchtTermId()) 
								&& tblTermChannelInf.getCardType().equals(temp.getCardType())){//只要文件中有两条数据重复，即提示失败信息
							return "您所提交的交行终端通道配置有重复！";
						}
					}
				}
				tblTermChannelInfList.add(tblTermChannelInf);
			}
			t11020BO.update(tblTermChannelInfList);
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
