package com.huateng.framework.ibatis.dao;

import com.huateng.framework.ibatis.model.EntityDictType;
import com.huateng.framework.ibatis.model.EntityDictTypeExample;
import com.huateng.framework.ibatis.model.EntityDictTypeKey;
import java.util.List;

public interface EntityDictTypeDAO {
	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_TYPE
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:42 CST 2010
	 */
	void insert(EntityDictType record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_TYPE
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:42 CST 2010
	 */
	int updateByPrimaryKey(EntityDictType record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_TYPE
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:42 CST 2010
	 */
	int updateByPrimaryKeySelective(EntityDictType record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_TYPE
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:42 CST 2010
	 */
	List<EntityDictType> selectByExample(EntityDictTypeExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_TYPE
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:42 CST 2010
	 */
	EntityDictType selectByPrimaryKey(EntityDictTypeKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_TYPE
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:42 CST 2010
	 */
	int deleteByExample(EntityDictTypeExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_TYPE
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:42 CST 2010
	 */
	int deleteByPrimaryKey(EntityDictTypeKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_TYPE
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:42 CST 2010
	 */
	int countByExample(EntityDictTypeExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_TYPE
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:42 CST 2010
	 */
	int updateByExampleSelective(EntityDictType record,
			EntityDictTypeExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DICT_TYPE
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:42 CST 2010
	 */
	int updateByExample(EntityDictType record, EntityDictTypeExample example);
}