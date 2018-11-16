package com.huateng.struts.error;

import java.io.File;
import java.util.List;

import com.huateng.bo.error.T60501BO;
import com.huateng.common.Constants;
import com.huateng.po.error.BthErrRegistTxn;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:差错调帐登记
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-7
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T60501Action extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	

	//商户编号
	private String mchntNo;
	//商户名称
	private String mchntNm;
	//差错交易类型
	private String errType;
	//借贷标志
	private String cdFalg;
	//本金
	private String amt1;
	//手续费
	private String fee1;
	//登记时间
	private String registTime;
	//生效时间
	private String startTime;
	//差错描述
	private String errDesc;
	
	
	private String resv1;
	
	//机构信息BO
	private T60501BO t60501BO = (T60501BO) ContextUtil.getBean("T60501BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("add".equals(getMethod())) {			
					rspCode = add();			
			} else if("delete".equals(getMethod())) {
				rspCode = delete();
			} else if("update".equals(getMethod())) {
				rspCode = update();
			} else if("uploadFile".equals(method)) {
				rspCode = uploadFile();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，差错调帐登记操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 添加机构信息
	 * @throws Exception
	 */
	private String add() throws Exception {
		BthErrRegistTxn bthErrRegistTxn = new BthErrRegistTxn();
		bthErrRegistTxn.setId(t60501BO.getNextId());
//		//判断商户号是否存在
//		if(t60501BO.get(mchntNo()) != null) {			
//			return ErrorCode.T60501BO_01;
//		}		
//		BeanUtils.copyProperties(bthErrRegistTxn, this);			
//		//商户编号
		bthErrRegistTxn.setMchntNo(mchntNo);
		//商户名称
		bthErrRegistTxn.setMchntNm(mchntNm);
		//差错交易类型
		bthErrRegistTxn.setErrType(errType);
		//借贷标志
		bthErrRegistTxn.setCdFlag(cdFalg);	
		//本金
		bthErrRegistTxn.setAmt1(amt1);
		//手续费
		bthErrRegistTxn.setFee1(fee1);
		//登记时间
		bthErrRegistTxn.setRegistTime(registTime);
		//生效时间
		bthErrRegistTxn.setStartTime(startTime);
		
		//差错描述
		bthErrRegistTxn.setErrDesc(errDesc);
		
		t60501BO.add(bthErrRegistTxn);
//		
//		log("添加机构信息成功。操作员编号：" + operator.getOprId()
//				+ "，机构编号：" + getBrhId());

		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除机构信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String delete() throws Exception {
		
//		String sql = "select * from tbl_opr_info where brh_id = '" + getBrhId() + "' ";
//		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
//		//判断是否有操作员在此机构下
//		if(dataList.size() > 0) {
//			return ErrorCode.T10101_02;
//		}
//		sql = "select * from TBL_MCHT_BASE_INF_TMP where acq_inst_id = '" + getBrhId() + "' ";
//		dataList = commQueryDAO.findBySQLQuery(sql);
//		//判断是否有商户在此机构下
//		if(dataList.size() > 0) {
//			return ErrorCode.T10101_03;
//		}
//		//删除机构信息
//		t60501BO.delete(getBrhId());
//		log("删除机构信息成功。操作员编号：" + operator.getOprId()
//				+ "，机构编号：" + getBrhId());
//		//加载操作员所在机构信息
//		reloadOprBrhInfo();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 更新机构信息
	 * @return
	 */
	private String update() throws Exception {
//		jsonBean.parseJSONArrayData(getBrhDataList());
//		int len = jsonBean.getArray().size();
//		List<TblBrhInfo> brhInfoList = new ArrayList<TblBrhInfo>();
//		for(int i = 0; i < len; i++) {
//			jsonBean.setObject(jsonBean.getJSONDataAt(i));
//			TblBrhInfo tblBrhInfo = new TblBrhInfo();
//			BeanUtils.setObjectWithPropertiesValue(tblBrhInfo,jsonBean,true);
//			tblBrhInfo.setLastUpdOprId(operator.getOprId());
//			tblBrhInfo.setLastUpdTxnId(getTxnId());
//			tblBrhInfo.setLastUpdTs(CommonFunction.getCurrentDateTime());
//			brhInfoList.add(tblBrhInfo);
//		}
//		t60501BO.update(brhInfoList);
//		log("更新机构信息成功。操作员编号：" + operator.getOprId());
//		//加载操作员所在机构信息
//		reloadOprBrhInfo();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 批量新增商户黑名单信息
	 * @return
	 * 2010-8-26下午11:59:38
	 * @throws Exception 
	 */
	private String uploadFile() throws Exception {
		rspCode = t60501BO.importFile(xlsFile, xlsFileFileName, operator);
		return rspCode;
	}
	
	// 文件集合
	private List<File> xlsFile;
	// 文件名称集合
	private List<String> xlsFileFileName;
	/**
	 * @return the mchntNo
	 */
	public String getMchntNo() {
		return mchntNo;
	}

	/**
	 * @param mchntNo the mchntNo to set
	 */
	public void setMchntNo(String mchntNo) {
		this.mchntNo = mchntNo;
	}

	/**
	 * @return the mchntNm
	 */
	public String getMchntNm() {
		return mchntNm;
	}

	/**
	 * @param mchntNm the mchntNm to set
	 */
	public void setMchntNm(String mchntNm) {
		this.mchntNm = mchntNm;
	}

	/**
	 * @return the errType
	 */
	public String getErrType() {
		return errType;
	}

	/**
	 * @param errType the errType to set
	 */
	public void setErrType(String errType) {
		this.errType = errType;
	}

	/**
	 * @return the cdFalg
	 */
	public String getCdFalg() {
		return cdFalg;
	}

	/**
	 * @param cdFalg the cdFalg to set
	 */
	public void setCdFalg(String cdFalg) {
		this.cdFalg = cdFalg;
	}

	/**
	 * @return the amt1
	 */
	public String getAmt1() {
		return amt1;
	}

	/**
	 * @param amt1 the amt1 to set
	 */
	public void setAmt1(String amt1) {
		this.amt1 = amt1;
	}

	/**
	 * @return the fee1
	 */
	public String getFee1() {
		return fee1;
	}

	/**
	 * @param fee1 the fee1 to set
	 */
	public void setFee1(String fee1) {
		this.fee1 = fee1;
	}

	/**
	 * @return the registTime
	 */
	public String getRegistTime() {
		return registTime;
	}

	/**
	 * @param registTime the registTime to set
	 */
	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the errDesc
	 */
	public String getErrDesc() {
		return errDesc;
	}

	/**
	 * @param errDesc the errDesc to set
	 */
	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	/**
	 * @return the resv1
	 */
	public String getResv1() {
		return resv1;
	}

	/**
	 * @param resv1 the resv1 to set
	 */
	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}

	/**
	 * @return the t60501BO
	 */
	public T60501BO getT60501BO() {
		return t60501BO;
	}

	/**
	 * @param t60501bo the t60501BO to set
	 */
	public void setT60501BO(T60501BO t60501bo) {
		t60501BO = t60501bo;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<File> getXlsFile() {
		return xlsFile;
	}

	public void setXlsFile(List<File> xlsFile) {
		this.xlsFile = xlsFile;
	}

	public List<String> getXlsFileFileName() {
		return xlsFileFileName;
	}

	public void setXlsFileFileName(List<String> xlsFileFileName) {
		this.xlsFileFileName = xlsFileFileName;
	}
	
	
}
