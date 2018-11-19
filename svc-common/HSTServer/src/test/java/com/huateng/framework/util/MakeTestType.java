/**
 * Classname MakeTestType.java
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
 *		htd033		2012-12-20
 * =============================================================================
 */

package com.huateng.framework.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author wpf
 * 
 *         提示该类已经被加入到某个测试清单当中
 */
@Retention(RetentionPolicy.RUNTIME)
@MakeTestType(testType={"MakeTestType"})
public @interface MakeTestType {
	/**
	 * 默认为无测试清单
	 * 
	 * @return
	 */
	public String[] testType() default { "none" };
}
