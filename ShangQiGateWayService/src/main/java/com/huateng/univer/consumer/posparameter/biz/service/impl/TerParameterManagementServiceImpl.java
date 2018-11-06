package com.huateng.univer.consumer.posparameter.biz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.pos.dto.PosParameterDTO;
import com.allinfinance.univer.consumer.pos.dto.PosParameterQueryDTO;
import com.allinfinance.univer.consumer.pos.dto.posParameterValueDTO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardRouteDAO;
import com.huateng.framework.ibatis.dao.ConsumerDAO;
import com.huateng.framework.ibatis.dao.InsTransPrivDAO;
import com.huateng.framework.ibatis.dao.PosInfoDAO;
import com.huateng.framework.ibatis.dao.PosParamenterDAO;
import com.huateng.framework.ibatis.model.PosInfo;
import com.huateng.framework.ibatis.model.PosInfoExample;
import com.huateng.framework.ibatis.model.PosParamenter;
import com.huateng.framework.ibatis.model.PosParamenterExample;
import com.huateng.framework.ibatis.model.PosParamenterKey;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtils;
import com.huateng.hstserver.gatewayService.Java2TXNBusinessServiceImpl;
import com.huateng.univer.consumer.posparameter.biz.service.TerParameterManagementService;
import com.huateng.univer.issuer.entityBaseInfo.dao.EntityBaseInfoServiceDao;

public class TerParameterManagementServiceImpl implements
		TerParameterManagementService {

	Logger logger = Logger.getLogger(TerParameterManagementServiceImpl.class);
	private EntityBaseInfoServiceDao entityBaseInfoServiceDao;

	private PosParamenterDAO terParameterDAO;

	private PageQueryDAO pageQueryDAO;

	private CommonsDAO commonsDAO;
	private PosInfoDAO posInfoDAO;
	private InsTransPrivDAO insTransPrivDAO;
	private CardRouteDAO cardRouteDAO;
	private ConsumerDAO consumerDAO;

	private Java2TXNBusinessServiceImpl java2TXNBusinessService;

	public Java2TXNBusinessServiceImpl getJava2TXNBusinessService() {
		return java2TXNBusinessService;
	}

	public void setJava2TXNBusinessService(
			Java2TXNBusinessServiceImpl java2txnBusinessService) {
		java2TXNBusinessService = java2txnBusinessService;
	}

	public ConsumerDAO getConsumerDAO() {
		return consumerDAO;
	}

	public void setConsumerDAO(ConsumerDAO consumerDAO) {
		this.consumerDAO = consumerDAO;
	}

	public InsTransPrivDAO getInsTransPrivDAO() {
		return insTransPrivDAO;
	}

	public void setInsTransPrivDAO(InsTransPrivDAO insTransPrivDAO) {
		this.insTransPrivDAO = insTransPrivDAO;
	}

	public CardRouteDAO getCardRouteDAO() {
		return cardRouteDAO;
	}

	public void setCardRouteDAO(CardRouteDAO cardRouteDAO) {
		this.cardRouteDAO = cardRouteDAO;
	}

	public EntityBaseInfoServiceDao getEntityBaseInfoServiceDao() {
		return entityBaseInfoServiceDao;
	}

	public void setEntityBaseInfoServiceDao(
			EntityBaseInfoServiceDao entityBaseInfoServiceDao) {
		this.entityBaseInfoServiceDao = entityBaseInfoServiceDao;
	}

	public PosInfoDAO getposInfoDAO() {
		return posInfoDAO;
	}

	public void setposInfoDAO(PosInfoDAO posInfoDAO) {
		this.posInfoDAO = posInfoDAO;
	}

	public void setTerParameterDAO(PosParamenterDAO terParameterDAO) {
		this.terParameterDAO = terParameterDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	/** 查询卡Bin **/
	public List<posParameterValueDTO> queryCardBinList(
			posParameterValueDTO posParameterValueDTO)
			throws BizServiceException {
		List<posParameterValueDTO> cardBinList;
		cardBinList = commonsDAO.queryCardBinList(posParameterValueDTO);
		List<String> tmpList = new ArrayList<String>();
		List<posParameterValueDTO> cardBinRtnList = new ArrayList<posParameterValueDTO>();
		for (int i = 0; i < cardBinList.size(); i++) {
			String cardBin = cardBinList.get(i).getCardBin().trim();
			if (cardBin.length() > 8) {
				cardBin = cardBin.substring(0, 8);
			}
			if (!tmpList.contains(cardBin)) {
				tmpList.add(cardBin);
				cardBinList.get(i).setCardBin(cardBin);
				cardBinRtnList.add(cardBinList.get(i));
			}
		}
		return cardBinRtnList;
	}

	/**
	 * 添加公共系统参数
	 */

	public void insert(PosParameterDTO sysParameterDTO)
			throws BizServiceException {
		try {
			sysParameterDTO.setConsumerId(sysParameterDTO.getDefaultEntityId());
			PosParamenterKey key = new PosParamenterKey();
			key.setPrmId(sysParameterDTO.getPrmId());
			key.setPrmType(sysParameterDTO.getPrmType());
			key.setPrmVersion(sysParameterDTO.getPrmVersion());
			key.setConsumerId(sysParameterDTO.getDefaultEntityId());
			PosParamenter temp = terParameterDAO.selectByPrimaryKey(key);
			if (temp != null) {
				throw new BizServiceException("版本为:"
						+ sysParameterDTO.getPrmVersion() + "名称为:"
						+ sysParameterDTO.getPrmName() + "的公共参数已存在!");
			}
			PosParamenter sysParameter = new PosParamenter();
			ReflectionUtil.copyProperties(sysParameterDTO, sysParameter);
			if (sysParameter.getPrmId().equals("14")
					|| sysParameter.getPrmId().equals("15")) {
				sysParameter.setPrmLen(Short.valueOf("14"));
			} else if (sysParameter.getPrmId().equals("16")
					|| sysParameter.getPrmId().equals("18")) {
				sysParameter.setPrmLen(Short.valueOf("15"));
			} else {
				sysParameter.setPrmLen(Short.valueOf(String.valueOf(sysParameterDTO
						.getPrmVal().length())));
			}
			terParameterDAO.insert(sysParameter);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加终端系统参数失败!");
		}

	}

	public void delete(PosParameterDTO sysParameterDTO)
			throws BizServiceException {
		try {
			sysParameterDTO.setConsumerId(sysParameterDTO.getDefaultEntityId());
			PosParamenterKey key = new PosParamenterKey();
			key.setPrmId(sysParameterDTO.getPrmId());
			key.setPrmType(sysParameterDTO.getPrmType());
			key.setConsumerId(sysParameterDTO.getDefaultEntityId());
			terParameterDAO.deleteByPrimaryKey(key);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除终端系统参数失败!");
		}
	}

	public void update(PosParameterDTO sysParameterDTO)
			throws BizServiceException {
		try {
			sysParameterDTO.setConsumerId(sysParameterDTO.getDefaultEntityId());
			PosParamenterKey key = new PosParamenterKey();
			key.setPrmId(sysParameterDTO.getPrmId());
			key.setPrmType(sysParameterDTO.getPrmType());
			key.setPrmVersion(sysParameterDTO.getPrmVersion());
			key.setConsumerId(sysParameterDTO.getDefaultEntityId());
			PosParamenter sysParameter1 = terParameterDAO
					.selectByPrimaryKey(key);

			PosParamenter sysParameter = new PosParamenter();
			ReflectionUtil.copyProperties(sysParameterDTO, sysParameter);
			if (sysParameter.getPrmId().equals("14")
					|| sysParameter.getPrmId().equals("15")) {
				sysParameter.setPrmLen(Short.valueOf("14"));
			} else if (sysParameter.getPrmId().equals("16")
					|| sysParameter.getPrmId().equals("18")) {
				sysParameter.setPrmLen(Short.valueOf("15"));
			} else {
				sysParameter.setPrmLen(Short.valueOf(String.valueOf(sysParameterDTO
						.getPrmVal().length())));
			}
			// 如果修改的参数是卡BIN参数，且长度小于8，则右补空格;如果长度大于8，则截取左端8位
			if (sysParameter.getPrmType().equals("10002")) {
				if (sysParameter.getPrmVal().length() < 8)
					sysParameter.setPrmVal(StringUtils.rightPad(sysParameter
							.getPrmVal(), 8));
				else
					sysParameter.setPrmVal(sysParameter.getPrmVal().substring(
							0, 8));
				sysParameter.setPrmLen(Short.valueOf("8"));
			}
			terParameterDAO.updateByPrimaryKeySelective(sysParameter);


			/**
			 * 如果参数值被修改才更新系统参数
			 */

			if (!sysParameter1.getPrmVal().equals(sysParameterDTO.getPrmVal())) {
				/**
				 * 如果修改的参数是公共参数，则修改该版本下的公共参数关联的POS使其对应的更新标志为1
				 */
				if (sysParameterDTO.getPrmType().equals("10001")) {
					PosInfoExample example = new PosInfoExample();
					example.createCriteria().andPrmVersion1EqualTo(
							sysParameterDTO.getPrmVersion());
					List<PosInfo> posInfs = posInfoDAO.selectByExample(example);
					for (PosInfo posInf : posInfs) {
						posInf.setPrmChangeFlag1(Short.parseShort("1"));
						posInfoDAO.updateByPrimaryKeySelective(posInf);
					}
				}
				/**
				 * 如果修改的参数是卡BIN参数，则修改该版本下的公共参数关联的POS使其对应的更新标志为1
				 */
				if (sysParameterDTO.getPrmType().equals("10002")) {
					PosInfoExample example = new PosInfoExample();
					example.createCriteria().andPrmVersion1EqualTo(
							sysParameterDTO.getPrmVersion());
					List<PosInfo> posInfs = posInfoDAO.selectByExample(example);
					for (PosInfo posInf : posInfs) {
						posInf.setPrmChangeFlag2(Short.parseShort("1"));
						posInfoDAO.updateByPrimaryKeySelective(posInf);
					}

				}
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新终端系统参数失败!");
		}

	}

	public PageDataDTO inquery(PosParameterQueryDTO sysParameterQueryDTO)
			throws BizServiceException {
		try {
			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"TERPARAMETER.selecTerparameter", sysParameterQueryDTO);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询终端系统参数失败!");
		}
	}

	public PosParameterDTO view(PosParameterDTO sysParameterDTO)
			throws BizServiceException {
		try {
			PosParamenterKey key = new PosParamenterKey();
			key.setPrmId(sysParameterDTO.getPrmId());
			key.setPrmType(sysParameterDTO.getPrmType());
			key.setPrmVersion(sysParameterDTO.getPrmVersion());
			key.setConsumerId(sysParameterDTO.getDefaultEntityId());
			PosParamenter sysParameter = terParameterDAO
					.selectByPrimaryKey(key);
			PosParameterDTO parameterDTO = new PosParameterDTO();
			ReflectionUtil.copyProperties(sysParameter, parameterDTO);
			return parameterDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("加载终端系统参数失败!");
		}
	}

	/**
	 * 添加卡BIN参数
	 * 
	 * @return
	 */
	public void insertCardBin(PosParameterDTO sysParameterDTO)
			throws BizServiceException {
		try {
			sysParameterDTO.setConsumerId(sysParameterDTO.getDefaultEntityId());
			PosParamenter sysParameter = new PosParamenter();
			ReflectionUtil.copyProperties(sysParameterDTO, sysParameter);
			// 卡BIN不足8位，右补空格
			sysParameter.setPrmVal(StringUtils.rightPad(
					sysParameter.getPrmVal(), 8));
			//查找表，如果参数值prmVal已经存在，提示拒绝重复添加，退出
			PosParamenterExample example = new PosParamenterExample();
			example.createCriteria().andPrmValEqualTo(sysParameter.getPrmVal())
				.andConsumerIdEqualTo(sysParameter.getConsumerId()).andPrmTypeEqualTo(sysParameter.getPrmType());
			List<PosParamenter> list = terParameterDAO.selectByExample(example);
			if(list.size()>0){
				throw new BizServiceException("不允许重复添加！");
			}
			
			String prmid = commonsDAO
			.getNextValueOfSequence("TB_POS_PARAMETER");
			sysParameter.setPrmId(Integer.parseInt(prmid));
			sysParameter.setPrmLen(Short.valueOf("8"));
			if (sysParameter.getPrmVersion() == null
					|| "".equals(sysParameter.getPrmVersion())) {
				sysParameter.setPrmVersion(0);
			}
				terParameterDAO.insert(sysParameter);

		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加卡BIN失败");
		}
	}

	

	/**
	 * 添加IC卡参数
	 * 
	 * @return
	 */

	public void insertIc(PosParameterDTO sysParameterDTO)
			throws BizServiceException {
		try {
			sysParameterDTO.setConsumerId(sysParameterDTO.getDefaultEntityId());
			Map<String, String> icCardMap = new HashMap<String, String>();
			icCardMap.put("9F06", "AID");
			icCardMap.put("9F08", "应用版本号");
			icCardMap.put("DF01", "ASI");
			icCardMap.put("DF11", "TAC-缺省");
			icCardMap.put("DF12", "TAC-联机");
			icCardMap.put("DF13", "TAC-拒绝");
			icCardMap.put("DF18", "终端联机PIN支持");

			PosParamenterExample sysExample = new PosParamenterExample();
			sysExample.createCriteria().andPrmVersionEqualTo(
					sysParameterDTO.getPrmVersion()).andPrmTypeEqualTo(
					sysParameterDTO.getPrmType()).andPrmNameEqualTo(
					sysParameterDTO.getPrmName()).andConsumerIdEqualTo(
					sysParameterDTO.getDefaultEntityId());
			List<PosParamenter> list = terParameterDAO
					.selectByExample(sysExample);
			if (list.size() > 0) {
				throw new BizServiceException("版本为:"
						+ sysParameterDTO.getPrmVersion() + "名称为:"
						+ icCardMap.get(sysParameterDTO.getPrmName())
						+ "IC卡参数已存在!");
			}

			PosParamenter sysParameter = new PosParamenter();
			ReflectionUtil.copyProperties(sysParameterDTO, sysParameter);
			String prmid = commonsDAO
					.getNextValueOfSequence("TB_POS_PARAMETER");
			sysParameter.setPrmId(Integer.parseInt(prmid));
			sysParameter.setPrmLen(Short.valueOf(String.valueOf(sysParameterDTO.getPrmVal()
					.length())));
			terParameterDAO.insert(sysParameter);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加IC卡参数失败!");
		}
	}

	/**
	 * 
	 * 检查版本下的通迅参数已加了多少
	 */

	public String commPrm(PosParameterDTO sysParameterDTO)
			throws BizServiceException {

		PosParamenterExample example = new PosParamenterExample();
		example.createCriteria().andPrmStatEqualTo(Short.valueOf("0")).andPrmTypeEqualTo(
				sysParameterDTO.getPrmType()).andPrmVersionEqualTo(
				sysParameterDTO.getPrmVersion()).andConsumerIdEqualTo(
				sysParameterDTO.getDefaultEntityId());
		List<PosParamenter> sysList = terParameterDAO.selectByExample(example);
		if (sysParameterDTO.getPrmType().equals("10001")) {
			Map<Integer, String> map = new HashMap<Integer, String>();
			map.put(11, "pos终端应用类型");
			map.put(12, "超时时间");
			map.put(13, "重试次数");
			map.put(14, "电话号码之一");
			map.put(15, "电话号码之二");
			map.put(16, "主机地址一");
			map.put(17, "主机端口一");
			map.put(18, "主机地址二");
			map.put(19, "主机端口二");
			map.put(23, "交易重发次数");
			for (PosParamenter sysParameter : sysList) {
				map.remove(new Integer(sysParameter.getPrmId()));
			}
			if (map.size() > 0) {
				StringBuffer message = new StringBuffer();
				message.append("该版本下的通迅参数还剩");
				for (Integer key : map.keySet()) {
					message.append(map.get(key)).append("\r\n");
				}
				return message.toString();
			} else {
				return "该版本下的通讯参数全部添加完毕";
			}

		} else if (sysParameterDTO.getPrmType().equals("10003")) {
			Map<String, String> icCardMap = new HashMap<String, String>();
			icCardMap.put("9F06", "AID");
			icCardMap.put("9F08", "应用版本号");
			icCardMap.put("DF01", "ASI");
			icCardMap.put("DF11", "TAC-缺省");
			icCardMap.put("DF12", "TAC-联机");
			icCardMap.put("DF13", "TAC-拒绝");
			icCardMap.put("DF18", "终端联机PIN支持");
			for (PosParamenter sysParameter : sysList) {
				icCardMap.remove(sysParameter.getPrmName());
			}
			if (icCardMap.size() > 0) {
				StringBuffer message = new StringBuffer();
				message.append("该版本下的IC卡参数还剩");
				for (String key : icCardMap.keySet()) {
					message.append(icCardMap.get(key)).append("\r\n");
				}
				return message.toString();
			} else {
				return "该版本下的IC参数全部添加完毕";
			}
		}
		return "";
	}

	public PosParamenterDAO getTerParameterDAO() {
		return terParameterDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public PosInfoDAO getPosInfoDAO() {
		return posInfoDAO;
	}

	public void setPosInfoDAO(PosInfoDAO posInfoDAO) {
		this.posInfoDAO = posInfoDAO;
	}

}
