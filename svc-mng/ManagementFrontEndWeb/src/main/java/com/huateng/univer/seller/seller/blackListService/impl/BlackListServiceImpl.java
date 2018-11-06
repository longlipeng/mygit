package com.huateng.univer.seller.seller.blackListService.impl;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListAreaDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListBatchDTO;
import com.allinfinance.univer.seller.blacklist.dto.BlackListPerDTO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.univer.seller.seller.blackList.dao.BlackListDAO;
import com.huateng.univer.seller.seller.blackListService.BlackListService;

import org.apache.log4j.Logger;

public class BlackListServiceImpl implements BlackListService{
	private Logger logger = Logger.getLogger(BlackListServiceImpl.class);

	BlackListDAO black_list_dao;
	private PageQueryDAO pageQueryDAO;
	

	/*插入黑名单*/
	public void insert(BlackListBatchDTO blackListDTO) throws BizServiceException{
		if(blackListDTO.getFlag().trim().equals("1")){//插入人员黑名单
			
			batchInsertPerson(blackListDTO);
		

		
		}
		if(blackListDTO.getFlag().trim().equals("2")){//插入人员黑名单
			batchInsertArea(blackListDTO);

		
		}
		
	}
	/*批量插入数据前清空数据  需要添加事务可能插入失败 而数据清空*/
	@Override
	public void batchInsertPerson(BlackListBatchDTO blackListDTO) throws BizServiceException{
		List<BlackListPerDTO> list=blackListDTO.getPersonList();		
		black_list_dao.delectAllBlackListPerson();
		black_list_dao.batchInsertPerson(list);
		
	}
	@Override
	public void insertPerson(BlackListBatchDTO personDto) throws BizServiceException {
		
		
		
	}
	//逐条插入地区黑名单
	@Override
	public void insertArea(BlackListBatchDTO areaDto) throws BizServiceException {
		
		 List<BlackListAreaDTO> areaList= areaDto.getAreaList();			
		  black_list_dao.delectAllBlackListArea();		 
		for(int i=0;i<areaList.size();i++){
			try{
				BlackListAreaDTO dto=areaList.get(i);
				black_list_dao.insertArea(dto);
			}catch(Exception e){
				logger.error(e.getMessage());
				throw new BizServiceException("第"+i+"条信息格式有误");
			}
		}
		
		
		
	}
	
	

/**
 * 批量插入数据前清空数据
 * 
 * 需要添加事务可能插入失败 而数据清空
 */
	@Override
	public void batchInsertArea(BlackListBatchDTO blackListDTO) throws BizServiceException{
		  List<BlackListAreaDTO> areaList= blackListDTO.getAreaList();
		
		  black_list_dao.delectAllBlackListArea();
		  black_list_dao.batchInsertArea(areaList);
		  
		
	}
	
	

	@Override
	public PageDataDTO inqueryPerson(BlackListPerDTO blackListPerDTO) throws BizServiceException{		
			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"BLACK_LIST.inqueryPerson", blackListPerDTO);
			return pageDataDTO;
			
	}

	@Override
	public PageDataDTO inqueryArea(BlackListAreaDTO areaDTO) throws BizServiceException{
		
		PageDataDTO pageDataDTO =pageQueryDAO.query(
				"BLACK_LIST.inqueryArea", areaDTO);
		return pageDataDTO;
	}


	public BlackListDAO getBlack_list_dao() {
		return black_list_dao;
	}
	public void setBlack_list_dao(BlackListDAO black_list_dao) {
		this.black_list_dao = black_list_dao;
	}
	
	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}
	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}
	

}
