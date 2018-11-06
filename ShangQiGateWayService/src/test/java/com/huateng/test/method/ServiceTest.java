/**
 * Classname ServiceTest.java
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
 *		htd033		2012-11-28
 * =============================================================================
 */

package com.huateng.test.method;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.huateng.framework.util.MakeTestType;
import com.huateng.univer.consumer.merchant.biz.service.impl.MerchantServiceImplTest;
import com.huateng.univer.consumer.posparameter.biz.service.impl.TerParameterManagementServiceImplTest;
import com.huateng.univer.issuer.cardLayout.service.impl.CardlayoutServiceImplTest;
import com.huateng.univer.issuer.issuercontract.service.impl.LoyaltyContractServiceTest;
import com.huateng.univer.seller.sellercontract.biz.service.impl.SellerContractServiceImplTest;
import com.huateng.univer.settle.biz.service.impl.SettleServiceImplTest;

/**
 * @author wpf
 * 
 *         针对service测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { TerParameterManagementServiceImplTest.class,
		MerchantServiceImplTest.class, SellerContractServiceImplTest.class,
		SettleServiceImplTest.class, CardlayoutServiceImplTest.class,
		LoyaltyContractServiceTest.class })
@MakeTestType(testType = { "MethodTest" })
public class ServiceTest {

}
