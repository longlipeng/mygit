package com.allinfinance.shangqi.gateway.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.shangqi.gateway.dto.ApplyAndBindCardDTO;
import com.huateng.framework.ibatis.dao.EntityStockDAOImpl;
import com.huateng.framework.ibatis.model.Cardholder;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.EntityStockExample;
import com.huateng.framework.ibatis.model.EntityStockExample.Criteria;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.ibm.db2.jcc.am.SqlException;

public class ApplyAndBindDaoImpl  extends SqlMapClientDaoSupport implements ApplyAndBindDao{

	@Override
	public int StockCardCountQuery(String statement,ApplyAndBindCardDTO stockCountDTO) throws SQLException {
		
		Object  strCount= getSqlMapClient().queryForObject(statement, stockCountDTO);
		logger.info("strCount is"+strCount+"-------------"+strCount.getClass());
		/*EntityStockDAOImpl entry = new EntityStockDAOImpl();
		EntityStockExample example = new EntityStockExample();
		Criteria criteria = example.createCriteria();
		criteria.andProductIdEqualTo(stockCountDTO.getProductId());
		criteria.andEntityIdEqualTo(stockCountDTO.getEntityId());
		criteria.andStockStateEqualTo(stockCountDTO.getStockStatus());*/
		return	(int)(Integer)strCount;
	}

	@Override
	public List<EntityStock> getStockCards(String statement, ApplyAndBindCardDTO stockCountDTO)
			throws SQLException {
		List<EntityStock> list = getSqlMapClient().queryForList(statement, stockCountDTO);
		return list;
	}

	@Override
	public List<ApplyAndBindCardDTO> getCardHolderMsg(String statement,
			ApplyAndBindCardDTO stockCountDTO) throws SQLException {
		// TODO Auto-generated method stub
		List<ApplyAndBindCardDTO> list =getSqlMapClient().queryForList(statement, stockCountDTO);
		return list;
	}

	@Override
	public ApplyAndBindCardDTO getApplyAndBindCard(String statement,
			ApplyAndBindCardDTO stockCountDTO) throws SQLException {
		ApplyAndBindCardDTO dto=null;
		Object object = getSqlMapClientTemplate().queryForObject(statement, stockCountDTO);
		if(object!=null){
			dto =(ApplyAndBindCardDTO)object;
		}
		return dto;
	}

	@Override
	public List<ApplyAndBindCardDTO> getApplyAndBindCardDTOList(
			String statement, ApplyAndBindCardDTO stockCountDTO)
			throws SQLException {
		List<ApplyAndBindCardDTO> list = getSqlMapClientTemplate().queryForList(statement, stockCountDTO);
		return list;
	}

	@Override
	public int updateApplyAndBindCardMsg(String statement,
			ApplyAndBindCardDTO stockCountDTO) throws SQLException {
		int rows = getSqlMapClientTemplate().update(statement, stockCountDTO);
		return rows;
	}

	@Override
	public Object insertCardHolderMsg(String statement,
			Cardholder stockCountDTO) throws SQLException {
		// TODO Auto-generated method stub
		
		Object object = getSqlMapClientTemplate().insert(statement, stockCountDTO);
		
		return object;
	}

	@Override
	public Object insertSellOrderMsg(String statement,SellOrder stockCountDTO) throws SQLException {
		// TODO Auto-generated method stub
		Object object = getSqlMapClientTemplate().insert(statement, stockCountDTO);
		return object;
	}

	@Override
	public Object insertSellOrderCardList(String statement,SellOrderCardList stockCountDTO) throws SqlException {
		// TODO Auto-generated method stub
		Object object = getSqlMapClientTemplate().insert(statement, stockCountDTO);
		return object;
	}

	@Override
	public Object insertApplyCard(String statement,
			ApplyAndBindCardDTO stockCountDTO) throws SQLException {
		// TODO Auto-generated method stub
		Object object = getSqlMapClientTemplate().insert(statement, stockCountDTO);
		return object;
	}

	@Override
	public int insertOrderFlow(String statement,
			ApplyAndBindCardDTO stockCountDTO) throws SQLException {
		// TODO Auto-generated method stub
		Object object = getSqlMapClientTemplate().insert(statement, stockCountDTO);
		return 0;
	}

	@Override
	public int update(String statement, ApplyAndBindCardDTO stockCountDTO)
			throws SQLException {
		int update = getSqlMapClientTemplate().update(statement, stockCountDTO);
		return update;
	}

	@Override
	public String getID(String statement, ApplyAndBindCardDTO stockCountDTO)
			throws SQLException {
		String id=null; 
	    Object  object= getSqlMapClientTemplate().queryForObject(statement, stockCountDTO);
	    if(object!=null)
	    	id=(String)object;
		return id;
	}
	
	
}
