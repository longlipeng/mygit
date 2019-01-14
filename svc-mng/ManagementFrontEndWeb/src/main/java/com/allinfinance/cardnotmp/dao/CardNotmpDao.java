package com.allinfinance.cardnotmp.dao;

import java.util.List;

import com.allinfinance.service.uploadFile.dto.UploadCardFileDTO;

public interface CardNotmpDao {
	public void insert(UploadCardFileDTO dto) throws Exception;
	public void delete(UploadCardFileDTO dto) throws Exception;
	public List<UploadCardFileDTO> select(UploadCardFileDTO dto) throws Exception;
	public void deleteAll(UploadCardFileDTO dto) throws Exception;
}
