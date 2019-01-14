package com.allinfinance.prepay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.DepartmentMapper;
import com.allinfinance.prepay.model.Department;
import com.allinfinance.prepay.model.DepartmentExample;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
/**
 * 部门信息
 * @author 
 *
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Override
	public void insert(DepartmentDTO departmentDTO) throws BizServiceException {
		// TODO Auto-generated method stub
		this.updateDefaultFlag(departmentDTO);

		Department entityDepartment = new Department();
		ReflectionUtil.copyProperties(departmentDTO, entityDepartment);
		String id = commonsDAO
				.getNextValueOfSequence("TB_ENTITY_DEPARTMENT");
		entityDepartment.setDepartmentId(id);
		entityDepartment.setDataState("1");
		entityDepartment.setCreateTime(DateUtil.getCurrentTime());
		entityDepartment.setModifyTime(DateUtil.getCurrentTime());

		departmentMapper.insert(entityDepartment);
	}
	
	private void updateDefaultFlag(DepartmentDTO departmentDTO) {
		if ("1".equals(departmentDTO.getDefaultFlag())) {
			DepartmentExample example = new DepartmentExample();
			example.createCriteria().andEntityIdEqualTo(
					departmentDTO.getEntityId()).andDefaultFlagEqualTo("1")
					.andDataStateEqualTo("1");

			Department department = new Department();
			department.setDefaultFlag("0");

			departmentMapper.updateByExampleSelective(department, example);
		}
	}

}
