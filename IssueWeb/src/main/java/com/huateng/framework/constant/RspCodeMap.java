package com.huateng.framework.constant;

import java.util.HashMap;
import java.util.Map;

public class RspCodeMap {
     public static Map<String,String> rspCodeMap=null;
     public static Map<String,String> getRspCodeMap(){
    	rspCodeMap=new HashMap<String, String>();
    	rspCodeMap.put("00", "返回成功");
     	rspCodeMap.put("01", "查发卡行");
     	rspCodeMap.put("03", "无效商户");
     	rspCodeMap.put("04", "受限商户");
     	rspCodeMap.put("06", "无效合同");
     	rspCodeMap.put("09", "文件不存在");
     	rspCodeMap.put("10", "文件打开错误");
     	rspCodeMap.put("11", "发送信息有误");
     	rspCodeMap.put("12", "无效交易");
     	rspCodeMap.put("13", "无效金额-累计退货金额、调账金额、超限等");
     	rspCodeMap.put("14", "无效卡号");
     	rspCodeMap.put("15", "卡未找到");
     	rspCodeMap.put("16", "卡未激活");
     	rspCodeMap.put("17", "与原交易卡号不符");
     	rspCodeMap.put("20", "无效应答");
     	rspCodeMap.put("21", "账户不匹配");
     	rspCodeMap.put("22", "怀疑操作有误");
     	rspCodeMap.put("23", "不可接受的手续费");
     	rspCodeMap.put("25", "未找到原交易");
     	rspCodeMap.put("26","原交易不成功");
     	rspCodeMap.put("31","路由失败-机构不支持");
     	rspCodeMap.put("36","卡已锁定");
     	rspCodeMap.put("39","交易日期有误");
     	rspCodeMap.put("41","卡已挂失");
     	rspCodeMap.put("42","无效账户 商户合同下未关联账户");
     	rspCodeMap.put("44","卡被注销");
     	rspCodeMap.put("45","卡被冻结");
     	rspCodeMap.put("51","余额不足");
     	rspCodeMap.put("54","过期的卡");
     	rspCodeMap.put("55", "密码错误!");
     	rspCodeMap.put("56", "无此卡记录!");
     	rspCodeMap.put("57", "不允许持卡人进行的交易!");
     	rspCodeMap.put("63", "余额不正确!"); 
     	rspCodeMap.put("64", "与原交易金额不符!");
     	rspCodeMap.put("65", "消费次数超限!");
     	rspCodeMap.put("66", "充值次数超限!");
     	rspCodeMap.put("67", "网上单笔交易金额超限!");
     	rspCodeMap.put("68", "网上当天累计交易金额超限!");
     	rspCodeMap.put("73", "cvv不正确!");
     	rspCodeMap.put("74", "CVV2错误");
     	rspCodeMap.put("75", "密码错误次数超限");
     	rspCodeMap.put("90", "系统正在批处理日切中");
     	rspCodeMap.put("91", "通信失败");
     	rspCodeMap.put("92", "参数配置有误");
     	rspCodeMap.put("94", "重复交易");
     	rspCodeMap.put("96", "系统异常");
     	return rspCodeMap;
     }
}
