package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.PosParamenter;
import com.huateng.framework.db.ibatis.model.PosParamenterExample;
import com.huateng.framework.db.ibatis.model.PosParamenterKey;
import java.util.List;

public interface PosParamenterDAO {
	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_PARAMETER
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	void insert(PosParamenter record);

	void insert_default(PosParamenter record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_PARAMETER
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	int updateByPrimaryKey(PosParamenter record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_PARAMETER
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	int updateByPrimaryKeySelective(PosParamenter record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_PARAMETER
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	List<PosParamenter> selectByExample(PosParamenterExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_PARAMETER
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	PosParamenter selectByPrimaryKey(PosParamenterKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_PARAMETER
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	int deleteByExample(PosParamenterExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_PARAMETER
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	int deleteByPrimaryKey(PosParamenterKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_PARAMETER
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	int countByExample(PosParamenterExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_PARAMETER
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	int updateByExampleSelective(PosParamenter record,
			PosParamenterExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_POS_PARAMETER
	 * 
	 * @abatorgenerated Mon Nov 08 16:52:40 CST 2010
	 */
	int updateByExample(PosParamenter record, PosParamenterExample example);
}