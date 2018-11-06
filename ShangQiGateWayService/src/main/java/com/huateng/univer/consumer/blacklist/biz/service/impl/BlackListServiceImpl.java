package com.huateng.univer.consumer.blacklist.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.blacklist.dto.BlackListDTO;
import com.allinfinance.univer.consumer.blacklist.dto.BlackListQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.BlackList;
import com.huateng.framework.ibatis.model.BlackListExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.consumer.blacklist.biz.service.BlackListService;
import com.huateng.framework.ibatis.dao.BlackListDAO;
public class BlackListServiceImpl implements BlackListService {

	/**
	 * Log操作
	 */
	Logger logger = Logger.getLogger(BlackListServiceImpl.class);
	/** 分页DAO */
	private PageQueryDAO pageQueryDAO;

	private CommonsDAO commonsDAO;
	
	private BlackListDAO blackListDAO;
	
	public BlackListDAO getBlackListDAO() {
		return blackListDAO;
	}

	public void setBlackListDAO(BlackListDAO blackListDAO) {
		this.blackListDAO = blackListDAO;
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
	public PageDataDTO inquiryBlackList(BlackListQueryDTO dto)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("BLACKLIST.selectBlackListByDTO", dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询黑名单信息失败！");
		}

		
	}

	@Override
	public void deleteBlackList(BlackListDTO dto) throws BizServiceException {
		
		try{
		List<BlackListDTO> list=dto.getBlackListDTOList();//里面只有id
		List<BlackList> blackLists=new ArrayList<BlackList>();
		for (BlackListDTO blackListDTO :list) {
			BlackList blackList=new BlackList();
			ReflectionUtil.copyProperties(blackListDTO, blackList);
			blackLists.add(blackList);
		}
		
		commonsDAO.batchDelete("TB_BLACKLIST.abatorgenerated_deleteByPrimaryKey", blackLists);
		}catch(Exception e){
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除黑名单失败！");
		}
	}

	@Override
	public void insertBlackList(BlackListDTO dto) throws BizServiceException {
		
		try{
		if(dto.getEndNo()==null){
			BlackListExample be=new BlackListExample();
			be.createCriteria().andCardNoEqualTo(dto.getStartNo());
			List<BlackList> bls=blackListDAO.selectByExample(be);
			if(bls.size()==0){
				BlackList blist=new BlackList();
				blist.setCardNo(dto.getStartNo());
				blist.setMeno(dto.getMeno());
				blist.setCreateTime(DateUtil.getStringDate());
				blist.setCreateUser(dto.getLoginUserId());
				blist.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
				blist.setModifyUser(dto.getLoginUserId());
				blist.setModifyTime(DateUtil.getStringDate());
				blackListDAO.insert(blist);
			}
		}else{
			List<BlackList> blackLists=new ArrayList<BlackList>();
			BlackListExample be=new BlackListExample();
			List<String> values = new ArrayList<String>();
			for(long i=Long.parseLong(dto.getStartNo());i<=Long.parseLong(dto.getEndNo());i++){
				values.add(String.valueOf(i));
			}
			be.createCriteria().andCardNoIn(values);
			//bls是黑名单集合，根据起始和结束参数查到
			List<BlackList> bls=blackListDAO.selectByExample(be);
			if(bls.size()>0)
				throw new BizServiceException("新增黑名单失败！重复添加卡号");
			for(long i=Long.parseLong(dto.getStartNo());i<=Long.parseLong(dto.getEndNo());i++){
				if(bls.size()==0){
					BlackList blist=new BlackList();
					blist.setCardNo(String.valueOf(i));
					blist.setMeno(dto.getMeno());
					blist.setCreateTime(DateUtil.getStringDate());
					blist.setCreateUser(dto.getLoginUserId());
					blist.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
					blist.setModifyUser(dto.getLoginUserId());
					blist.setModifyTime(DateUtil.getStringDate());
					blackLists.add(blist);
				}
			}
			commonsDAO.batchInsert("TB_BLACKLIST.abatorgenerated_insert", blackLists);
		}
			
		}catch(BizServiceException e){
			this.logger.error(e.getMessage());
			throw new BizServiceException(e.getErrorMessage());
		}catch(Exception e){
			this.logger.error(e.getMessage());
			throw new BizServiceException("新增黑名单失败！");
		}
		
	}
}
