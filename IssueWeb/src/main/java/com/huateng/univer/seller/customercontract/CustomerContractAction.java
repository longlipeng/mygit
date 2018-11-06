package com.huateng.univer.seller.customercontract;

import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.univer.seller.seller.ContractBaseAction;

/**
 * 客户合同action
 * 
 * @author xxl
 * 
 */
public class CustomerContractAction extends ContractBaseAction {

	private static final long serialVersionUID = -4876235911392616617L;

	/**
	 * 查询初始化
	 */
	protected void inqueryInit() {
		// 合同卖方
		sellerContractQueryDTO.setContractSeller(getUser().getEntityId());
		// 客户合同类型 :3
		sellerContractQueryDTO
				.setContractType(DataBaseConstant.CONTRACT_CUSTOMER);
	}

	protected void insertInit() {
		sellerContractDTO
				.setContractType(DataBaseConstant.CONTRACT_CUSTOMER);
		sellerContractDTO.setContractSeller(getUser().getEntityId());
	}

	protected void initNameSpace(){
		nameSpace = "customerContract";
	}
}
