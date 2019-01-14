package com.huateng.univer.synch.CRMSynch;

import com.allinfinance.univer.seller.customer.CustomerDTO;

public interface CustomerSynchCRM {
	public void syncToCRM(CustomerDTO dto) throws Exception;

}
