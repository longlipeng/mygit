package com.allinfinance.prepay.batch;

import java.util.Date;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.allinfinance.prepay.mapper.svc_mng.AttachInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.CompanyInfoMapper;
import com.allinfinance.prepay.model.AttachInfo;
import com.allinfinance.prepay.model.CompanyInfo;
import com.allinfinance.prepay.utils.DateUtil;

public class ValidityCheckForIDCard {
	private static Logger logger = Logger
			.getLogger(ValidityCheckForIDCard.class);
	@Autowired
	private AttachInfoMapper attachInfoMapper;
	@Autowired
	private CompanyInfoMapper companyInfoMapper;

	public void CheckInfo() {
		try {
			logger.info("证件有效期的任务！！！" + (new Date()).toString());
			AttachInfo attachInfo = new AttachInfo();
			//attachInfo.setUpdateDate(DateUtil.getCurrentTime());
			attachInfo.setValidity(DateUtil.getStringDate());
			// 0证件有效期已到期,1将要到期（三十天内），2未过期
			//未过期
			attachInfo.setIsvalidity("2");
			attachInfoMapper.updateByValiditySelective(attachInfo);
			//修改已过期
			attachInfo.setIsvalidity("0");
			attachInfoMapper.updateByValiditySelective(attachInfo);
			//修改将要过期
			attachInfo.setIsvalidity("1");
			attachInfoMapper.updateByValiditySelective(attachInfo);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("更新Attach错："+e.getMessage());
		}
		
		try {
			CompanyInfo info = new CompanyInfo();
			//info.setModifyTime(DateUtil.getCurrentTime());
			info.setCorpCredValidity(DateUtil.getStringDate());
			// 0证件有效期已到期,1将要到期（三十天内），2未过期
			//未过期
			info.setIsvalidity("2");
			companyInfoMapper.updateByValidity(info);
			//修改已过期
			info.setIsvalidity("0");
			companyInfoMapper.updateByValidity(info);
			//修改将要过期
			info.setIsvalidity("1");
			companyInfoMapper.updateByValidity(info);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("更新CompanyInfo错："+e.getMessage());
		}
		
		

	}

	public static void main(String[] args) {
		System.out.println("Test start.");
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		// 如果配置文件中将startQuertz bean的lazy-init设置为false 则不用实例化
		// batchIssueCard qj=(batchIssueCard)ctx.getBean("batchIssueCard");
		// qj.work();
		System.out.println("请输入信息：");
		Scanner input = new Scanner(System.in);
		int x = input.nextInt();
		System.out.println(x);
		System.out.print("Test end..");

	}

}
