package com.huateng.univer.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;
import com.huateng.framework.action.BaseAction;

public class PageAction extends BaseAction{
	private Logger logger = Logger.getLogger(PageAction.class);
	
	private PageQueryDTO pageQueryDTO = new PageQueryDTO();
	
	private PageDataDTO pageDataDTO;
	
	private Integer totalRows = 0;
	
	private static final long serialVersionUID = 1L;

	public String list(){
		try{			
			ListPageInit(null,pageQueryDTO);
			
			pageDataDTO  = new PageDataDTO();
			pageQueryDTO.getFirstCursorPosition();
			totalRows = 90;
			
			List<Map<?,?>> list = new ArrayList<Map<?,?>>();
			
			for (int i =1;i<=totalRows;i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("productId", i+"");
				map.put("productName", "产品"+i);
				map.put("cardType", "卡类型"+i);
				map.put("productType", "产品类型"+i);
				map.put("prodStat", "产品状态"+i);
				map.put("entityName", this.getUser().getIssuerName());
				list.add(map);
			}
			pageDataDTO.setData(list);
		}catch(Exception e){
			this.logger.error(e.getMessage());
			
		}
		return "list";
		
	}

	public PageQueryDTO getPageQueryDTO() {
		return pageQueryDTO;
	}

	public void setPageQueryDTO(PageQueryDTO pageQueryDTO) {
		this.pageQueryDTO = pageQueryDTO;
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
	
	
	
}
