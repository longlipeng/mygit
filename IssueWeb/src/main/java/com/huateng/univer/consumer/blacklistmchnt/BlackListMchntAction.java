package com.huateng.univer.consumer.blacklistmchnt;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.blacklistmchnt.dto.BlackListMchntDTO;
import com.allinfinance.univer.consumer.blacklistmchnt.dto.BlackListMchntQueryDTO;
import com.huateng.framework.action.BaseAction;

public class BlackListMchntAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6366844948055653423L;

	/**
	 * 黑名单查询DTO
	 */
	private BlackListMchntQueryDTO blackListMchntQueryDTO; 
	
	/**
	 * 黑名单DTO
	 */
	private BlackListMchntDTO blackListMchntDTO; 
	/**
     * 查询数据存放DTO
     */	
	private PageDataDTO pageDataDTO = new PageDataDTO();
	
	/**
	 * 黑名单mchntNo存放List
	 */
	private List<String> mchntNoList;
	
	public void setBlackListMchntQueryDTO(BlackListMchntQueryDTO blackListMchntQueryDTO) {
		this.blackListMchntQueryDTO = blackListMchntQueryDTO;
	}

	public BlackListMchntQueryDTO getBlackListMchntQueryDTO() {
		return blackListMchntQueryDTO;
	}

	public void setBlackListMchntDTO(BlackListMchntDTO blackListMchntDTO) {
		this.blackListMchntDTO = blackListMchntDTO;
	}

	public BlackListMchntDTO getBlackListMchntDTO() {
		return blackListMchntDTO;
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
		if(blackListMchntQueryDTO ==null){
			blackListMchntQueryDTO =new BlackListMchntQueryDTO();
		}
		ListPageInit(null,blackListMchntQueryDTO);
		pageDataDTO=(PageDataDTO)sendService("2012111401",blackListMchntQueryDTO).getDetailvo();
		
		if(hasErrors()){
			return "input";
		}
		return "list";
	}

	public String delete() throws Exception{
		List<BlackListMchntDTO> blackListMchntDTOList=new ArrayList<BlackListMchntDTO>();
		for(String mchntNo:mchntNoList){
			BlackListMchntDTO dto=new BlackListMchntDTO();
			dto.setMchntNo(mchntNo);
			blackListMchntDTOList.add(dto);
		}
		blackListMchntDTO=new BlackListMchntDTO();
		blackListMchntDTO.setBlackListMchntDTOList(blackListMchntDTOList);
		sendService("2012111402",blackListMchntDTO);
		
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
	
	public String insertBlackListMchnt() throws Exception{
		sendService("2012111403",blackListMchntDTO);
		return inquiry();
	}
	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	

	public void setMchntNoList(List<String> mchntNoList) {
		this.mchntNoList = mchntNoList;
	}

	public List<String> getMchntNoList() {
		return mchntNoList;
	}
}
