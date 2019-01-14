package com.allinfinance.prepay;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.service.MngAccountInfoService;

public class TestTransaction {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
		MngAccountInfoService mngAccountInfoService=(MngAccountInfoService) ctx.getBean("mngAccountInfoServiceImpl");
		try {
			mngAccountInfoService.insertAccInfoAndBinding("111", "1");
		} catch (BizServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
