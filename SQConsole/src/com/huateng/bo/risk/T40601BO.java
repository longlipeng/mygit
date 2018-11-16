package com.huateng.bo.risk;

import java.io.File;
import java.util.List;

import com.huateng.common.Operator;
import com.huateng.po.TblCnpcCardInf;
import com.huateng.po.TblCnpcCardInfPK;

/**
 * Title: 中石油卡黑名单信息管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-24
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public interface T40601BO {
	/**
	 * 添加卡黑名单信息
	 * @return
	 * @throws Exception
	 * 2010-8-24下午05:19:30
	 */
	public String add(TblCnpcCardInf tblCnpcCardInf) throws Exception;
	/**
	 * 修改卡黑名单信息
	 * @return
	 * @throws Exception
	 * 2010-8-24下午05:20:26
	 */
	public String update(TblCnpcCardInf tblCnpcCardInf) throws Exception;
	/**
	 * 查询卡黑名单信息
	 * @param key
	 * @return
	 * 2010-8-24下午11:09:53
	 */
	public TblCnpcCardInf get(TblCnpcCardInfPK key);
	/**
	 * 删除卡黑名单信息
	 * @return
	 * @throws Exception
	 * 2010-8-24下午05:20:26
	 */
	public void delete(TblCnpcCardInfPK key) throws Exception;
}
