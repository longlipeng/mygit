package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.UnionOrderExample;
import com.huateng.framework.db.ibatis.model.UnionOrderKey;
import java.util.List;
import com.huateng.framework.db.ibatis.model.UnionOrder;

public interface UnionOrderDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	void insert(UnionOrder record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	int updateByPrimaryKey(UnionOrder record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	int updateByPrimaryKeySelective(UnionOrder record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	List<UnionOrder> selectByExample(UnionOrderExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	UnionOrder selectByPrimaryKey(UnionOrderKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	int deleteByExample(UnionOrderExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	int deleteByPrimaryKey(UnionOrderKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	int countByExample(UnionOrderExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	int updateByExampleSelective(UnionOrder record, UnionOrderExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_UNION_ORDER
	 * 
	 * @abatorgenerated Mon Dec 13 17:12:34 CST 2010
	 */
	int updateByExample(UnionOrder record, UnionOrderExample example);
}