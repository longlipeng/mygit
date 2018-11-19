package com.huateng.univer.entitybaseinfo.department.biz.service;

import java.util.List;

import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.huateng.framework.exception.BizServiceException;

public interface DepartmentService {
	public List<DepartmentDTO> inquery(String entityId)
			throws BizServiceException;

	public void insert(DepartmentDTO departmentDTO) throws BizServiceException;

	public void delete(DepartmentDTO departmentDTO) throws BizServiceException;
}
