package com.huateng.bo.risk;

import com.huateng.po.TblCtlMchtInf;
import com.huateng.po.risk.TblWhiteList;

/**
 * Title: 商户白名单管理
 * 
 * Copyright: Copyright (c) 2014-08-01
 * @author shiyiwen
 * 
 * @version 1.0
 */
public interface T41201BO {

	/**
	 * 删除商户白名单信息
	 * @param tblWhiteList
	 * @return
	 * @throws Exception
	 * 2014-08-01
	 */
	public String delete(String key) throws Exception; 
	/**
	 * 查询商户白名单信息
	 * @param tblWhiteList
	 * @return
	 * @throws Exception
	 * 2014-08-04
	 */
	public TblWhiteList get(String key);
	/**
	 * 新增商户白名单信息
	 * @param tblWhiteList
	 * @return
	 * @throws Exception
	 * 2014-08-04
	 */
	public String add(TblWhiteList tblWhiteList) throws Exception;
	
	/**
	 * 修改商户白名单信息
	 * @param tblWhiteList
	 * @return
	 * @throws Exception
	 * 2014-08-04
	 */
	public String update(TblWhiteList tblWhiteList) ;
}
