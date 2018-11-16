package com.huateng.bo.risk;

import com.huateng.po.TblCtlCardInf;
import com.huateng.po.risk.TblRiskRefuse;

/**
 * Title: 卡黑名单审核
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-24
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author crystal
 * 
 * @version 1.0
 */
public interface T40206BO {

	/**
	 * 审核通过新增待审核的卡黑名单
	 * @return
	 * @throws Exception
	 * 2012-4-18
	 */
	public String acceptAdd(TblCtlCardInf tblCtlCardInf) throws Exception;
	
	/**
	 * 审核拒绝新增待审核的卡黑名单
	 * @return
	 * @throws Exception
	 * 2012-4-18
	 */
	public String refuseAdd(TblCtlCardInf tblCtlCardInf) throws Exception;
	
	/**
	 * 审核通过删除待审核的卡黑名单
	 * @return
	 * @throws Exception
	 * 2012-4-18
	 */
	public String acceptDelete(TblCtlCardInf tblCtlCardInf) throws Exception;
	
	/**
	 * 审核通过删除待审核的卡黑名单
	 * @return
	 * @throws Exception
	 * 2012-4-18
	 */
	public String refuseDelete(TblCtlCardInf tblCtlCardInf) throws Exception;
	
	/**
	 * 保存审核拒绝原因
	 * @return
	 * @throws Exception
	 * 2012-4-18
	 */
	public String saveRefuseInfo(TblRiskRefuse riskRefuse) throws Exception;
	
	public TblCtlCardInf get(String key) throws Exception;
	
	public String delete(String key)throws Exception;
	
}
