package com.huateng.univer.system.syslog.biz.service.impl;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.syslog.SystemLogDTO;
import com.allinfinance.univer.system.syslog.SystemLogQueryDTO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.SystemLog;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.system.syslog.biz.service.SystemLogService;
import com.huateng.univer.system.syslog.integration.dao.SystemLogServiceDAO;
import com.huateng.univer.system.user.integration.dao.UserServiceDAO;

public class SystemLogServiceImpl implements SystemLogService {
	Logger logger = Logger.getLogger(SystemLogServiceImpl.class);
	private PageQueryDAO pageQueryDAO;
	public SystemLogServiceDAO systemLogDAO;
	private UserServiceDAO userServiceDAO;

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	/**
	 * 加载分页信息
	 * 
	 * @param systemLogQueryDTO
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inquerySystemLog(SystemLogQueryDTO systemLogQueryDTO)
			throws BizServiceException {
		try {
			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"SYSTEMLOG.selectSystemLogByDTO", systemLogQueryDTO);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询系统日志失败!");
		}
	}

	public SystemLogDTO loadSysLog(SystemLogDTO systemLogDTO)
			throws BizServiceException {
		try {
			SystemLog systemLog = systemLogDAO.selectByPrimaryKey(systemLogDTO
					.getLogId());
			User user = userServiceDAO.selectByPrimaryKey(systemLog
					.getOperationUser());
			ReflectionUtil.copyProperties(systemLog, systemLogDTO);
			if (user != null)
				systemLogDTO.setOperationUserName(user.getUserName());
			return systemLogDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询系统日志信息失败");
		}
	}

	/*
	 * public PageDataDTO inqueryCardStockOprLog( CardStockOprQueryDTO
	 * cardStockOprQueryDTO) throws BizServiceException { try { PageDataDTO
	 * pageDataDTO = pageQueryDAO.query( "CARDSTOCKOPR.selectCardStockOprByDTO",
	 * cardStockOprQueryDTO); return pageDataDTO; } catch (Exception e) {
	 * this.logger.error(e.getMessage()); logger.error(e); throw new
	 * BizServiceException("查询卡库存调整日志失败!"); } }
	 */

	public UserServiceDAO getUserServiceDAO() {
		return userServiceDAO;
	}

	public SystemLogServiceDAO getSystemLogDAO() {
		return systemLogDAO;
	}

	public void setSystemLogDAO(SystemLogServiceDAO systemLogDAO) {
		this.systemLogDAO = systemLogDAO;
	}

	public void setUserServiceDAO(UserServiceDAO userServiceDAO) {
		this.userServiceDAO = userServiceDAO;
	}

}
