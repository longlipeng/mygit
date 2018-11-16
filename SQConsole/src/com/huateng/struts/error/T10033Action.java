package com.huateng.struts.error;

import com.huateng.bo.error.T10033BO;
import com.huateng.bo.error.T10031BO;
import com.huateng.common.Constants;
import com.huateng.po.error.BthCupErrTxn;
import com.huateng.po.error.BthCupErrTxnPK;
import com.huateng.po.error.TblAlgoDtl;
import com.huateng.po.error.TblAlgoDtlCheck;
import com.huateng.po.error.TblAlgoDtlPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

public class T10033Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	T10031BO t10031BO = (T10031BO) ContextUtil.getBean("T10031BO");
	T10033BO t10033BO = (T10033BO) ContextUtil.getBean("T10033BO");
	
	public static String CUT_TO_CHECK = "0";
	public static String CUT = "1";
	public static String CUT_REFUSE = "2";
	
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		
		TblAlgoDtlCheck tblAlgoDtlCheck = t10033BO.get(id);
		String state = tblAlgoDtlCheck.getSaState();
		if("accept".equals(method)) {
			if(state.equals(CUT_TO_CHECK)){
				log("审核通过追扣待审核的已清算交易");
				rspCode = accept();
			}
		} else if("refuse".equals(method)) {
			if(state.equals(CUT_TO_CHECK)){
				log("审核拒绝追扣待审核的已清算交易");
				rspCode = refuse();
			}
		}
		return rspCode;
	}

	/**
	 * 审核通过追扣待审核的已清算交易
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String accept() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			TblAlgoDtlCheck tblAlgoDtlCheck = t10033BO.get(id);
			TblAlgoDtlPK pk = new TblAlgoDtlPK(tblAlgoDtlCheck.getDateSettlmt(),tblAlgoDtlCheck.getTxnKey());
			TblAlgoDtl tblAlgoDtl = t10031BO.get(pk);
			
			//往差错流水表插入记录
//			BthCupErrTxnPK pk2 = new BthCupErrTxnPK(tblAlgoDtlCheck.getDateSettlmt(),tblAlgoDtl.getTxnNum(),GenerateNextId.getSSn());
			//20121024,posp需要根据此日期做后续差错处理
			BthCupErrTxnPK pk2 = new BthCupErrTxnPK(CommonFunction.getCurrentDate(),tblAlgoDtl.getTxnNum(),GenerateNextId.getSSn());
			
			BthCupErrTxn bthCupErrTxn = new BthCupErrTxn();
			bthCupErrTxn.setId(pk2);
			bthCupErrTxn.setMchtId(tblAlgoDtl.getMchtCd());
			bthCupErrTxn.setTermId(tblAlgoDtl.getTermId());
			bthCupErrTxn.setStlmFlg("1");
//			bthCupErrTxn.setTransDateTime(tblAlgoDtl.getTransDate().trim());
			bthCupErrTxn.setTransDateTime(CommonFunction.getCurrentDate());//20121024，如果此字段无值则查看详情时
			bthCupErrTxn.setOrgDateTime(tblAlgoDtl.getTransDate().trim());//20121012修改，解决追扣类型的差错信息没有显示交易时间的问题
			bthCupErrTxn.setPan(tblAlgoDtl.getPan());
			bthCupErrTxn.setErrFlag("H01");
			bthCupErrTxn.setAmtTrans(tblAlgoDtl.getSettlAmt());
			bthCupErrTxn.setFeeCredit(formatYuanToFen(tblAlgoDtl.getFeeCOut()));//转化单位
			bthCupErrTxn.setFeeDebit(formatYuanToFen(tblAlgoDtl.getFeeDOut()));//转化单位
			bthCupErrTxn.setOrgRetrivlRef(tblAlgoDtl.getMisc1().substring(0,12));
			bthCupErrTxn.setOrgTransAmt(tblAlgoDtl.getTransAmt());
			bthCupErrTxn.setOrgTermSsn(tblAlgoDtl.getTermSsn());
			bthCupErrTxn.setOrgTxnSsn(tblAlgoDtl.getTxnSsn());
			bthCupErrTxn.setLstUpdTlr(operator.getOprId());
			bthCupErrTxn.setOrgStlmDate(tblAlgoDtl.getSettlDate());//?
			bthCupErrTxn.setMchtAccInType("00");//商户结算类型 00-实时结算
			bthCupErrTxn.setIpsNo(tblAlgoDtl.getIpsNo());
			rspCode = t10031BO.add(bthCupErrTxn);
			
			//更新审核信息表
			tblAlgoDtlCheck.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblAlgoDtlCheck.setModifyOprId(operator.getOprId());
			tblAlgoDtlCheck.setSaState(CUT);
			
			t10033BO.update(tblAlgoDtlCheck);
		}
		return rspCode;
	}
	/**
	 * 将费用单位由元转化为分（包括负号的判断）
	 * @return
	 * @throws Exception
	 */
	private String formatYuanToFen(String feeYuan) throws Exception{
		feeYuan = feeYuan.replace(".", "");
		StringBuffer result = new StringBuffer();
		if(feeYuan.equals("0"))
			return "000000000000";
		else{
			if(feeYuan.startsWith("-")){
				result.append("-");
				for(int i=0;i<11-feeYuan.length();i++){
					result.append("0");
				}
			}else{
				for(int i=0;i<12-feeYuan.length();i++){
					result.append("0");
				}
			}
			result.append(feeYuan);
		}
		return result.toString();
	}
	
	/**
	 * 审核拒绝追扣待审核的已清算交易
	 * @return
	 * 
	 * @throws Exception 
	 */
	private String refuse() throws Exception {
		if(this.checkOperator())
			return "提交人与审核人不能是同一个人";
		else{
			//更新审核信息表
			TblAlgoDtlCheck tblAlgoDtlCheck = t10033BO.get(id);
			tblAlgoDtlCheck.setUpdateTime(CommonFunction.getCurrentDateTime());
			tblAlgoDtlCheck.setModifyOprId(operator.getOprId());
			tblAlgoDtlCheck.setSaState(CUT_REFUSE);
			tblAlgoDtlCheck.setCutsInfo(cutsInfo);
			
			t10033BO.update(tblAlgoDtlCheck);
		}
		return Constants.SUCCESS_CODE;
	}
	
	//判断卡黑名单的操作人和审核人是否相同
	private boolean checkOperator()throws Exception {
		TblAlgoDtlCheck tblAlgoDtlCheck = t10033BO.get(id);
		String oprID = tblAlgoDtlCheck.getOprId();
		if(operator.getOprId().equals(oprID))
			return true;//相同
		else
			return false;//不同
	}
	
	private String id;
	//拒绝原因
	private String cutsInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCutsInfo() {
		return cutsInfo;
	}

	public void setCutsInfo(String cutsInfo) {
		this.cutsInfo = cutsInfo;
	}
	
}
