package com.huateng.startup.listener;

import java.util.Date;

import org.apache.log4j.Logger;

import com.huateng.system.util.CommonFunction;

/**
 * 
 * @author 
 *
 */
public class FocusQuartz {
	private static Logger log = Logger.getLogger(FocusQuartz.class);
	
	public void focus(){
		log.info(new Date() + "执行人行集中缴存备款回填自动查询备款状态");
		//查询人行缴存备款失败数量
		String sql1 = "select count(FOCUS_STATUS) from TBL_FOCUS_RESERVE where FOCUS_STATUS = '1'";
		//查询人行缴存备款成功数量
		String sql2 = "select count(FOCUS_STATUS) from TBL_FOCUS_RESERVE where FOCUS_STATUS = '0'";
		//查询人行缴存备款成功失败总数量
		String sql3 = "select count(FOCUS_STATUS) from TBL_FOCUS_RESERVE where 1 = 1";
		String paymentCount1 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
		String paymentCount2 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql2);
		String paymentCount3 = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql3);
		
		System.out.println("备款失败交易笔数: " + paymentCount1);
		System.out.println("备款成功交易笔数: " + paymentCount2);
		System.out.println("备款总笔数: " + paymentCount3);
		log.info(new Date() + "执行自动结束");
	}
	
}
