package com.huateng.univer.issuer.cardLayout.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardLayoutDAO;
import com.huateng.framework.ibatis.dao.ProdLayoutDAO;
import com.huateng.framework.ibatis.model.CardLayout;
import com.huateng.framework.ibatis.model.ProdLayout;
import com.huateng.framework.ibatis.model.ProdLayoutExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.issuer.cardLayout.service.CardLayoutService;

public class CardlayoutServiceImpl implements CardLayoutService {

	Logger logger = Logger.getLogger(CardlayoutServiceImpl.class);

	/**
	 * 共通操作DAO
	 */
	private CommonsDAO commonsDAO;

	/**
	 * 分页查询DAO
	 */
	private PageQueryDAO pageQueryDAO;
	private ProdLayoutDAO prodLayoutDAO;

	public ProdLayoutDAO getProdLayoutDAO() {
		return prodLayoutDAO;
	}

	public void setProdLayoutDAO(ProdLayoutDAO prodLayoutDAO) {
		this.prodLayoutDAO = prodLayoutDAO;
	}

	public CardLayoutDAO getCardLayoutDAO() {
		return cardLayoutDAO;
	}

	public void setCardLayoutDAO(CardLayoutDAO cardLayoutDAO) {
		this.cardLayoutDAO = cardLayoutDAO;
	}

	private CardLayoutDAO cardLayoutDAO;

	public void deleteCardLayout(CardLayoutDTO cardLayoutDTO)
			throws BizServiceException {
		try {
			List<String> cardLayoutIdList = new ArrayList<String>();
			for (CardLayoutDTO target : cardLayoutDTO.getCardLayoutDTO()) {
				cardLayoutIdList.add(target.getCardLayoutId());
			}
			ProdLayoutExample example = new ProdLayoutExample();
			example.createCriteria().andCardLayoutIdIn(cardLayoutIdList);
			List<ProdLayout> prodLayouts = prodLayoutDAO
					.selectByExample(example);
			if (null != prodLayouts && prodLayouts.size() > 0) {
				throw new BizServiceException("卡面已关联产品,不能删除");
			}
			List<CardLayout> cardLayouts = new ArrayList<CardLayout>();
			for (CardLayoutDTO cardLayoutDto : cardLayoutDTO.getCardLayoutDTO()) {
				CardLayout cardLayout = new CardLayout();
				cardLayout.setCardLayoutId(cardLayoutDto.getCardLayoutId());
				cardLayout.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				cardLayouts.add(cardLayout);
			}
			commonsDAO
					.batchUpdate(
							"TB_CARD_LAYOUT.abatorgenerated_updateByPrimaryKeySelective",
							cardLayouts);
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除信息失败~！");
		}
	}

	public PageDataDTO inqueryCardLayout(CardLayoutQueryDTO cardLayoutQueryDTO)
			throws BizServiceException {
		try {
			cardLayoutQueryDTO.setSort("desc");
			cardLayoutQueryDTO.setSortFieldName("cardLayoutId");
			PageDataDTO pdd = pageQueryDAO.query("CARDLAYOUT.selectCardLayout",
					cardLayoutQueryDTO);
			return pdd;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询信息失败~！");
		}
	}

	public void insertCardLayout(CardLayoutDTO cardLayoutDTO)
			throws BizServiceException {
		try {
			/*
			 * @先查询卡面名称是否相同，如果相同抛出已存在的异常 如果不存在招待插入操作
			 */
			/*
			 * CardLayoutExample cardLayoutExample = new CardLayoutExample();
			 * cardLayoutExample.createCriteria().andDataStateEqualTo(
			 * DataBaseConstant.DATA_STATE_NORMAL)
			 * .andCardLayoutNameEqualTo(cardLayoutDTO.getCardName());
			 * List<CardLayout> cardLayouts = cardLayoutDAO
			 * .selectByExampleWithBLOBs(cardLayoutExample); if
			 * (cardLayouts.size() > 0) { throw new BizServiceException(" 卡面名称:"
			 * + cardLayoutDTO.getCardName() + " 已存在!"); }
			 */
			CardLayout cardLayout = new CardLayout();
			ReflectionUtil.copyProperties(cardLayoutDTO, cardLayout);
			String id = commonsDAO.getNextValueOfSequence("TB_CARD_LAYOUT");
			cardLayout.setCardLayoutId(id);
			cardLayout.setCardLayoutName(cardLayoutDTO.getCardName());
			cardLayout.setCardLayoutpicture(cardLayoutDTO.getPicture());
			cardLayout.setCardLayoutComment(cardLayoutDTO.getMemo());
			cardLayout.setEntityId(cardLayoutDTO.getDefaultEntityId());
			cardLayout.setCreateTime(DateUtil.getCurrentTime());
			cardLayout.setCreateUser(cardLayoutDTO.getCreateUser());
			cardLayout.setModifyUser(cardLayoutDTO.getCreateUser());
			cardLayout.setModifyTime(DateUtil.getCurrentTime());
			cardLayout.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			cardLayoutDAO.insert(cardLayout);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加信息失败~！");
		}

	}

	public List<CardLayoutDTO> selectCardLayout(
			CardLayoutQueryDTO cardLayoutQueryDTO) {
		
		return null;
	}

	public void updateCardLayout(CardLayoutDTO cardLayoutDTO)
			throws BizServiceException {
		try {
			/*
			 * CardLayoutExample cardLayoutExample = new CardLayoutExample();
			 * cardLayoutExample.createCriteria().andDataStateEqualTo(
			 * DataBaseConstant.DATA_STATE_NORMAL)
			 * .andCardLayoutNameEqualTo(cardLayoutDTO.getCardName())
			 * .andCardLayoutIdNotEqualTo(cardLayoutDTO.getCardLayoutId());
			 * List<CardLayout> cardLayouts = cardLayoutDAO
			 * .selectByExampleWithBLOBs(cardLayoutExample); if
			 * (cardLayouts.size() > 0) { throw new BizServiceException(" 卡面名称:"
			 * + cardLayoutDTO.getCardName() + " 已存在!"); }
			 */
			CardLayout cardLayout = new CardLayout();
			ReflectionUtil.copyProperties(cardLayoutDTO, cardLayout);

			cardLayout.setModifyTime(DateUtil.getCurrentTime());
			cardLayout.setCardLayoutComment(cardLayoutDTO.getMemo());
			cardLayout.setCardLayoutName(cardLayoutDTO.getCardName());
			cardLayout.setCardLayoutpicture(cardLayoutDTO.getPicture());

			cardLayoutDAO.updateByPrimaryKeySelective(cardLayout);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改信息失败~！");
		}

	}

	public CardLayoutDTO viewCardLayout(CardLayoutDTO cardLayoutDTO)
			throws BizServiceException {
		try {
			CardLayout cardlayout = cardLayoutDAO
					.selectByPrimaryKey(cardLayoutDTO.getCardLayoutId());
			CardLayoutDTO cardLayoutDto = new CardLayoutDTO();
			ReflectionUtil.copyProperties(cardlayout, cardLayoutDto);
			cardLayoutDto.setCardName(cardlayout.getCardLayoutName());
			cardLayoutDto.setPicture(cardlayout.getCardLayoutpicture());
			cardLayoutDto.setMemo(cardlayout.getCardLayoutComment());
			return cardLayoutDto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取卡面信息失败~！");
		}
	}

	public CardLayoutDTO loadCardLayoutForEdit(CardLayoutDTO cardLayoutDTO)
			throws BizServiceException {
		try {
			ProdLayoutExample example = new ProdLayoutExample();
			example.createCriteria().andCardLayoutIdEqualTo(
					cardLayoutDTO.getCardLayoutId());
			List<ProdLayout> prodLayouts = prodLayoutDAO
					.selectByExample(example);
			if (null != prodLayouts && prodLayouts.size() > 0) {
				throw new BizServiceException("该卡面已关联产品,不能修改");
			}

			CardLayout cardlayout = cardLayoutDAO
					.selectByPrimaryKey(cardLayoutDTO.getCardLayoutId());
			CardLayoutDTO cardLayoutDto = new CardLayoutDTO();
			ReflectionUtil.copyProperties(cardlayout, cardLayoutDto);
			cardLayoutDto.setCardName(cardlayout.getCardLayoutName());
			cardLayoutDto.setPicture(cardlayout.getCardLayoutpicture());
			cardLayoutDto.setMemo(cardlayout.getCardLayoutComment());

			return cardLayoutDto;
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取卡面信息失败~！");
		}
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public void initCardLayout() throws BizServiceException {
		

	}

}
