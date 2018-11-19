package com.allinfinance.prepay.service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.univer.entity.dto.DepartmentDTO;

public interface DepartmentService {
	public void insert(DepartmentDTO departmentDTO)throws BizServiceException;

}
