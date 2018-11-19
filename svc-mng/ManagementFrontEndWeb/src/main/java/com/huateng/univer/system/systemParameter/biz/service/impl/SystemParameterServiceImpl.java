package com.huateng.univer.system.systemParameter.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.sysparam.dto.SystemParameterDTO;
import com.allinfinance.univer.system.sysparam.dto.SystemParameterQueryDTO;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SystemParameterDAO;
import com.huateng.framework.ibatis.model.SystemParameter;
import com.huateng.framework.ibatis.model.SystemParameterExample;
import com.huateng.framework.servlet.SystemInfoBO;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.system.systemParameter.biz.service.SystemParameterService;

public class SystemParameterServiceImpl implements SystemParameterService {
	Logger logger = Logger.getLogger(SystemParameterServiceImpl.class);
	/**
	 * 查询分页DAO
	 */
	private PageQueryDAO pageQueryDAO;
	private BaseDAO systemParameterDAO;
	private SystemParameterDAO sysParameterDAO;

	private SystemInfoBO systemInfoBO;

	public SystemInfoBO getSystemInfoBO() {
		return systemInfoBO;
	}

	public void setSystemInfoBO(SystemInfoBO systemInfoBO) {
		this.systemInfoBO = systemInfoBO;
	}

	public List<SystemParameterDTO> getSystemParameter() {

		List<SystemParameterDTO> systemParameterDTOs = new ArrayList<SystemParameterDTO>();

		SystemParameterExample systemParameterExample = new SystemParameterExample();

		List<?> systemParameters = systemParameterDAO
				.selectByExample(systemParameterExample);

		for (int i = 0; i < systemParameters.size(); i++) {
			SystemParameter systemParameter = (SystemParameter) systemParameters
					.get(i);
			SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
			ReflectionUtil.copyProperties(systemParameter, systemParameterDTO);
			systemParameterDTOs.add(systemParameterDTO);
		}
		return systemParameterDTOs;
	}

	public PageDataDTO inquerySystemParamter(
			SystemParameterQueryDTO systemParameterQueryDTO)
			throws BizServiceException {
		try {
			PageDataDTO pdd = pageQueryDAO.query(
					"SYSTEMPARAMETER.selectSystemParameterByDTO",
					systemParameterQueryDTO);
			return pdd;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询系统参数失败!");
		}
	}

	public SystemParameterDTO viewSystemParamter(
			SystemParameterDTO systemParameterDTO) throws BizServiceException {
		try {
			SystemParameter systemParameter = sysParameterDAO
					.selectByPrimaryKey(systemParameterDTO.getParameterCode());
			ReflectionUtil.copyProperties(systemParameter, systemParameterDTO);
			return systemParameterDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询系统参数信息失败!");
		}
	}

	public void updateSystemParamter(SystemParameterDTO systemParameterDTO)
			throws BizServiceException {
		try {
			SystemParameter sysParameter = sysParameterDAO
					.selectByPrimaryKey(systemParameterDTO.getParameterCode());
			ReflectionUtil.copyProperties(systemParameterDTO, sysParameter);
			sysParameterDAO.updateByPrimaryKey(sysParameter);
			systemInfoBO.initParameter();

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新系统参数失败");
		}
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public BaseDAO getSystemParameterDAO() {
		return systemParameterDAO;
	}

	public void setSystemParameterDAO(BaseDAO systemParameterDAO) {
		this.systemParameterDAO = systemParameterDAO;
	}

	public SystemParameterDAO getSysParameterDAO() {
		return sysParameterDAO;
	}

	public void setSysParameterDAO(SystemParameterDAO sysParameterDAO) {
		this.sysParameterDAO = sysParameterDAO;
	}

}
