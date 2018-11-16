package com.huateng.bo.risk;

import java.io.File;
import java.util.List;
import com.huateng.common.Operator;
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
 * @version 1.0
 */
public interface T40208BO {
	/**
	 * 添加商户交易黑名单信息
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 2010-8-26下午11:33:52
	 */
	public String add( TblRiskMchtTranCtl tblRiskMchtTranCtl) throws Exception;
	/**
	 * 更新商户交易黑名单信息
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 2010-8-26下午11:34:46
	 */
	public String update(TblRiskMchtTranCtl tblRiskMchtTranCtl) throws Exception;
	/**
	 * 查询商户交易黑名单信息
	 * @param key
	 * @return
	 * 2010-8-26下午11:35:16
	 */
	public TblRiskMchtTranCtl get(String key);
	
	public String importFile(List<File> fileList,List<String> fileNameList,Operator operator) throws Exception;
	
	/**
	 * 删除商户交易黑名单信息
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 2010-8-26下午11:34:46
	 */
	public void delete(String key) throws Exception;
	
}
