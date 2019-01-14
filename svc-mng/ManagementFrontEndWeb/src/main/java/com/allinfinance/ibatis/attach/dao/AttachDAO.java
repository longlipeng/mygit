package com.allinfinance.ibatis.attach.dao;

import java.sql.SQLException;
import java.util.List;

import com.allinfinance.univer.seller.cardholder.dto.AttachInfoDTO;



public interface AttachDAO {
	public List<AttachInfoDTO> getAttachInfos(AttachInfoDTO attachInfoDTO) throws SQLException;
	public void updateAttachInfo(AttachInfoDTO attachInfoDTO) throws  SQLException;
	public void insertAttachInfo(AttachInfoDTO attachInfoDTO ) throws  SQLException;
}
