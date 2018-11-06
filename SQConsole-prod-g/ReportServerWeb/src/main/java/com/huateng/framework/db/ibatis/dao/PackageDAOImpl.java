package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.Package;
import com.huateng.framework.db.ibatis.model.PackageExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class PackageDAOImpl extends SqlMapClientDaoSupport implements
		PackageDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:15 CST 2010
	 */
	public PackageDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:15 CST 2010
	 */
	public void insert(Package record) {
		getSqlMapClientTemplate().insert("TB_PACKAGE.abatorgenerated_insert",
				record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:15 CST 2010
	 */
	public int updateByPrimaryKey(Package record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_PACKAGE.abatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:15 CST 2010
	 */
	public int updateByPrimaryKeySelective(Package record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_PACKAGE.abatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:15 CST 2010
	 */
	@SuppressWarnings("unchecked")
	public List<Package> selectByExample(PackageExample example) {
		List<Package> list = (List<Package>) getSqlMapClientTemplate()
				.queryForList("TB_PACKAGE.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:15 CST 2010
	 */
	public Package selectByPrimaryKey(String packageId) {
		Package key = new Package();
		key.setPackageId(packageId);
		Package record = (Package) getSqlMapClientTemplate().queryForObject(
				"TB_PACKAGE.abatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:15 CST 2010
	 */
	public int deleteByExample(PackageExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_PACKAGE.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:15 CST 2010
	 */
	public int deleteByPrimaryKey(String packageId) {
		Package key = new Package();
		key.setPackageId(packageId);
		int rows = getSqlMapClientTemplate().delete(
				"TB_PACKAGE.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:15 CST 2010
	 */
	public int countByExample(PackageExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_PACKAGE.abatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:15 CST 2010
	 */
	public int updateByExampleSelective(Package record, PackageExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_PACKAGE.abatorgenerated_updateByExampleSelective", parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:15 CST 2010
	 */
	public int updateByExample(Package record, PackageExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_PACKAGE.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_PACKAGE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:15 CST 2010
	 */
	private static class UpdateByExampleParms extends PackageExample {
		private Object record;

		public UpdateByExampleParms(Object record, PackageExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}