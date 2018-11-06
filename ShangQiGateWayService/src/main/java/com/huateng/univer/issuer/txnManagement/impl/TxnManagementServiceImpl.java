package com.huateng.univer.issuer.txnManagement.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.txnManagement.CardRouteDTO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardRouteDAO;
import com.huateng.framework.ibatis.model.CardRoute;
import com.huateng.framework.ibatis.model.CardRouteExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtils;
import com.huateng.hstserver.constants.RspCodeMap;
import com.huateng.hstserver.gatewayService.Java2TXNBusinessServiceImpl;
import com.huateng.hstserver.model.TxnPackageDTO;
import com.huateng.univer.issuer.txnManagement.TxnManagementService;


public class TxnManagementServiceImpl extends SqlMapClientDaoSupport implements TxnManagementService {
	Logger logger = Logger.getLogger(TxnManagementServiceImpl.class);
	private PageQueryDAO pageQueryDAO;
	private CardRouteDAO cardRouteDAO;
	
	private Java2TXNBusinessServiceImpl java2TXNBusinessService;
	/**
	 * 查看卡bin路由信息
	 */
	@Override
	public PageDataDTO inqueryCardRoute(CardRouteDTO cardRouteDTO)
			throws BizServiceException {
		try {
			if(null==cardRouteDTO.getIssInsIdCd() || "".equals(cardRouteDTO.getIssInsIdCd().trim())){
				//组装机构号
				cardRouteDTO.setIssInsIdCd(getIssInsIdCd(cardRouteDTO.getDefaultEntityId()));
			}else{
				cardRouteDTO.setIssInsIdCd(getIssInsIdCd(cardRouteDTO.getIssInsIdCd()));
			}
//			cardRouteDTO.setIssInsIdCd(StringUtils.blankFillString(cardRouteDTO.getIssInsIdCd(), 13));
			if(null!=cardRouteDTO.getBinVal() && !"".equals(cardRouteDTO.getBinVal().trim())){
				cardRouteDTO.setBinVal(StringUtils.blankFillString(cardRouteDTO.getBinVal(), 14));
			}
			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"CARD_ROUTE.selectCardCoute", cardRouteDTO);
			return pageDataDTO;
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询交易路由信息失败！");
		}
	}
	
	
	private String getIssInsIdCd(String entityId){
		CardRouteDTO cardRouteDTO = new CardRouteDTO();
		if (entityId.length() < 10) {
			cardRouteDTO.setIssInsIdCd("0"
					+ entityId
							.length()
					+ entityId);
		} else {
			cardRouteDTO.setIssInsIdCd(entityId.length()
					+ entityId);
		}
		return cardRouteDTO.getIssInsIdCd();
	}
	public void cardRouteAdd(CardRouteDTO cardRouteDTO) throws BizServiceException{
		addCardRoute(cardRouteDTO);
//		loadCardRoute(cardRouteDTO);
	}
	
	public void addCardRoute(CardRouteDTO cardRouteDTO) throws BizServiceException{
		try {
			CardRoute cardRoute = new CardRoute();
			ReflectionUtil.copyProperties(cardRouteDTO, cardRoute);
			String strLength = cardRouteDTO.getLength().trim();
			String maxBin = StringUtils.rightPad(strLength
					+ cardRoute.getBinVal().trim(), 14, '9');
			cardRoute.setMaxBin(maxBin);
			String minBin = StringUtils.rightPad(strLength
					+ cardRoute.getBinVal().trim(), 14, '0');
			cardRoute.setMinBin(minBin);	
			cardRoute.setIssInsIdCd(cardRoute.getIssInsIdCd().trim());
				if (cardRoute.getIssInsIdCd().length() < 10) {
					cardRoute.setIssInsIdCd("0"
							+cardRoute.getIssInsIdCd()
									.length()
							+ cardRoute.getIssInsIdCd());
				} else {
					cardRoute.setIssInsIdCd(cardRoute.getIssInsIdCd().length()
							+ cardRoute.getIssInsIdCd());
				}
				
			CardRouteExample cardRouteExample = new CardRouteExample();
			cardRouteExample.createCriteria().andMaxBinEqualTo(cardRoute.getMaxBin());
			if(cardRouteDAO.selectByExample(cardRouteExample).size()>0){
				throw new BizServiceException("长度为"+strLength+"的卡bin"+cardRoute.getBinVal().trim()+"已存在，不能重复添加!");
			}
				
			cardRoute.setRsvData("0000000000");
			cardRoute.setTransChnl("*");
			cardRoute.setCardAttr("10");
			cardRoute
					.setBinVal(cardRoute.getBinVal().trim());
			cardRoute.setRecUpdUsrId(cardRouteDTO
					.getLoginUserId());
			cardRoute.setRecUpdTs(DateUtil
					.getCurrentDateAndTime());
			cardRoute.setRecCrtTs(DateUtil
					.getCurrentDateAndTime());
			cardRouteDAO.insert(cardRoute);
		} catch (BizServiceException b){
			throw b;
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加交易路由失败");
		}
	}
	
	public void cardRouteDel(CardRouteDTO cardRouteDTO) throws BizServiceException{
		delCardRoute(cardRouteDTO);
//		loadCardRoute(cardRouteDTO);
	}
	
	public void delCardRoute(CardRouteDTO cardRouteDTO) throws BizServiceException{
		try {
			CardRoute cardRoute = new CardRoute();
			ReflectionUtil.copyProperties(cardRouteDTO, cardRoute);
			String strLength = cardRouteDTO.getLength().trim();
			String maxBin = StringUtils.rightPad(strLength
					+ cardRoute.getBinVal().trim(), 14, '9');
			cardRoute.setMaxBin(maxBin);
			CardRouteExample example = new CardRouteExample();
			example.createCriteria().andMaxBinEqualTo(cardRoute.getMaxBin());
			cardRouteDAO.deleteByExample(example);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除交易路由失败");
		}
	}
	
	/**
	 * 加载后台路由
	 */
	public void loadCardRoute(CardRouteDTO cardRouteDTO)
			throws BizServiceException {
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		TxnPackageDTO txnPackageDto = new TxnPackageDTO();

		txnPackageDto.setTxnCode("M000");
		// 收单机构号
		txnPackageDto.setConsumerCode(cardRouteDTO.getDefaultEntityId());
		// 新加卡bin值
		txnPackageDto.setBinValue(cardRouteDTO.getBinVal());
		// 操作人员ID
		txnPackageDto.setOperaterId(cardRouteDTO.getLoginUserId());

		try {
			txnPackageDto = java2TXNBusinessService
					.loadCardRoute(txnPackageDto);

			String rspCode = txnPackageDto.getRspCode();
			if (!"00".equals(rspCode)) {
				if (rspCodeMap.containsKey(rspCode)) {
					throw new BizServiceException(rspCodeMap.get(rspCode));
				}
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}
	
	public List<CardRouteDTO> getIssuerList(CardRouteDTO cardRouteDTO) throws BizServiceException{
		try {
			if (cardRouteDTO.getDefaultEntityId().length() < 10) {
				cardRouteDTO.setIssInsIdCd("0"
						+ cardRouteDTO.getDefaultEntityId()
								.length()
						+ cardRouteDTO.getDefaultEntityId());
			} else {
				cardRouteDTO.setIssInsIdCd(cardRouteDTO
						.getDefaultEntityId().length()
						+ cardRouteDTO.getDefaultEntityId());
			}
			List<CardRouteDTO> issuerList = getSqlMapClientTemplate()
			.queryForList("CARD_ROUTE.selectIssuerInfoList", cardRouteDTO);
			return issuerList;
		} catch (DataAccessException e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询机构失败！");
		}
	}
	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}
	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CardRouteDAO getCardRouteDAO() {
		return cardRouteDAO;
	}

	public void setCardRouteDAO(CardRouteDAO cardRouteDAO) {
		this.cardRouteDAO = cardRouteDAO;
	}

	public Java2TXNBusinessServiceImpl getJava2TXNBusinessService() {
		return java2TXNBusinessService;
	}

	public void setJava2TXNBusinessService(
			Java2TXNBusinessServiceImpl java2txnBusinessService) {
		java2TXNBusinessService = java2txnBusinessService;
	}
	
}
