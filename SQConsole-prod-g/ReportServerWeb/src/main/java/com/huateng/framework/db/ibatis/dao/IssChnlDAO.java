package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.IssChnl;
import com.huateng.framework.db.ibatis.model.IssChnlExample;
import com.huateng.framework.db.ibatis.model.IssChnlKey;
import java.util.List;

public interface IssChnlDAO {
	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ISS_CHNL
	 * 
	 * @abatorgenerated Mon Feb 20 09:43:01 CST 2012
	 */
	void insert(IssChnl record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ISS_CHNL
	 * 
	 * @abatorgenerated Mon Feb 20 09:43:01 CST 2012
	 */
	int updateByPrimaryKey(IssChnl record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ISS_CHNL
	 * 
	 * @abatorgenerated Mon Feb 20 09:43:01 CST 2012
	 */
	int updateByPrimaryKeySelective(IssChnl record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ISS_CHNL
	 * 
	 * @abatorgenerated Mon Feb 20 09:43:01 CST 2012
	 */
	List<IssChnl> selectByExample(IssChnlExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ISS_CHNL
	 * 
	 * @abatorgenerated Mon Feb 20 09:43:01 CST 2012
	 */
	IssChnl selectByPrimaryKey(IssChnlKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ISS_CHNL
	 * 
	 * @abatorgenerated Mon Feb 20 09:43:01 CST 2012
	 */
	int deleteByExample(IssChnlExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ISS_CHNL
	 * 
	 * @abatorgenerated Mon Feb 20 09:43:01 CST 2012
	 */
	int deleteByPrimaryKey(IssChnlKey key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ISS_CHNL
	 * 
	 * @abatorgenerated Mon Feb 20 09:43:01 CST 2012
	 */
	int countByExample(IssChnlExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ISS_CHNL
	 * 
	 * @abatorgenerated Mon Feb 20 09:43:01 CST 2012
	 */
	int updateByExampleSelective(IssChnl record, IssChnlExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ISS_CHNL
	 * 
	 * @abatorgenerated Mon Feb 20 09:43:01 CST 2012
	 */
	int updateByExample(IssChnl record, IssChnlExample example);
}