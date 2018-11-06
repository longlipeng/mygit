package com.huateng.univer.issuer.cardcompany.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.cardcompany.CardCompanyDTO;
import com.allinfinance.univer.issuer.dto.cardcompany.CardCompanyQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardCompanyDAO;
import com.huateng.framework.ibatis.model.CardCompany;
import com.huateng.framework.ibatis.model.CardCompanyExample;
import com.huateng.framework.ibatis.model.CardCompanyExample.Criteria;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.issuer.cardcompany.service.CardCompanyService;

/**
 * 制卡商管理service
 * 
 * @author xxl
 * 
 */
public class CardCompanyServiceImpl implements CardCompanyService {
	Logger logger = Logger.getLogger(this.getClass());
	private PageQueryDAO pageQueryDAO;
	private CommonsDAO commonsDAO;
	private CardCompanyDAO cardCompanyDAO;

	public PageDataDTO inquery(CardCompanyQueryDTO cardCompanyQueryDTO)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("CARD_COMPANY.selectCardCompanyByDTO",
					cardCompanyQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询制卡商失败！");
		}
	}

	public List<CardCompanyDTO> getCardCompanyDTOsByExample(
			CardCompanyExample cardCompanyExample) throws BizServiceException {
		try {
			List<CardCompany> cardCompanyList = cardCompanyDAO
					.selectByExample(cardCompanyExample);
			List<CardCompanyDTO> cardCompanyDTOs = new ArrayList<CardCompanyDTO>();
			for (CardCompany cardCompany : cardCompanyList) {
				CardCompanyDTO cardCompanyDTO = new CardCompanyDTO();
				ReflectionUtil.copyProperties(cardCompany, cardCompanyDTO);
				cardCompanyDTOs.add(cardCompanyDTO);
			}
			return cardCompanyDTOs;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询制卡商失败！");
		}

	}

	private int checkRepeat(CardCompanyDTO cardCompanyDTO) {
		CardCompanyExample example = new CardCompanyExample();
		Criteria criteria = example.createCriteria().andCardCompanyNameEqualTo(
				cardCompanyDTO.getCardCompanyName()).andEntityIdEqualTo(
				cardCompanyDTO.getEntityId());
		// 更新时
		if (null != cardCompanyDTO.getCardCompanyId()
				&& !"".equals(cardCompanyDTO.getCardCompanyId())) {
			criteria.andCardCompanyIdNotEqualTo(cardCompanyDTO
					.getCardCompanyId());
		}

		return cardCompanyDAO.countByExample(example);
	}

	public void insert(CardCompanyDTO cardCompanyDTO)
			throws BizServiceException {
		try {
			if (checkRepeat(cardCompanyDTO) > 0) {
				throw new BizServiceException(cardCompanyDTO
						.getCardCompanyName()
						+ "  制卡商已存在！");
			}
			CardCompany cardCompany = new CardCompany();
			ReflectionUtil.copyProperties(cardCompanyDTO, cardCompany);
			cardCompany.setCardCompanyId(commonsDAO
					.getNextValueOfSequence("TB_CARD_COMPANY"));
			cardCompany.setEntityId(cardCompanyDTO.getDefaultEntityId());
			cardCompany.setCardCompanyState(DataBaseConstant.STATE_ACTIVE);
			cardCompany.setCreateUser(cardCompanyDTO.getLoginUserId());
			cardCompany.setCreateTime(DateUtil.getCurrentTime());
			cardCompany.setModifyUser(cardCompanyDTO.getLoginUserId());
			cardCompany.setModifyTime(DateUtil.getCurrentTime());
			cardCompany.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			cardCompanyDAO.insert(cardCompany);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加制卡商失败！");
		}
	}

	public void update(CardCompanyDTO cardCompanyDTO)
			throws BizServiceException {
		try {
			if (checkRepeat(cardCompanyDTO) > 0) {
				throw new BizServiceException(cardCompanyDTO
						.getCardCompanyName()
						+ "制卡商已存在！");
			}
			CardCompany cardCompany = new CardCompany();
			ReflectionUtil.copyProperties(cardCompanyDTO, cardCompany);
			cardCompany.setModifyUser(cardCompanyDTO.getLoginUserId());
			cardCompany.setModifyTime(DateUtil.getCurrentTime());
			cardCompanyDAO.updateByPrimaryKeySelective(cardCompany);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新制卡商失败！");
		}
	}

	public CardCompanyDTO view(CardCompanyDTO cardCompanyDTO)
			throws BizServiceException {
		try {
			CardCompany cardCompany = cardCompanyDAO
					.selectByPrimaryKey(cardCompanyDTO.getCardCompanyId());
			ReflectionUtil.copyProperties(cardCompany, cardCompanyDTO);
			return cardCompanyDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看制卡商失败！");
		}
	}

	public void modifyCardCompany(CardCompanyDTO cardCompanyDTO)
			throws BizServiceException {
		try {
			CardCompany cardCompany = new CardCompany();
			ReflectionUtil.copyProperties(cardCompanyDTO, cardCompany);
			cardCompany.setModifyUser(cardCompanyDTO.getLoginUserId());
			cardCompany.setModifyTime(DateUtil.getCurrentTime());
			cardCompanyDAO.updateByPrimaryKeySelective(cardCompany);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改制卡商状态失败！");
		}
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public CardCompanyDAO getCardCompanyDAO() {
		return cardCompanyDAO;
	}

	public void setCardCompanyDAO(CardCompanyDAO cardCompanyDAO) {
		this.cardCompanyDAO = cardCompanyDAO;
	}

}
