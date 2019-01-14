package com.huateng.univer.consumer.blacklistmchnt.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.blacklistmchnt.dto.BlackListMchntDTO;
import com.allinfinance.univer.consumer.blacklistmchnt.dto.BlackListMchntQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.BlackListMchnt;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.consumer.blacklistmchnt.biz.service.BlackListMchntService;
import com.huateng.framework.ibatis.dao.BlackListMchntDAO;
public class BlackListMchntServiceImpl implements BlackListMchntService {

	/**
	 * Log操作
	 */
	Logger logger = Logger.getLogger(BlackListMchntServiceImpl.class);
	/** 分页DAO */
	private PageQueryDAO pageQueryDAO;

	private CommonsDAO commonsDAO;
	
	private BlackListMchntDAO blackListMchntDAO;
	
	public BlackListMchntDAO getBlackListMchntDAO() {
		return blackListMchntDAO;
	}

	public void setBlackListMchntDAO(BlackListMchntDAO blackListMchntDAO) {
		this.blackListMchntDAO = blackListMchntDAO;
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

	@Override
	public PageDataDTO inquiryBlackListMchnt(BlackListMchntQueryDTO dto)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("BLACKLISTMCHNT.selectBlackListMchntByDTO", dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询黑名单信息失败！");
		}

		
	}

	@Override
	public void deleteBlackListMchnt(BlackListMchntDTO dto) throws BizServiceException {
		
		try{
		List<BlackListMchntDTO> list=dto.getBlackListMchntDTOList();//里面只有id
		List<BlackListMchnt> blackListMchnts=new ArrayList<BlackListMchnt>();
		for (BlackListMchntDTO blackListMchntDTO :list) {
			BlackListMchnt blackListMchnt=new BlackListMchnt();
			ReflectionUtil.copyProperties(blackListMchntDTO, blackListMchnt);
			blackListMchnts.add(blackListMchnt);
		}
		
		commonsDAO.batchDelete("TB_BLACKLIST_MCHNT.abatorgenerated_deleteByPrimaryKey", blackListMchnts);
		}catch(Exception e){
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除黑名单失败！");
		}
	}

	@Override
	public void insertBlackListMchnt(BlackListMchntDTO dto) throws BizServiceException {
		
		try{

			List<BlackListMchnt> blackListMchnts=new ArrayList<BlackListMchnt>();

					BlackListMchnt blist=new BlackListMchnt();
					blist.setMchntNo(dto.getMchntNo());
					blist.setMeno(dto.getMeno());
					blist.setCreateTime(DateUtil.getStringDate());
					blist.setCreateUser(dto.getLoginUserId());
					blist.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
					blist.setModifyUser(dto.getLoginUserId());
					blist.setModifyTime(DateUtil.getStringDate());
					blackListMchnts.add(blist);


			commonsDAO.batchInsert("TB_BLACKLIST_MCHNT.abatorgenerated_insert", blackListMchnts);

			
		}catch(Exception e){
			this.logger.error(e.getMessage());
			throw new BizServiceException("新增黑名单失败！");
		}
		
	}
}
