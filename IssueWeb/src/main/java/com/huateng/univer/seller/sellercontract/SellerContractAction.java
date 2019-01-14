package com.huateng.univer.seller.sellercontract;

import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.univer.seller.seller.ContractBaseAction;

/**
 * 营销机构合同action
 * 
 * @author xxl
 * 
 */
public class SellerContractAction extends ContractBaseAction {

	private static final long serialVersionUID = -8593142441860259956L;

	/**
	 * 查询初始化
	 */
	protected void inqueryInit() {
		// 合同卖方
		sellerContractQueryDTO.setContractType(DataBaseConstant.CONTRACT_SELLER);
		sellerContractQueryDTO.setContractSeller(getUser().getEntityId());
	}

	protected void insertInit() {
		sellerContractDTO.setContractType(DataBaseConstant.CONTRACT_SELLER);
		sellerContractDTO.setContractSeller(getUser().getEntityId());
	}
	
	protected void initNameSpace(){
		nameSpace = "sellerConstract";
	}
	
}
