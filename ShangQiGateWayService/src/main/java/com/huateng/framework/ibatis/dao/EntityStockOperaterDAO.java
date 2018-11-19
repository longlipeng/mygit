package com.huateng.framework.ibatis.dao;

import com.huateng.framework.ibatis.model.EntityStockOperater;
import com.huateng.framework.ibatis.model.EntityStockOperaterExample;
import java.util.List;

public interface EntityStockOperaterDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK_OPERATER
	 * 
	 * @abatorgenerated Thu Jan 13 17:14:35 CST 2011
	 */
	void insert(EntityStockOperater record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK_OPERATER
	 * 
	 * @abatorgenerated Thu Jan 13 17:14:35 CST 2011
	 */
	int updateByPrimaryKey(EntityStockOperater record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK_OPERATER
	 * 
	 * @abatorgenerated Thu Jan 13 17:14:35 CST 2011
	 */
	int updateByPrimaryKeySelective(EntityStockOperater record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK_OPERATER
	 * 
	 * @abatorgenerated Thu Jan 13 17:14:35 CST 2011
	 */
	List<EntityStockOperater> selectByExample(EntityStockOperaterExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK_OPERATER
	 * 
	 * @abatorgenerated Thu Jan 13 17:14:35 CST 2011
	 */
	EntityStockOperater selectByPrimaryKey(String operaterId);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK_OPERATER
	 * 
	 * @abatorgenerated Thu Jan 13 17:14:35 CST 2011
	 */
	int deleteByExample(EntityStockOperaterExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK_OPERATER
	 * 
	 * @abatorgenerated Thu Jan 13 17:14:35 CST 2011
	 */
	int deleteByPrimaryKey(String operaterId);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK_OPERATER
	 * 
	 * @abatorgenerated Thu Jan 13 17:14:35 CST 2011
	 */
	int countByExample(EntityStockOperaterExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK_OPERATER
	 * 
	 * @abatorgenerated Thu Jan 13 17:14:35 CST 2011
	 */
	int updateByExampleSelective(EntityStockOperater record,
			EntityStockOperaterExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_STOCK_OPERATER
	 * 
	 * @abatorgenerated Thu Jan 13 17:14:35 CST 2011
	 */
	int updateByExample(EntityStockOperater record,
			EntityStockOperaterExample example);
}