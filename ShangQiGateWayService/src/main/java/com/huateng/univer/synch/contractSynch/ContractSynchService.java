/**
 * Classname ContractSynchService.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		lfr		2013-4-15
 * =============================================================================
 */

package com.huateng.univer.synch.contractSynch;

import java.util.List;

import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.AcctypeContract;
import com.huateng.framework.ibatis.model.ConsumerContract;
import com.huateng.univer.synch.dto.MerchantContractSynchDTO;

/**
 * @author lfr
 *
 */
public interface ContractSynchService {
	
	public String contractInfoSynch(MerchantContractSynchDTO merchantContractSynchDTO) throws BizServiceException;
	
	public ConsumerContract insertContract(ConsumerContract contract)throws BizServiceException;
	
	public void insertAccTypeContract(AcctypeContract acctypeContract)throws BizServiceException;
	
	public MerchantContractSynchDTO checkContractData(MerchantContractSynchDTO merchantContractSynchDTO)throws BizServiceException;
	
	public void checkMchntContract(ConsumerContract contract)  throws BizServiceException;
	
	public List<ConsumerContract> getConsumerContractListForAdd(
			ConsumerContract contract) throws BizServiceException;
}
