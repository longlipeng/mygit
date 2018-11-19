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
			logger.info("֤����Ч�ڵ����񣡣���" + (new Date()).toString());
			AttachInfo attachInfo = new AttachInfo();
			//attachInfo.setUpdateDate(DateUtil.getCurrentTime());
			attachInfo.setValidity(DateUtil.getStringDate());
			// 0֤����Ч���ѵ���,1��Ҫ���ڣ���ʮ���ڣ���2δ����
			//δ����
			attachInfo.setIsvalidity("2");
			attachInfoMapper.updateByValiditySelective(attachInfo);
			//�޸��ѹ���
			attachInfo.setIsvalidity("0");
			attachInfoMapper.updateByValiditySelective(attachInfo);
			//�޸Ľ�Ҫ����
			attachInfo.setIsvalidity("1");
			attachInfoMapper.updateByValiditySelective(attachInfo);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("����Attach��"+e.getMessage());
		}
		
		try {
			CompanyInfo info = new CompanyInfo();
			//info.setModifyTime(DateUtil.getCurrentTime());
			info.setCorpCredValidity(DateUtil.getStringDate());
			// 0֤����Ч���ѵ���,1��Ҫ���ڣ���ʮ���ڣ���2δ����
			//δ����
			info.setIsvalidity("2");
			companyInfoMapper.updateByValidity(info);
			//�޸��ѹ���
			info.setIsvalidity("0");
			companyInfoMapper.updateByValidity(info);
			//�޸Ľ�Ҫ����
			info.setIsvalidity("1");
			companyInfoMapper.updateByValidity(info);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("����CompanyInfo��"+e.getMessage());
		}
		
		

	}

	public static void main(String[] args) {
		System.out.println("Test start.");
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		// ��������ļ��н�startQuertz bean��lazy-init����Ϊfalse ����ʵ����
		// batchIssueCard qj=(batchIssueCard)ctx.getBean("batchIssueCard");
		// qj.work();
		System.out.println("��������Ϣ��");
		Scanner input = new Scanner(System.in);
		int x = input.nextInt();
		System.out.println(x);
		System.out.print("Test end..");

	}

}
