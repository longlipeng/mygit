package com.huateng.bo.impl.error;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.huateng.bo.error.T60501BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.error.BthErrRegistTxnDAO;

import com.huateng.po.TblCtlMchtInf;
import com.huateng.po.error.BthErrRegistTxn;
import com.huateng.system.util.CommonFunction;

/**
 * Title:机构信息BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T60501BOTarget implements T60501BO {

	private BthErrRegistTxnDAO thErrRegistTxnDAO;
	/**
	 * @param thErrRegistTxnDAO the thErrRegistTxnDAO to set
	 */
	public void setThErrRegistTxnDAO(BthErrRegistTxnDAO thErrRegistTxnDAO) {
		this.thErrRegistTxnDAO = thErrRegistTxnDAO;
	}


	private ICommQueryDAO commQueryDAO;
	
	/* (non-Javadoc)
	 * @see com.huateng.bo.error.T60501BO#add(com.huateng.po.error.BthErrRegistTxn)
	 */
	public String add(BthErrRegistTxn bthErrRegistTxn) {
		// TODO Auto-generated method stub
		return thErrRegistTxnDAO.save(bthErrRegistTxn);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.error.T60501BO#delete(com.huateng.po.error.BthErrRegistTxn)
	 */
	public String delete(BthErrRegistTxn BthErrRegistTxn) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.error.T60501BO#delete(java.lang.String)
	 */
	public String delete(String brhId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.error.T60501BO#get(java.lang.String)
	 */
	public BthErrRegistTxn get(String errSeqNo) {
		// TODO Auto-generated method stub
		return thErrRegistTxnDAO.load(errSeqNo);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.error.T60501BO#update(java.util.List)
	 */
	public String update(BthErrRegistTxn BthErrRegistTxn) {
		// TODO Auto-generated method stub
//		BthErrRegistTxn.setErrStatus(CommonFunction.transYuanToFen(BthErrRegistTxn.getErrStatus()));
		BthErrRegistTxn.setErrStatus(BthErrRegistTxn.getErrStatus());
		return thErrRegistTxnDAO.update(BthErrRegistTxn);
		  
	}

	public BthErrRegistTxnDAO getThErrRegistTxnDAO() {
		return thErrRegistTxnDAO;
	}

	public void setThErrRegistTxn1DAO(BthErrRegistTxnDAO thErrRegistTxnDAO) {
		this.thErrRegistTxnDAO = thErrRegistTxnDAO;
	}
	
	public String getNextId() {
		String sql = "select ERR_SEQ_NO.nextval from dual";
		List list = commQueryDAO.findBySQLQuery(sql);
		String nextId = "0";
		if(list != null || list.isEmpty())
		{
			String num = list.get(0).toString();
			if(list.get(0).toString().length() <= 8)
				nextId = CommonFunction.fillString(num, '0', 8, false);
			else
				nextId = Hex.encodeHexString(num.getBytes()).toString();
		}
		return nextId;
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	

	/* (non-Javadoc)
	 * @see com.huateng.bo.T40202BO#importFile(java.util.List, java.util.List, com.huateng.common.Operator)
	 */
	@SuppressWarnings("unchecked")
	public String importFile(List<File> fileList, List<String> fileNameList,
			Operator operator) throws Exception {
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		// 返回信息
		String returnMsg = "";
		// 文件名称索引
		int fileNameIndex = 0;
		// 文件名称
		String fileName = null;
		String sql = null;
		// 存放查询结果集
		List<Object[]> dataList = null;
		

		//商户编号
		 String mchntNo;
		//商户名称
		 String mchntNm;
		//差错交易类型
		 String errType;
		//借贷标志
		 String cdFalg;
		//本金
		 String amt1;
		//手续费
		 String fee1;
		//登记时间
		 String registTime;
		//生效时间
		 String startTime;
		//差错描述
		 String errDesc;
		
		
		TblCtlMchtInf tblCtlMchtInf = null;
		
		FileInputStream fileInputStream = null;
		
		for(File file : fileList) {
			
			fileInputStream = new FileInputStream(file);
			
			workbook = new HSSFWorkbook(fileInputStream);
			
			sheet = workbook.getSheetAt(0);
			
			
			
			fileName = fileNameList.get(fileNameIndex);
			
			for(int rowIndex = sheet.getFirstRowNum()+1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {				
				row = sheet.getRow(rowIndex);				
				for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
					if (row.getCell(i).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//						Integer reportYear = new Integer((int) row.getCell(i).getNumericCellValue());
						row.getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
//						row.getCell(i).setCellValue(reportYear.toString());
//						
					}
//					if(row.getCell(i).getCellType() != HSSFCell.CELL_TYPE_STRING)
//						returnMsg += "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，" + 
//									"第" + (i + 1) + "列单元格格式不正确<br>";
			}

//				if(!"".equals(returnMsg))
//					return returnMsg;

				
				mchntNo = row.getCell(0).getStringCellValue();
				// 校验商户编号
				if(mchntNo.getBytes().length > 15)
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，受控商户编号长度超出限制<br>";
				sql = "select mcht_nm,eng_name from tbl_mcht_base_inf where mcht_no = '" + mchntNo + "'";
				
				dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
				if(dataList.size() == 0)
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，商户编号不存在<br>";
//				// 商户中文名称
				String saMerChName = dataList.get(0)[0].toString();
//				// 商户英文名称
//				saMerEnName = dataList.get(0)[1].toString();
//				
//				saLimitAmt = row.getCell(1).getStringCellValue();
//				saLimitAmt = CommonFunction.transYuanToFen(saLimitAmt);
				
				mchntNm = row.getCell(1).getStringCellValue();
				// 校验商户编号
				if(!saMerChName.trim().equals(mchntNm.trim()))
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，商户名称跟商户编号不对应<br>";				
			
				errType = row.getCell(2).getStringCellValue();
				// 校验受控动作
				if(!("1".equals(errType) || "2".equals(errType) || "3".equals(errType) || "4".equals(errType)))
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，差错类型不在可选范围内<br>";
				
				cdFalg = row.getCell(3).getStringCellValue();
				// 校验受控动作
				if(!("1".equals(cdFalg) || "2".equals(cdFalg) || "3".equals(cdFalg) || "4".equals(cdFalg)))
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，差错类型不在可选范围内<br>";
				
				amt1 = row.getCell(4).getStringCellValue();
				
				if(!CommonFunction.isMoney(amt1))
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，金额包含非法字内容<br>";
				
				fee1 = row.getCell(5).getStringCellValue();
				if(!CommonFunction.isMoney(fee1))
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，手续费包含非法内容<br>";
				
				registTime = row.getCell(6).getStringCellValue();
				startTime = row.getCell(7).getStringCellValue();
				errDesc = row.getCell(8).getStringCellValue();
				
				// 验证通过，对商户黑名单信息进行处理
				BthErrRegistTxn bthErrRegistTxn = new BthErrRegistTxn();
				bthErrRegistTxn.setId(this.getNextId());
				//商户编号
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
				
				thErrRegistTxnDAO.saveOrUpdate(bthErrRegistTxn);
			}
			fileInputStream.close();
			fileNameIndex++;
		}
		return Constants.SUCCESS_CODE;
	}
	
}
