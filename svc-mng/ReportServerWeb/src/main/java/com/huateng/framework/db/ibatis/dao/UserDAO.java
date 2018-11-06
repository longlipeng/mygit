package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.User;
import com.huateng.framework.db.ibatis.model.UserExample;
import java.util.List;

public interface UserDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENT_USER
	 * 
	 * @abatorgenerated Sat Mar 24 15:57:26 CST 2012
	 */
	void insert(User record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENT_USER
	 * 
	 * @abatorgenerated Sat Mar 24 15:57:26 CST 2012
	 */
	int updateByPrimaryKey(User record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENT_USER
	 * 
	 * @abatorgenerated Sat Mar 24 15:57:26 CST 2012
	 */
	int updateByPrimaryKeySelective(User record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENT_USER
	 * 
	 * @abatorgenerated Sat Mar 24 15:57:26 CST 2012
	 */
	List<User> selectByExample(UserExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENT_USER
	 * 
	 * @abatorgenerated Sat Mar 24 15:57:26 CST 2012
	 */
	User selectByPrimaryKey(String userId);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENT_USER
	 * 
	 * @abatorgenerated Sat Mar 24 15:57:26 CST 2012
	 */
	int deleteByExample(UserExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENT_USER
	 * 
	 * @abatorgenerated Sat Mar 24 15:57:26 CST 2012
	 */
	int deleteByPrimaryKey(String userId);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENT_USER
	 * 
	 * @abatorgenerated Sat Mar 24 15:57:26 CST 2012
	 */
	int countByExample(UserExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENT_USER
	 * 
	 * @abatorgenerated Sat Mar 24 15:57:26 CST 2012
	 */
	int updateByExampleSelective(User record, UserExample example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENT_USER
	 * 
	 * @abatorgenerated Sat Mar 24 15:57:26 CST 2012
	 */
	int updateByExample(User record, UserExample example);
}