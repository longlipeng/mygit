package com.huateng.bo.risk;

import java.io.File;
import java.util.List;
import com.huateng.common.Operator;
import com.huateng.po.risk.TblRiskMchtTranLimit;

public interface T40214BO {

	/**
	 * 添加商户交易权限信息
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public String add( TblRiskMchtTranLimit tblRiskMchtTranLimit) throws Exception;
	/**
	 * 更新商户交易权限信息
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public String update(TblRiskMchtTranLimit tblRiskMchtTranLimit) throws Exception;
	public String updateList(List<TblRiskMchtTranLimit> tblRiskMchtTranLimitList) throws Exception;
	/**
	 * 查询商户交易权限信息
	 * @param key
	 * @return
	 * 
	 */
	public TblRiskMchtTranLimit get(String key);
	
	public String importFile(List<File> fileList,List<String> fileNameList,Operator operator) throws Exception;
	
	/**
	 * 删除商户交易权限信息
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public void delete(String key) throws Exception;

}
