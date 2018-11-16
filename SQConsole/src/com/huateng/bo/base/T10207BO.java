package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.TblBrhTlrInfo;
import com.huateng.po.TblBrhTlrInfoPK;




/**
 * Title:虚拟会员维护Bo
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
public interface T10207BO {
	public TblBrhTlrInfo get(TblBrhTlrInfoPK id) ; // 获取卡BIN信息

	public void createTblBrhTlrInfo(TblBrhTlrInfo TblBrhTlrInfo) ;// 创建卡BIN信息

	public void update(TblBrhTlrInfo TblBrhTlrInfo) ; // 更新卡BIN信息

	public void delete(TblBrhTlrInfoPK id) ;// 删除卡BIN信息
	
	public void update(List<TblBrhTlrInfo> TblBrhTlrInfoList); // 更新卡BIN信息
	
}
