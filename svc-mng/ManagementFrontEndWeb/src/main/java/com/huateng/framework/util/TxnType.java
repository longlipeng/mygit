package com.huateng.framework.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TxnType implements Serializable {
	private static final long serialVersionUID = -2183417610227810765L;

	private static Map<String, String> map = new HashMap<String, String>();
	
	static {
		map.put("2095", "预授权完成冲正");
		map.put("2105", "POS消费冲正");
		map.put("4015", "预授权撤销冲正");
		map.put("4095", "预授权完成撤销冲正");
		map.put("4105", "POS消费撤销冲正");
		map.put("1095", "预授权完成");
		map.put("1105", "POS消费");
		map.put("5205", "退货");
		map.put("1205", "销户");
		map.put("1075", "挂失");
		map.put("1306", "卡账务调整(偿还)");
		map.put("1305", "卡账务调整(扣款)");
		map.put("3095", "预授权完成撤销");
		map.put("3105", "POS消费撤销");
		map.put("0002", "售卡充值");
		map.put("0004", "订单充值");
		map.put("0005", "订单充值");
		map.put("0006", "订单赎回");
		map.put("2075", "解挂");
		map.put("1065", "锁定");
		map.put("2065", "解锁");
		map.put("1335", "卡密重置");
		map.put("7125", "portal充值");
		map.put("1325", "延期");
		map.put("7055", "单卡赎回");
		map.put("1115", "食堂充值");
		map.put("7225", "批量充值");
		map.put("1105_1", "中石油转充");
		map.put("8055", "不记名卡逾期注销");
	}

	public static Map<String, String> getMap() {
		return map;
	}
}
