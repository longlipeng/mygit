package com.huateng.bo.risk;

import com.huateng.po.risk.CstAfterRuleInf;
import com.huateng.po.risk.CstAfterRuleInfPK;

public interface T40501BO {

	/**
	 * 添加商户事后风险预警系数
	 * @param cstAfterRuleInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public String add( CstAfterRuleInf cstAfterRuleInf) throws Exception;
	/**
	 * 更新商户事后风险预警系数
	 * @param cstAfterRuleInf
	 * @return
	 * @throws Exception
	 * 
	 */
	public String update(CstAfterRuleInf cstAfterRuleInf) throws Exception;
	/**
	 * 查询商户事后风险预警系数
	 * @param key
	 * @return
	 * 
	 */
	public CstAfterRuleInf get(CstAfterRuleInfPK key);
	
	/**
	 * 删除商户事后风险预警系数
	 * @param key
	 * @return
	 * @throws Exception
	 * 
	 */
	public void delete(CstAfterRuleInfPK key) throws Exception;
	
}
