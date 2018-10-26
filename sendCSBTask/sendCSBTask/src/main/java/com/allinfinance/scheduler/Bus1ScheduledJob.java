package com.allinfinance.scheduler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.allinfinance.service.GetDataService;

import service.CopProPertiesUtil;
import service.FileUtil;

/**
 * ��ʱ�����ļ�����
 * 
 * @author taojiajun
 *
 */
public class Bus1ScheduledJob {
	@Autowired
	GetDataService getDataService;
	private static Logger logger = LoggerFactory.getLogger(Bus1ScheduledJob.class);

	public void bus1Job() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(new Date());
		logger.info("��ʱ�����ļ�����ʼ��>>>>>>>>>" + time);
		Map<String, Object> condition = new HashMap<>();
		String registeredCard = getDataService.selectGetIsRegisterCardInfo(condition);// ��ȡ���˼�������Ϣ
		Map<String, Object> condition2 = new HashMap<>();
		String notRegisteredCard = getDataService.selectGetNotIsRegisterCardInfo(condition2);// ��ȡ���˲���������Ϣ
		Map<String, Object> condition3 = new HashMap<>();
		String singleRecharge= getDataService.selectSingleTxnInfo(condition3);//���ʳ�ֵ������Ϣ
		Map<String, Object> condition4 = new HashMap<>();
		String selectTransaction= getDataService.selectTransaction(condition4);//������Ϣ  
		String filePath = CopProPertiesUtil.getProperties().getProperty("filePath");
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String fileTime = simpleDateFormat.format(new Date());
		String fileName = "csb" + (Long.parseLong(fileTime)) + ".txt";
		File file = new File(filePath + fileName);
		try {
			FileUtil.createFile(file, registeredCard);
			FileUtil.appendFile(file, notRegisteredCard);
			FileUtil.appendFile(file, singleRecharge);
			FileUtil.appendFile(file, selectTransaction);
		} catch (IOException e) {
			logger.error("�����ļ�ʧ��!!!"+time);
			e.printStackTrace();
		}
		logger.info("�����ļ�������ɣ�>>>>>"+"fileName: "+fileName+">>>>" + format.format(new Date()));
	}

}
