package com.huateng.univer.issuer.sellerManagerment;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.univer.seller.seller.ContractBaseAction;

public class IssuerSellerContractAction extends ContractBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String queryType;

	@Override
	protected void initNameSpace() {
		

	}

	@Override
	protected void inqueryInit() {
		// 合同卖方
		sellerContractQueryDTO
				.setContractType(DataBaseConstant.CONTRACT_ISSUER);
		sellerContractQueryDTO.setContractSeller(getUser().getEntityId());

	}

	@Override
	protected void insertInit() {
		
		sellerContractDTO.setContractType(DataBaseConstant.CONTRACT_ISSUER);
		sellerContractDTO.setContractSeller(getUser().getEntityId());

	}

	// 转向发行机构下营销机构合同查询页面 add by sff
	public String issuerSellerInquery() throws Exception {
		// 查询初始化
		this.inqueryInit();

		ListPageInit(null, sellerContractQueryDTO);
	
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.SELLER_CONTRACT_SERVICE_INQUERY,
				sellerContractQueryDTO).getDetailvo();
		if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
			totalRows=pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		
		queryType="infoMng";
		
		return "query";
	}
	
	//营销合同期限管理查询
	public String issuerSellerInqueryByDeadline() throws Exception {
		// 查询初始化
		this.inqueryInit();

		ListPageInit(null, sellerContractQueryDTO);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		Date date=new Date();
		String startDate=df.format(date);
		sellerContractQueryDTO.setStartDate(startDate);
		date.setMonth(date.getMonth()+1);
		String endDate=df.format(date);
		sellerContractQueryDTO.setEndDate(endDate);
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.SELLER_CONTRACT_SERVICE_INQUERY,
				sellerContractQueryDTO).getDetailvo();
		if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
			totalRows=pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		
		queryType="deadlineMng";
		
		return "query";
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
	
	
	
}
