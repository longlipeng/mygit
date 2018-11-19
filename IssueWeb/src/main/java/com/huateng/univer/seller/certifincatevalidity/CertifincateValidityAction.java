package com.huateng.univer.seller.certifincatevalidity;

import java.io.IOException;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.customer.CertifincateValidityQueryDTO;
import com.huateng.univer.entity.EntityBaseAction;

public class CertifincateValidityAction extends EntityBaseAction{
	private String list="list";
	private CertifincateValidityQueryDTO dto = new CertifincateValidityQueryDTO();
	private Integer totalRows;
	private PageDataDTO pageDataDTO = new PageDataDTO();
	
	public String inquery() {
		try {
			ListPageInit(null, dto);
			pageDataDTO=(PageDataDTO) sendService("2017080401", dto).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}else{
				totalRows=0;
			}
			if(hasActionErrors()) {
				this.addActionError("查询证件信息失败！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public void judgeOutdateLience() {
		StringBuffer existFlag=new StringBuffer(); 
		this.inquery();
		try {
			if(totalRows==null) {
				existFlag.append("{\"sum\":0}");
			}else {
				existFlag.append("{\"sum\":"+totalRows+"}");
			}
			getResponse().setContentType("application/json; charset=utf-8");
			getResponse().setCharacterEncoding("utf-8");
			getResponse().getWriter().println(existFlag.toString());
			getResponse().getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	@Override
	public void initEntityId() throws Exception {
		entityId = dto.getEntityId();
	}

	@Override
	public void initNameSpace() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String reloadForEntity() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public CertifincateValidityQueryDTO getDto() {
		return dto;
	}

	public void setDto(CertifincateValidityQueryDTO dto) {
		this.dto = dto;
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
