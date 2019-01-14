package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.ProdLayout;
import com.huateng.framework.db.ibatis.model.ProdLayoutExample;
import java.util.List;

public interface ProdLayoutDAO {
	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	void insert(ProdLayout record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	int updateByPrimaryKey(ProdLayout record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	int updateByPrimaryKeySelective(ProdLayout record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	List<ProdLayout> selectByExample(ProdLayoutExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	ProdLayout selectByPrimaryKey(String relId);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	int deleteByExample(ProdLayoutExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	int deleteByPrimaryKey(String relId);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	int countByExample(ProdLayoutExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	int updateByExampleSelective(ProdLayout record, ProdLayoutExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_REL_PROD_LAYOUT
	 * 
	 * @abatorgenerated Thu Nov 11 17:44:36 CST 2010
	 */
	int updateByExample(ProdLayout record, ProdLayoutExample example);
}