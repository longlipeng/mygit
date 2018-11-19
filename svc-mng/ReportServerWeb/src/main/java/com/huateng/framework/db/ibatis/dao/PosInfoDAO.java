package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.PosInfo;
import com.huateng.framework.db.ibatis.model.PosInfoExample;
import com.huateng.framework.db.ibatis.model.PosInfoKey;
import java.util.List;

public interface PosInfoDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table COMDB.TB_POS_INF
	 * 
	 * @abatorgenerated Wed Feb 22 11:08:17 CST 2012
	 */
	void insert(PosInfo record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table COMDB.TB_POS_INF
	 * 
	 * @abatorgenerated Wed Feb 22 11:08:17 CST 2012
	 */
	int updateByPrimaryKey(PosInfo record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table COMDB.TB_POS_INF
	 * 
	 * @abatorgenerated Wed Feb 22 11:08:17 CST 2012
	 */
	int updateByPrimaryKeySelective(PosInfo record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table COMDB.TB_POS_INF
	 * 
	 * @abatorgenerated Wed Feb 22 11:08:17 CST 2012
	 */
	List<PosInfo> selectByExample(PosInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table COMDB.TB_POS_INF
	 * 
	 * @abatorgenerated Wed Feb 22 11:08:17 CST 2012
	 */
	PosInfo selectByPrimaryKey(PosInfoKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table COMDB.TB_POS_INF
	 * 
	 * @abatorgenerated Wed Feb 22 11:08:17 CST 2012
	 */
	int deleteByExample(PosInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table COMDB.TB_POS_INF
	 * 
	 * @abatorgenerated Wed Feb 22 11:08:17 CST 2012
	 */
	int deleteByPrimaryKey(PosInfoKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table COMDB.TB_POS_INF
	 * 
	 * @abatorgenerated Wed Feb 22 11:08:17 CST 2012
	 */
	int countByExample(PosInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table COMDB.TB_POS_INF
	 * 
	 * @abatorgenerated Wed Feb 22 11:08:17 CST 2012
	 */
	int updateByExampleSelective(PosInfo record, PosInfoExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table COMDB.TB_POS_INF
	 * 
	 * @abatorgenerated Wed Feb 22 11:08:17 CST 2012
	 */
	int updateByExample(PosInfo record, PosInfoExample example);
}