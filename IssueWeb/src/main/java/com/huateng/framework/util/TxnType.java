package com.huateng.framework.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TxnType implements Serializable {
	private static final long serialVersionUID = -2183417610227810765L;

	private static Map<String, String> map = new HashMap<String, String>();
	
	static {
		map.put("G000", "卡批量查询");
		map.put("1035", "卡片信息设置");
		map.put("1345", "支付网关密码修改");
		map.put("1005", "支付网关消费登记");
		map.put("2005", "支付网关消费确认");
		map.put("5005", "支付网关退货");
		map.put("7005", "支付网关单笔充值");
		map.put("1045", "转入账户");
		map.put("1055", "转出账户");
		map.put("7035", "批量激活未激活销毁");
		map.put("1105", "网上消费");
		map.put("2105", "网上消费冲正");
		map.put("7125", "单笔管理平台充值");
		map.put("2015", "预授权冲正");
		map.put("2095", "预授权完成冲正");
		map.put("2105", "POS消费冲正");
		map.put("4015", "预授权撤销冲正");
		map.put("4095", "预授权完成撤销冲正");
		map.put("4105", "POS消费撤销冲正");
		map.put("7405", "单笔充值撤销冲正");
		map.put("1025", "POS查询");
		map.put("1015", "预授权交易");
		map.put("1095", "预授权完成");
		map.put("1105", "POS消费");
		map.put("5105", "退货");
		map.put("5205", "退货申请");
		map.put("7105", "单笔充值");
		map.put("7015", "激活");
		map.put("7115", "注销");
		map.put("1065", "锁定");
		map.put("2065", "解锁");
		map.put("1165", "冻结");
		map.put("2165", "解冻");
		map.put("1205", "换卡");
		map.put("1075", "挂失");
		map.put("2075", "解挂");
		map.put("1306", "卡账务调整(偿还)");
		map.put("2315", "卡安全信息查询");
		map.put("1315", "卡安全信息设置");
		map.put("1325", "卡延期");
		map.put("1335", "卡密重置");
		map.put("1305", "卡账务调整(扣款)");
		map.put("7025", "激活确认");
		map.put("7205", "单笔充值确认");
		map.put("3015", "预授权撤销");
		map.put("3095", "预授权完成撤销");
		map.put("3105", "POS消费撤销");
		map.put("7305", "单笔充值撤销");
		map.put("M010", "POS签到");
		map.put("M000", "TMK下载");
		map.put("M020", "参数下载");
		map.put("M030", "签退");
		map.put("M040", "卡bin下载");
		map.put("M050", "IC卡参数下载");
		map.put("M060", "POS发起的批结算");
		map.put("M070", "POS发起的批上送");
		map.put("M080", "POS发起的批上送结束");
		map.put("0001", "制卡");
		map.put("0002", "订单充值激活");
		map.put("0003", "订单激活");
		map.put("0004", "订单充值");
		map.put("0005", "订单充值");
		map.put("0006", "订单赎回");
	}

	public static Map<String, String> getMap() {
		return map;
	}
}
