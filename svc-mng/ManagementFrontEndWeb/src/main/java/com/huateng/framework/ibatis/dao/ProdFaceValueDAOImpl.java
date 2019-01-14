package com.huateng.framework.ibatis.dao;

import com.huateng.framework.ibatis.model.ProdFaceValue;
import com.huateng.framework.ibatis.model.ProdFaceValueExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class ProdFaceValueDAOImpl extends SqlMapClientDaoSupport implements
		ProdFaceValueDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PROD_FACE_VALUE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:17 CST 2010
	 */
	public ProdFaceValueDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PROD_FACE_VALUE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:17 CST 2010
	 */
	public void insert(ProdFaceValue record) {
		getSqlMapClientTemplate().insert(
				"TB_PROD_FACE_VALUE.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PROD_FACE_VALUE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:17 CST 2010
	 */
	public int updateByPrimaryKey(ProdFaceValue record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_PROD_FACE_VALUE.abatorgenerated_updateByPrimaryKey",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PROD_FACE_VALUE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:17 CST 2010
	 */
	public int updateByPrimaryKeySelective(ProdFaceValue record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_PROD_FACE_VALUE.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PROD_FACE_VALUE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:17 CST 2010
	 */
	@SuppressWarnings("unchecked")
	public List<ProdFaceValue> selectByExample(ProdFaceValueExample example) {
		List<ProdFaceValue> list = (List<ProdFaceValue>) getSqlMapClientTemplate()
				.queryForList(
						"TB_PROD_FACE_VALUE.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PROD_FACE_VALUE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:17 CST 2010
	 */
	public ProdFaceValue selectByPrimaryKey(String faceValueId) {
		ProdFaceValue key = new ProdFaceValue();
		key.setFaceValueId(faceValueId);
		ProdFaceValue record = (ProdFaceValue) getSqlMapClientTemplate()
				.queryForObject(
						"TB_PROD_FACE_VALUE.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PROD_FACE_VALUE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:17 CST 2010
	 */
	public int deleteByExample(ProdFaceValueExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_PROD_FACE_VALUE.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PROD_FACE_VALUE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:17 CST 2010
	 */
	public int deleteByPrimaryKey(String faceValueId) {
		ProdFaceValue key = new ProdFaceValue();
		key.setFaceValueId(faceValueId);
		int rows = getSqlMapClientTemplate().delete(
				"TB_PROD_FACE_VALUE.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PROD_FACE_VALUE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:17 CST 2010
	 */
	public int countByExample(ProdFaceValueExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_PROD_FACE_VALUE.abatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PROD_FACE_VALUE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:17 CST 2010
	 */
	public int updateByExampleSelective(ProdFaceValue record,
			ProdFaceValueExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_PROD_FACE_VALUE.abatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PROD_FACE_VALUE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:17 CST 2010
	 */
	public int updateByExample(ProdFaceValue record,
			ProdFaceValueExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_PROD_FACE_VALUE.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_PROD_FACE_VALUE
	 * 
	 * @abatorgenerated Sun Sep 26 15:56:17 CST 2010
	 */
	private static class UpdateByExampleParms extends ProdFaceValueExample {
		private Object record;

		public UpdateByExampleParms(Object record, ProdFaceValueExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}