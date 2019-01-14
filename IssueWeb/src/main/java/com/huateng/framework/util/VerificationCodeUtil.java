package com.huateng.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.framework.exception.BizServiceException;

public class VerificationCodeUtil {
	public static Logger logger = Logger.getLogger(VerificationCodeUtil.class);
	public String sendMsg(String telephone,String userName) throws BizServiceException {
		Random random = new Random();
		String sRand = "";
		try {
			// 取随机产生的认证码 (6 位数字 ) 
			for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			}
			logger.info("短信验证码："+sRand);
			//调用短信接口
			SendShortMessageThread sendMsg=new SendShortMessageThread(telephone, ConfigMessage.getTemplet1_No(),sRand);
			sendMsg.start();
			Date date = new Date();
			String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			ServletActionContext.getRequest().getSession().setAttribute("validCode", sRand);
			ServletActionContext.getRequest().getSession().setAttribute("validCodeUsername", userName);
			ServletActionContext.getRequest().getSession().setAttribute("validCodeTime", dateStr);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("发送手机短信接口失败");
		} 
		return sRand;
	}
	
	public long getDatePoor(Date endDate, Date nowDate) {
		 
	    long nd = 1000 * 24 * 60 * 60;
	    long nh = 1000 * 60 * 60;
	    long nm = 1000 * 60;
	    // long ns = 1000;
	    // 获得两个时间的毫秒时间差异
	    long diff = nowDate.getTime() -endDate.getTime() ;
	    // 计算差多少天
	    
//	    long day = diff / nd;
	    // 计算差多少小时
//	    long hour = diff % nd / nh;
	    // 计算差多少分钟
	    long min = diff / nm;
	    // 计算差多少秒//输出结果
	    // long sec = diff % nd % nh % nm / ns;
	    return  min;
	}

}
