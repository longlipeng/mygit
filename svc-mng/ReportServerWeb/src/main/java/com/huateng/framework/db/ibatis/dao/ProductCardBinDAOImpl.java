package com.huateng.framework.db.ibatis.dao;

import com.huateng.framework.db.ibatis.model.ProductCardBin;
import com.huateng.framework.db.ibatis.model.ProductCardBinExample;
import com.huateng.framework.db.ibatis.model.ProductCardBinKey;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class ProductCardBinDAOImpl extends SqlMapClientDaoSupport implements
		ProductCardBinDAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PRODUCT_CARD_BIN
	 * 
	 * @abatorgenerated Fri Mar 04 11:28:35 CST 2011
	 */
	public ProductCardBinDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PRODUCT_CARD_BIN
	 * 
	 * @abatorgenerated Fri Mar 04 11:28:35 CST 2011
	 */
	public void insert(ProductCardBin record) {
		getSqlMapClientTemplate().insert(
				"TB_PRODUCT_CARD_BIN.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PRODUCT_CARD_BIN
	 * 
	 * @abatorgenerated Fri Mar 04 11:28:35 CST 2011
	 */
	public int updateByPrimaryKey(ProductCardBin record) {
		int rows = getSqlMapClientTemplate().update(
				"TB_PRODUCT_CARD_BIN.abatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PRODUCT_CARD_BIN
	 * 
	 * @abatorgenerated Fri Mar 04 11:28:35 CST 2011
	 */
	public int updateByPrimaryKeySelective(ProductCardBin record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"TB_PRODUCT_CARD_BIN.abatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PRODUCT_CARD_BIN
	 * 
	 * @abatorgenerated Fri Mar 04 11:28:35 CST 2011
	 */
	@SuppressWarnings("unchecked")
	public List<ProductCardBin> selectByExample(ProductCardBinExample example) {
		List<ProductCardBin> list = (List<ProductCardBin>) getSqlMapClientTemplate()
				.queryForList(
						"TB_PRODUCT_CARD_BIN.abatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PRODUCT_CARD_BIN
	 * 
	 * @abatorgenerated Fri Mar 04 11:28:35 CST 2011
	 */
	public ProductCardBin selectByPrimaryKey(ProductCardBinKey key) {
		ProductCardBin record = (ProductCardBin) getSqlMapClientTemplate()
				.queryForObject(
						"TB_PRODUCT_CARD_BIN.abatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PRODUCT_CARD_BIN
	 * 
	 * @abatorgenerated Fri Mar 04 11:28:35 CST 2011
	 */
	public int deleteByExample(ProductCardBinExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_PRODUCT_CARD_BIN.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PRODUCT_CARD_BIN
	 * 
	 * @abatorgenerated Fri Mar 04 11:28:35 CST 2011
	 */
	public int deleteByPrimaryKey(ProductCardBinKey key) {
		int rows = getSqlMapClientTemplate().delete(
				"TB_PRODUCT_CARD_BIN.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PRODUCT_CARD_BIN
	 * 
	 * @abatorgenerated Fri Mar 04 11:28:35 CST 2011
	 */
	public int countByExample(ProductCardBinExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"TB_PRODUCT_CARD_BIN.abatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PRODUCT_CARD_BIN
	 * 
	 * @abatorgenerated Fri Mar 04 11:28:35 CST 2011
	 */
	public int updateByExampleSelective(ProductCardBin record,
			ProductCardBinExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_PRODUCT_CARD_BIN.abatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds
	 * to the database table TB_PRODUCT_CARD_BIN
	 * 
	 * @abatorgenerated Fri Mar 04 11:28:35 CST 2011
	 */
	public int updateByExample(ProductCardBin record,
			ProductCardBinExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"TB_PRODUCT_CARD_BIN.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to
	 * the database table TB_PRODUCT_CARD_BIN
	 * 
	 * @abatorgenerated Fri Mar 04 11:28:35 CST 2011
	 */
	private static class UpdateByExampleParms extends ProductCardBinExample {
		private Object record;

		public UpdateByExampleParms(Object record, ProductCardBinExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}