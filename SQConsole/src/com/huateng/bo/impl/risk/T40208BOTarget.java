/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-26       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.bo.impl.risk;

import java.io.File;
import java.util.List;
import com.huateng.bo.risk.T40208BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.dao.iface.risk.TblRiskMchtTranCtlDAO;
import com.huateng.po.risk.TblRiskMchtTranCtl;

/**
 * Title: 商户交易黑名单管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-26
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T40208BOTarget implements T40208BO {

	private TblRiskMchtTranCtlDAO tblRiskMchtTranCtlDAO;
//	private ICommQueryDAO commQueryDAO;
	
	public TblRiskMchtTranCtlDAO getTblRiskMchtTranCtlDAO() {
		return tblRiskMchtTranCtlDAO;
	}

	public void setTblRiskMchtTranCtlDAO(TblRiskMchtTranCtlDAO tblRiskMchtTranCtlDAO) {
		this.tblRiskMchtTranCtlDAO = tblRiskMchtTranCtlDAO;
	}

	public String add(TblRiskMchtTranCtl tblRiskMchtTranCtl) throws Exception {
		tblRiskMchtTranCtlDAO.save(tblRiskMchtTranCtl);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.risk.T40208BO#get(java.lang.String)
	 */
	public TblRiskMchtTranCtl get(String key) {
		return tblRiskMchtTranCtlDAO.get(key);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.risk.T40208BO#update(com.huateng.po.TblCtlMchtInf)
	 */
	public String update(TblRiskMchtTranCtl tblRiskMchtTranCtl) throws Exception {
	//	tblCtlMchtInf.setSaLimitAmt(CommonFunction.transYuanToFen(tblCtlMchtInf.getSaLimitAmt()));
		tblRiskMchtTranCtlDAO.update(tblRiskMchtTranCtl);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.risk.T40208BO#importFile(java.util.List, java.util.List, com.huateng.common.Operator)
	 */
	/*public String importFile(List<File> fileList, List<String> fileNameList,
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
		
		// 受控商户号
		String saMerNo = null;
		// 商户中文名称
		String saMerChName = null;
		// 商户英文名称
		String saMerEnName = null;
		// 分行号
		String saZoneNo = null;
		// 受控金额
		String saLimitAmt = null;
		// 受控动作
		String saAction = null;
		// 机构
		String saBrhId = operator.getOprBrhId();
		// 操作员
		String saOprId = operator.getOprId();
		// 时间
		String saInitTime = CommonFunction.getCurrentDateTime();
		
		TblCtlMchtInf tblCtlMchtInf = null;
		
		FileInputStream fileInputStream = null;
		
		for(File file : fileList) {
			
			fileInputStream = new FileInputStream(file);
			
			workbook = new HSSFWorkbook(fileInputStream);
			
			sheet = workbook.getSheetAt(0);
			
			fileName = fileNameList.get(fileNameIndex);
			
			for(int rowIndex = sheet.getFirstRowNum(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				
				row = sheet.getRow(rowIndex);
				
				for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
					if(row.getCell(i).getCellType() != HSSFCell.CELL_TYPE_STRING)
						returnMsg += "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，" + 
									"第" + (i + 1) + "列单元格格式不正确<br>";

				if(!"".equals(returnMsg))
					return returnMsg;

				
				saMerNo = row.getCell(0).getStringCellValue();
				// 校验商户编号
				if(saMerNo.getBytes().length > 15)
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，受控商户编号长度超出限制<br>";
				sql = "select  mcht_nm,eng_name,bank_no from TBL_MCHT_BASE_INF where MCHT_NO = '" + saMerNo + "'";
				
				dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
				if(dataList.size() == 0)
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，商户编号不存在<br>";
				// 商户中文名称
				saMerChName = dataList.get(0)[0].toString();
				// 商户英文名称
				saMerEnName = dataList.get(0)[1].toString();
				saZoneNo = dataList.get(0)[2].toString();
				
				saLimitAmt = row.getCell(1).getStringCellValue();
//				saLimitAmt = CommonFunction.transYuanToFen(saLimitAmt);
				
				// 校验受控金额
				if(!CommonFunction.isAllDigit(saLimitAmt))
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，受控金额包含非数字内容<br>";
				
				if(saLimitAmt.getBytes().length > 12) 
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，受控金额长度超出限制<br>";

				
				saAction = row.getCell(2).getStringCellValue();
				// 校验受控动作
				if(!("1".equals(saAction) || "2".equals(saAction) || "3".equals(saAction) || "4".equals(saAction)))
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，受控动作不在可选范围内<br>";
				
				
				// 验证通过，对商户黑名单信息进行处理
				tblCtlMchtInf = new TblCtlMchtInf();
				tblCtlMchtInf.setId(saMerNo);
				tblCtlMchtInf.setSaMerChName(saMerChName);
				tblCtlMchtInf.setSaMerEnName(saMerEnName);
				tblCtlMchtInf.setSaZoneNo(saZoneNo);
				tblCtlMchtInf.setSaLimitAmt(saLimitAmt);
				tblCtlMchtInf.setSaAction(saAction);
				tblCtlMchtInf.setSaInitZoneNo(saBrhId);
				tblCtlMchtInf.setSaInitOprId(saOprId);
				tblCtlMchtInf.setSaInitTime(saInitTime);
				tblCtlMchtInfDAO.saveOrUpdate(tblCtlMchtInf);
			}
			fileInputStream.close();
			fileNameIndex++;
		}
		return Constants.SUCCESS_CODE;
	}*/
	

	/*
	 * (non-Javadoc)
	 * @see com.huateng.bo.risk.T40208BO#delete(java.lang.String)
	 */
	public void delete(String key) {
		tblRiskMchtTranCtlDAO.delete(key);
	}

	public String importFile(List<File> fileList, List<String> fileNameList,Operator operator) throws Exception {
		return null;
	}
	
}
