package com.huateng.univer.entitybaseinfo.department.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.EntityDepartmentDAO;
import com.huateng.framework.ibatis.model.EntityDepartment;
import com.huateng.framework.ibatis.model.EntityDepartmentExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitybaseinfo.department.biz.service.DepartmentService;

/**
 * 部门service
 * 
 * @author xxl
 * 
 */
public class DepartmentServiceImpl implements DepartmentService {

	Logger logger = Logger.getLogger(this.getClass());
	private CommonsDAO commonsDAO;
	private EntityDepartmentDAO departmentDAO;

	public List<DepartmentDTO> inquery(String entityId)
			throws BizServiceException {
		try {
			EntityDepartmentExample example = new EntityDepartmentExample();
			example.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);

			List<EntityDepartment> list = departmentDAO
					.selectByExample(example);
			List<DepartmentDTO> dtoList = new ArrayList<DepartmentDTO>();

			for (EntityDepartment e : list) {
				DepartmentDTO dto = new DepartmentDTO();
				ReflectionUtil.copyProperties(e, dto);
				dtoList.add(dto);
			}

			return dtoList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询部门信息失败！");
		}
	}

	private void updateDefaultFlag(DepartmentDTO departmentDTO) {
		if ("1".equals(departmentDTO.getDefaultFlag())) {
			EntityDepartmentExample example = new EntityDepartmentExample();
			example.createCriteria().andEntityIdEqualTo(
					departmentDTO.getEntityId()).andDefaultFlagEqualTo("1")
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);

			EntityDepartment department = new EntityDepartment();
			department.setDefaultFlag("0");

			departmentDAO.updateByExampleSelective(department, example);
		}
	}

	public void insert(DepartmentDTO departmentDTO) throws BizServiceException {
		try {

			this.updateDefaultFlag(departmentDTO);

			EntityDepartment entityDepartment = new EntityDepartment();
			ReflectionUtil.copyProperties(departmentDTO, entityDepartment);
			String id = commonsDAO
					.getNextValueOfSequence("TB_ENTITY_DEPARTMENT");
			entityDepartment.setDepartmentId(id);
			entityDepartment.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			entityDepartment.setCreateTime(DateUtil.getCurrentTime());
			entityDepartment.setModifyTime(DateUtil.getCurrentTime());

			departmentDAO.insert(entityDepartment);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("失败！");
		}
	}

	public void delete(DepartmentDTO departmentDTO) throws BizServiceException {
		try {
			List<DepartmentDTO> dtoList = departmentDTO.getDepartmentDTO();
			List<EntityDepartment> departmentList = new ArrayList<EntityDepartment>();
			for (DepartmentDTO dto : dtoList) {
				EntityDepartment department = new EntityDepartment();
				ReflectionUtil.copyProperties(dto, department);
				department.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				department.setModifyUser(departmentDTO.getLoginUserId());
				department.setModifyTime(DateUtil.getCurrentTime());

				departmentList.add(department);
			}

			commonsDAO
					.batchUpdate(
							"TB_ENTITY_DEPARTMENT.abatorgenerated_updateByPrimaryKeySelective",
							departmentList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除部门失败！");
		}
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public EntityDepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	public void setDepartmentDAO(EntityDepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

}
