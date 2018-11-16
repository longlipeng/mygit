package com.huateng.bo.risk;

import java.io.File;
import java.util.List;
import com.huateng.common.Operator;
import com.huateng.po.risk.TblRiskTermTranLimit;

public interface T40216BO {

	/**
	 * 添加终端交易权限信息
	 * @param tblRiskTermTranLimit
	 * @return
	 * @throws Exception
	 * 
	 */
	public String add( TblRiskTermTranLimit tblRiskTermTranLimit) throws Exception;
	/**
	 * 更新终端交易权限信息
	 * @param tblRiskTermTranLimit
	 * @return
	 * @throws Exception
	 * 
	 */
	public String update(TblRiskTermTranLimit tblRiskTermTranLimit) throws Exception;
	public String updateList(List<TblRiskTermTranLimit> tblRiskTermTranLimitList) throws Exception;
	/**
	 * 查询终端交易权限信息
	 * @param key
	 * @return
	 * 
	 */
	public TblRiskTermTranLimit get(String key);
	
	public String importFile(List<File> fileList,List<String> fileNameList,Operator operator) throws Exception;
	
	/**
	 * 删除终端交易权限信息
	 * @param tblRiskTermTranLimit
	 * @return
	 * @throws Exception
	 * 
	 */
	public void delete(String key) throws Exception;
}
