package com.huateng.bo.error;

import java.io.File;
import java.util.List;


import com.huateng.common.Operator;
import com.huateng.po.error.BthErrRegistTxn;

/**
 * Title: 差错登录BO
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
public interface T60501BO {
	/**
	 * 查询差错信息
	 * @param brhId    差错编号
	 * @return
	 */
	public BthErrRegistTxn get(String brhId);
//	public BthErrRegistTxn get(String errSeqNo);
	/**
	 * 添加差错信息
	 * @param BthErrRegistTxn    差错信息
	 * @return
	 */
	public String add(BthErrRegistTxn BthErrRegistTxn);
	/**
	 * 更新差错信息
	 * @param BthErrRegistTxnList    差错信息列表
	 * @return
	 */
	public String update(BthErrRegistTxn BthErrRegistTxn);
	/**
	 * 删除差错信息
	 * @param BthErrRegistTxn    差错信息
	 * @return
	 */
	public String delete(BthErrRegistTxn BthErrRegistTxn);
	/**
	 * 删除差错信息
	 * @param brhId    差错编号
	 * @return
	 */
	public String delete(String brhId);
	
	public String getNextId();
	
	public String importFile(List<File> fileList,List<String> fileNameList,Operator operator) throws Exception;
}
