package com.huateng.framework.ibatis.dao;

import com.huateng.framework.ibatis.model.SerivceFeeRule;
import com.huateng.framework.ibatis.model.SerivceFeeRuleExample;
import com.huateng.framework.ibatis.model.SerivceFeeRuleKey;
import java.util.List;

public interface SerivceFeeRuleDAO {
	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERIVCE_FEE_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:58 CST 2010
	 */
	void insert(SerivceFeeRule record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERIVCE_FEE_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:58 CST 2010
	 */
	int updateByPrimaryKey(SerivceFeeRule record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERIVCE_FEE_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:58 CST 2010
	 */
	int updateByPrimaryKeySelective(SerivceFeeRule record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERIVCE_FEE_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:58 CST 2010
	 */
	List<SerivceFeeRule> selectByExample(SerivceFeeRuleExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERIVCE_FEE_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:58 CST 2010
	 */
	SerivceFeeRule selectByPrimaryKey(SerivceFeeRuleKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERIVCE_FEE_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:58 CST 2010
	 */
	int deleteByExample(SerivceFeeRuleExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERIVCE_FEE_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:58 CST 2010
	 */
	int deleteByPrimaryKey(SerivceFeeRuleKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERIVCE_FEE_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:58 CST 2010
	 */
	int countByExample(SerivceFeeRuleExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERIVCE_FEE_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:58 CST 2010
	 */
	int updateByExampleSelective(SerivceFeeRule record,
			SerivceFeeRuleExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_SERIVCE_FEE_RULE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:58 CST 2010
	 */
	int updateByExample(SerivceFeeRule record, SerivceFeeRuleExample example);
}