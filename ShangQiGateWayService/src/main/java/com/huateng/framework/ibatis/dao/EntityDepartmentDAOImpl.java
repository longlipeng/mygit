package com.huateng.framework.ibatis.dao;

import com.huateng.framework.ibatis.model.EntityDepartment;
import com.huateng.framework.ibatis.model.EntityDepartmentExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class EntityDepartmentDAOImpl extends SqlMapClientDaoSupport implements
		EntityDepartmentDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public EntityDepartmentDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public void insert(EntityDepartment record) {
		getSqlMapClientTemplate().insert(
				"TB_ENTITY_DEPARTMENT.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public int updateByPrimaryKey(EntityDepartment record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_ENTITY_DEPARTMENT.abatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public int updateByPrimaryKeySelective(EntityDepartment record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_ENTITY_DEPARTMENT.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	@SuppressWarnings("unchecked")
	public List<EntityDepartment> selectByExample(
			EntityDepartmentExample example) {
		List<EntityDepartment> list = (List<EntityDepartment>) getSqlMapClientTemplate()
				.queryForList(
						"TB_ENTITY_DEPARTMENT.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public EntityDepartment selectByPrimaryKey(String departmentId) {
		EntityDepartment key = new EntityDepartment();
		key.setDepartmentId(departmentId);
		EntityDepartment record = (EntityDepartment) getSqlMapClientTemplate()
				.queryForObject(
						"TB_ENTITY_DEPARTMENT.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public int deleteByExample(EntityDepartmentExample example) {
		int rows = getSqlMapClientTemplate()
				.delete("TB_ENTITY_DEPARTMENT.abatorgenerated_deleteByExample",
						example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public int deleteByPrimaryKey(String departmentId) {
		EntityDepartment key = new EntityDepartment();
		key.setDepartmentId(departmentId);
		int rows = getSqlMapClientTemplate().delete(
				"TB_ENTITY_DEPARTMENT.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public int countByExample(EntityDepartmentExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_ENTITY_DEPARTMENT.abatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public int updateByExampleSelective(EntityDepartment record,
			EntityDepartmentExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_ENTITY_DEPARTMENT.abatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	public int updateByExample(EntityDepartment record,
			EntityDepartmentExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_ENTITY_DEPARTMENT.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_ENTITY_DEPARTMENT
	 * 
	 * @abatorgenerated Sun Sep 26 15:55:40 CST 2010
	 */
	private static class UpdateByExampleParms extends EntityDepartmentExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				EntityDepartmentExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}