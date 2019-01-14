package com.huateng.univer.consumer.blacklist;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.blacklist.dto.BlackListDTO;
import com.allinfinance.univer.consumer.blacklist.dto.BlackListQueryDTO;
import com.huateng.framework.action.BaseAction;

public class BlackListAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6366844948055653423L;

	/**
	 * 黑名单查询DTO
	 */
	private BlackListQueryDTO blackListQueryDTO; 
	
	/**
	 * 黑名单DTO
	 */
	private BlackListDTO blackListDTO; 
	/**
     * 查询数据存放DTO
     */	
	private PageDataDTO pageDataDTO = new PageDataDTO();
	
	/**
	 * 黑名单cardNo存放List
	 */
	private List<String> cardNoList;
	
	public void setBlackListQueryDTO(BlackListQueryDTO blackListQueryDTO) {
		this.blackListQueryDTO = blackListQueryDTO;
	}

	public BlackListQueryDTO getBlackListQueryDTO() {
		return blackListQueryDTO;
	}

	public void setBlackListDTO(BlackListDTO blackListDTO) {
		this.blackListDTO = blackListDTO;
	}

	public BlackListDTO getBlackListDTO() {
		return blackListDTO;
	}
	
	
	
	
	public int getTotalRows() {
		if(null!=pageDataDTO){
			if(pageDataDTO.getTotalRecord()<0){
				return 0;
			}
			else return pageDataDTO.getTotalRecord();	
		}
		
		else return 0;
	}
	public String listAll() throws Exception{
		
		inquiry();
		return "list";
	}
	
	
	public String inquiry() throws Exception {
		//logger.info(StartDate+""+EndDate);
		if(blackListQueryDTO ==null){
			blackListQueryDTO =new BlackListQueryDTO();
		}
		ListPageInit(null,blackListQueryDTO);
		pageDataDTO=(PageDataDTO)sendService("2012110701",blackListQueryDTO).getDetailvo();
		
		if(hasErrors()){
			return "input";
		}
		return "list";
	}

	public String delete() throws Exception{
		List<BlackListDTO> blackListDTOList=new ArrayList<BlackListDTO>();
		for(String cardNo:cardNoList){
			BlackListDTO dto=new BlackListDTO();
			dto.setCardNo(cardNo);
			blackListDTOList.add(dto);
		}
		blackListDTO=new BlackListDTO();
		blackListDTO.setBlackListDTOList(blackListDTOList);
		sendService("2012110702",blackListDTO);
		
		 if (hasErrors()) {
	        	this.inquiry();
	            return "input";
	        }
	        addActionMessage("删除黑名单成功！");
	        return listAll();
	}
	
	public String add(){
		 if (hasErrors()) {
	            return "input";
	        }
		return "add";
	}
	
	public String insertBlackList() throws Exception{
		sendService("2012110703",blackListDTO);
		return inquiry();
	}
	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	

	public void setCardNoList(List<String> cardNoList) {
		this.cardNoList = cardNoList;
	}

	public List<String> getCardNoList() {
		return cardNoList;
	}
}
