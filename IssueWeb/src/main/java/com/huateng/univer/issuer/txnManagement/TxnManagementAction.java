package com.huateng.univer.issuer.txnManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.txnManagement.CardRouteDTO;
import com.huateng.framework.action.BaseAction;

public class TxnManagementAction extends BaseAction{
	private Logger logger = Logger.getLogger(TxnManagementAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardRouteDTO cardRouteDTO = new CardRouteDTO();
	public PageDataDTO pageDataDTO;
	private Integer totalRows=0;
	private List<String> choose = new ArrayList<String>();
	/**
	 * 发行机构
	 */
	List<Map<String, String>> entityList = new ArrayList<Map<String, String>>();
	
	public String cardRouteList(){
		try {
			ListPageInit(null, cardRouteDTO);
			pageDataDTO=(PageDataDTO) sendService(ConstCode.INQUERY_CARD_ROUTE, cardRouteDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
				return INPUT;
			}
			
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	
	public String reCardRouteAdd(){
		cardRouteDTO.setBinVal("");
		entityList = getIssuerLst(cardRouteDTO);
		return "add";
	}
	
	public String addCardRoute(){
		sendService(ConstCode.ADD_CARD_ROUTE, cardRouteDTO).getDetailvo();
		if(hasErrors()){
			entityList = getIssuerLst(cardRouteDTO);
			return INPUT;
		}
		addActionMessage("交易路由信息添加成功！");
		cardRouteDTO.setBinVal("");
		cardRouteDTO.setIssInsIdCd("");
		cardRouteList();
		return "list";
	}
	
	public String delCardRoute(){
		List<CardRouteDTO> cardRouteDTOs = new ArrayList<CardRouteDTO>();
		for (String id : choose) {
			String[] defineIssuerIdString=id.split(",");
			CardRouteDTO cardRouteDTO= new CardRouteDTO();
			cardRouteDTO.setBinVal(defineIssuerIdString[0]);
			cardRouteDTO.setLength(defineIssuerIdString[1]);
			cardRouteDTOs.add(cardRouteDTO);
		}
		sendService(ConstCode.DEL_CARD_ROUTE, cardRouteDTOs.get(0)).getDetailvo();
		if(hasErrors()){
			return INPUT;
		}
		addActionMessage("交易路由信息删除成功！");
		cardRouteList();
		return "list";
	}
	/**
	 * 获得所属发行机构和子发行机构
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getIssuerLst(
			CardRouteDTO cardRouteDTO) {

		List<CardRouteDTO> cDTO = (List<CardRouteDTO>) sendService(
				ConstCode.GET_ISSUER_LIST, cardRouteDTO)
				.getDetailvo();
		List<Map<String, String>> issuerList = new ArrayList<Map<String, String>>();
		for (CardRouteDTO cardRouteDto : cDTO) {
			Map<String, String> issuer = new HashMap<String, String>();
			issuer.put("entityId", cardRouteDto.getIssInsIdCd());
			issuer.put("entityName", cardRouteDto.getInsNm());
			issuerList.add(issuer);
		}
		return issuerList;
	}
	
	public CardRouteDTO getCardRouteDTO() {
		return cardRouteDTO;
	}
	public void setCardRouteDTO(CardRouteDTO cardRouteDTO) {
		this.cardRouteDTO = cardRouteDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public List<Map<String, String>> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<Map<String, String>> entityList) {
		this.entityList = entityList;
	}

	public List<String> getChoose() {
		return choose;
	}

	public void setChoose(List<String> choose) {
		this.choose = choose;
	} 
	
}
