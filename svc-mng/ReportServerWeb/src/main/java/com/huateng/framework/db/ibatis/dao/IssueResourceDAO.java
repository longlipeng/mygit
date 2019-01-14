package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.IssueResource;
import com.huateng.framework.db.ibatis.model.IssueResourceExample;
import com.huateng.framework.db.ibatis.model.IssueResourceKey;
import java.util.List;

public interface IssueResourceDAO {
	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ISSUE_RESOURCE
	 * 
	 * @abatorgenerated Tue Jul 26 14:38:30 CST 2011
	 */
	void insert(IssueResource record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ISSUE_RESOURCE
	 * 
	 * @abatorgenerated Tue Jul 26 14:38:30 CST 2011
	 */
	int updateByPrimaryKey(IssueResource record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ISSUE_RESOURCE
	 * 
	 * @abatorgenerated Tue Jul 26 14:38:30 CST 2011
	 */
	int updateByPrimaryKeySelective(IssueResource record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ISSUE_RESOURCE
	 * 
	 * @abatorgenerated Tue Jul 26 14:38:30 CST 2011
	 */
	List<IssueResource> selectByExample(IssueResourceExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ISSUE_RESOURCE
	 * 
	 * @abatorgenerated Tue Jul 26 14:38:30 CST 2011
	 */
	IssueResource selectByPrimaryKey(IssueResourceKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ISSUE_RESOURCE
	 * 
	 * @abatorgenerated Tue Jul 26 14:38:30 CST 2011
	 */
	int deleteByExample(IssueResourceExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ISSUE_RESOURCE
	 * 
	 * @abatorgenerated Tue Jul 26 14:38:30 CST 2011
	 */
	int deleteByPrimaryKey(IssueResourceKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ISSUE_RESOURCE
	 * 
	 * @abatorgenerated Tue Jul 26 14:38:30 CST 2011
	 */
	int countByExample(IssueResourceExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ISSUE_RESOURCE
	 * 
	 * @abatorgenerated Tue Jul 26 14:38:30 CST 2011
	 */
	int updateByExampleSelective(IssueResource record,
			IssueResourceExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_ISSUE_RESOURCE
	 * 
	 * @abatorgenerated Tue Jul 26 14:38:30 CST 2011
	 */
	int updateByExample(IssueResource record, IssueResourceExample example);
}