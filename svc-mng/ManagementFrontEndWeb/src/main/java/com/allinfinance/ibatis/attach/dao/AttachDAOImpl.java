package com.allinfinance.ibatis.attach.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.univer.seller.cardholder.dto.AttachInfoDTO;

public class AttachDAOImpl  extends SqlMapClientDaoSupport implements AttachDAO  {

	@Override
	public List<AttachInfoDTO> getAttachInfos(AttachInfoDTO attachInfoDTO) throws SQLException {
			List queryForList = getSqlMapClientTemplate().queryForList("Attach_info.Attach_info_select", attachInfoDTO);
		return queryForList;
	}

	@Override
	public void updateAttachInfo(AttachInfoDTO attachInfoDTO) throws SQLException {
		int update =getSqlMapClientTemplate().update("Attach_info.Attach_info_update", attachInfoDTO);
		
	}

	@Override
	public void insertAttachInfo(AttachInfoDTO attachInfoDTO) throws SQLException {
		getSqlMapClientTemplate().insert("Attach_info.Attach_info_insert", attachInfoDTO);
		
	}

}
