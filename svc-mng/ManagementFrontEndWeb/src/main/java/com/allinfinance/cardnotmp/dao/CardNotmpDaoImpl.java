package com.allinfinance.cardnotmp.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.service.uploadFile.dto.UploadCardFileDTO;

public class CardNotmpDaoImpl extends SqlMapClientDaoSupport implements CardNotmpDao {

	@Override
	public void insert(UploadCardFileDTO dto) throws Exception{
		this.getSqlMapClientTemplate().insert("TB_CARDNOS_TMP.insertCardNo", dto);
		
	}

	@Override
	public void delete(UploadCardFileDTO dto) throws Exception{
		this.getSqlMapClientTemplate().delete("TB_CARDNOS_TMP.deleteCardNos", dto);
		
	}

	@Override
	public List<UploadCardFileDTO> select(UploadCardFileDTO dto)  throws Exception{
		List<UploadCardFileDTO > list = (List<UploadCardFileDTO >)this.getSqlMapClientTemplate().queryForList("TB_CARDNOS_TMP.selectCardNos", dto);
		return list;
	}

	@Override
	public void deleteAll(UploadCardFileDTO dto) throws Exception {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().delete("TB_CARDNOS_TMP.deleteAll", dto);
	}
	

}
