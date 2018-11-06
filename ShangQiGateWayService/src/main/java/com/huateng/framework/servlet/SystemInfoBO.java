package com.huateng.framework.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.univer.system.dictinfo.dto.DictInfoDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.allinfinance.univer.system.sysparam.dto.SystemParameterDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.EntitySystemParameterDAO;
import com.huateng.framework.ibatis.model.DictInfo;
import com.huateng.framework.ibatis.model.DictInfoExample;
import com.huateng.framework.ibatis.model.EntitySystemParameter;
import com.huateng.framework.ibatis.model.EntitySystemParameterExample;
import com.huateng.framework.ibatis.model.SystemLog;
import com.huateng.framework.ibatis.model.SystemParameter;
import com.huateng.framework.ibatis.model.SystemParameterExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.SystemInfo;
import com.huateng.framework.webservice.service.SystemInfoService;
import com.huateng.framework.webservice.service.WebServiceClientService;
import com.huateng.univer.system.dictinfo.integration.dao.DictInfoServiceDAO;
import com.huateng.univer.system.syslog.integration.dao.SystemLogServiceDAO;
import com.huateng.univer.system.sysparam.integration.dao.SystemParameterServiceDAO;

/**
 * 系统信息业务对象，该对象完成一些系统级别参数的读取和系统日志的记录
 */
public class SystemInfoBO {

	/**
	 * 字典数据
	 */
	private static Map<Integer, List<DictInfoDTO>> dictInfo;
	/**
	 * 系统参数数据
	 */
	private static List<SystemParameterDTO> parameters;
	/**
	 * 实体系统参数数据
	 */
	private static List<EntitySystemParameterDTO> entityParameters;

	private WebServiceClientService webServiceClientService;

	private DictInfoServiceDAO dictInfoDAO;
	private SystemParameterServiceDAO systemParameterDAO;
	private EntitySystemParameterDAO entitySystemParameterDAO;
	private CommonsDAO commonsDAO;
	private SystemLogServiceDAO systemLogDAO;
	private SystemInfoService systemInfoService;

	public void logError(String txnCode, String userId, String txnContent,
			String operationMemo) throws Exception {
		SystemLog systemLog = new SystemLog();
		String logId = commonsDAO
				.getNextValueOfSequenceBySequence("SEQ_SYSTEM_LOG");
		systemLog.setLogId(logId);
		systemLog.setTxnCode(txnCode);
		systemLog.setTxnContent(txnContent);
		systemLog.setOperationUser(userId);
		systemLog.setSuccessFlag("0");
		systemLog.setOperationTime(DateUtil.getCurrentTime());
		systemLog.setOperationMemo(operationMemo);
		systemLogDAO.insert(systemLog);
	}

	public void logSuccess(String txnCode, String userId, String txnContent)
			throws Exception {
		SystemLog systemLog = new SystemLog();
		String logId = commonsDAO
				.getNextValueOfSequenceBySequence("SEQ_SYSTEM_LOG");
		systemLog.setLogId(logId);
		systemLog.setTxnCode(txnCode);
		systemLog.setTxnContent(txnContent);
		systemLog.setOperationUser(userId);
		systemLog.setSuccessFlag("1");
		systemLog.setOperationTime(DateUtil.getCurrentTime());
		systemLogDAO.insert(systemLog);
	}

	public void initSystemInfo() {
		parameters = getSystemParameter();
		dictInfo = getSystemDictInfo();
	}

	public void initParameter() {
		parameters = getSystemParameter();
	}

	public void initEntityParameter() {
		entityParameters = getEntitySystemParameter();
	}

	public void initDict() {
		dictInfo = getSystemDictInfo();
	}

	/**
	 * 根据系统参数代码,取系统参数值
	 * 
	 * @param parameterName
	 * @return
	 */
	public static String getParameterValue(String parameterCode)
			throws BizServiceException {
		String parameterValue = null;
		if (parameters == null)
			throw new BizServiceException("系统异常，系统参数没有初始化！");
		for (int i = 0; i < parameters.size(); i++) {
			SystemParameterDTO systemParameterDTO = parameters.get(i);
			if (parameterCode.equals(systemParameterDTO.getParameterCode())) {
				parameterValue = systemParameterDTO.getParameterCode();
				break;
			}
		}
		return parameterValue;
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

	public List<EntitySystemParameterDTO> getEntitySystemParameter() {

		List<EntitySystemParameterDTO> systemParameterDTOs = new ArrayList<EntitySystemParameterDTO>();

		EntitySystemParameterExample systemParameterExample = new EntitySystemParameterExample();

		List<?> systemParameters = entitySystemParameterDAO
				.selectByExample(systemParameterExample);

		for (int i = 0; i < systemParameters.size(); i++) {
			EntitySystemParameter systemParameter = (EntitySystemParameter) systemParameters
					.get(i);
			EntitySystemParameterDTO systemParameterDTO = new EntitySystemParameterDTO();
			ReflectionUtil.copyProperties(systemParameter, systemParameterDTO);
			systemParameterDTOs.add(systemParameterDTO);
		}
		return systemParameterDTOs;
	}

	public Map<Integer, List<DictInfoDTO>> getSystemDictInfo() {

		/**
		 * 设置查询条件,只查询状态为有效的（等于1）,且根据字典类型进行排序
		 */
		DictInfoExample example = new DictInfoExample();
		example.createCriteria().andDictStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL);
		example.setOrderByClause("DICT_TYPE_CODE,DICT_CODE");
		List<DictInfo> list = dictInfoDAO.selectByExample(example);
		Map<Integer, List<DictInfoDTO>> map = new HashMap<Integer, List<DictInfoDTO>>();
		Integer dictType = Integer.valueOf(0);

		List<DictInfoDTO> lstDictInfo = new ArrayList<DictInfoDTO>();
		/**
		 * 
		 */
		for (int i = 0; i < list.size(); i++) {
			DictInfo dictInfo = (DictInfo) list.get(i);
			DictInfoDTO dictInfoDTO = new DictInfoDTO();
			ReflectionUtil.copyProperties(dictInfo, dictInfoDTO);
			Integer dictTypeCode = Integer.parseInt(dictInfo.getDictTypeCode());

			if (dictType.compareTo(dictTypeCode) == 0) {
				lstDictInfo.add(dictInfoDTO);
			} else {
				// 当字典类型变为第二个时候，在map中添加字典list
				if (dictType.compareTo(new Integer(0)) > 0) {
					map.put(dictType, lstDictInfo);
				}
				lstDictInfo = new ArrayList<DictInfoDTO>();
				dictType = dictTypeCode;
				lstDictInfo.add(dictInfoDTO);
			}
		}
		// 添加最后一个字典list
		map.put(dictType, lstDictInfo);
		return map;
	}

	/**
	 * 初始化实体系统参数
	 */

	@SuppressWarnings("unchecked")
	public void initEntityDict() throws BizServiceException {

		// OperationCtx operationCtx = new OperationCtx();
		// operationCtx.setTxncode("4001000010");
		// OperationRequest operationRequest = new OperationRequest();
		// OperationResult operationResult = webServiceClientService.process(
		// operationCtx, operationRequest);
		// Map entityIdDictInfo = (Map) operationResult.getDetailvo();
		// SystemInfo.setEntityDictInfo(entityIdDictInfo);
		systemInfoService.initEntityDictInfo();

	}

	public SystemLogServiceDAO getSystemLogDAO() {
		return systemLogDAO;
	}

	public void setSystemLogDAO(SystemLogServiceDAO systemLogDAO) {
		this.systemLogDAO = systemLogDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public static Map<Integer, List<DictInfoDTO>> getDictInfo() {
		return dictInfo;
	}

	public static void setDictInfo(Map<Integer, List<DictInfoDTO>> dictInfo) {
		SystemInfoBO.dictInfo = dictInfo;
	}

	public static List<SystemParameterDTO> getParameters() {
		return parameters;
	}

	public static void setParameters(List<SystemParameterDTO> parameters) {
		SystemInfoBO.parameters = parameters;
	}

	public static List<EntitySystemParameterDTO> getEntityParameters() {
		return entityParameters;
	}

	public static void setEntityParameters(
			List<EntitySystemParameterDTO> entityParameters) {
		SystemInfoBO.entityParameters = entityParameters;
	}

	public SystemParameterServiceDAO getSystemParameterDAO() {
		return systemParameterDAO;
	}

	public void setSystemParameterDAO(
			SystemParameterServiceDAO systemParameterDAO) {
		this.systemParameterDAO = systemParameterDAO;
	}

	public DictInfoServiceDAO getDictInfoDAO() {
		return dictInfoDAO;
	}

	public void setDictInfoDAO(DictInfoServiceDAO dictInfoDAO) {
		this.dictInfoDAO = dictInfoDAO;
	}

	public WebServiceClientService getWebServiceClientService() {
		return webServiceClientService;
	}

	public void setWebServiceClientService(
			WebServiceClientService webServiceClientService) {
		this.webServiceClientService = webServiceClientService;
	}

	public EntitySystemParameterDAO getEntitySystemParameterDAO() {
		return entitySystemParameterDAO;
	}

	public void setEntitySystemParameterDAO(
			EntitySystemParameterDAO entitySystemParameterDAO) {
		this.entitySystemParameterDAO = entitySystemParameterDAO;
	}

	public SystemInfoService getSystemInfoService() {
		return systemInfoService;
	}

	public void setSystemInfoService(SystemInfoService systemInfoService) {
		this.systemInfoService = systemInfoService;
	}

}
