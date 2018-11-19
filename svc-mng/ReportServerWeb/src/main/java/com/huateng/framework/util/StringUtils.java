/**
 * Classname StringUtils.java
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
 *		htd033		2012-11-12
 * =============================================================================
 */

package com.huateng.framework.util;

/**
 * @author wpf
 * 
 */
public class StringUtils {

	/**
	 * 一个公共的首字母大写的方法
	 * 
	 * @param code
	 * @return
	 */
	public static String abc2Abc(String code) {
		char[] chars = code.toCharArray();
		if (chars[0] > 'z' || chars[0] < 'a') {
			return code;
		}
		chars[0] = (char) (chars[0] - 'a' + 'A');
		return new String(chars);
	}

}
