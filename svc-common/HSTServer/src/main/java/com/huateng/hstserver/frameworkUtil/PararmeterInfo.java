package com.huateng.hstserver.frameworkUtil;

import org.apache.commons.lang.StringUtils;

public class PararmeterInfo {

	/**
	 * 获得卡片类型
	 * 
	 * @param type
	 * @return
	 */
	public static String getCardType(String type) {
		if (StringUtils.isBlank(type)) {
			return "";
		}
		if (type.equals("1")) {
			return "充值卡";
		} else if (type.equals("2")) {
			return "礼品卡";
		} else {
			return "其他卡";
		}
	}


}
