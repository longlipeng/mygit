package com.allinfinance.scheduler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allinfinance.model.TbDictInfoExample;
import com.allinfinance.model.UplaodFlown;
import com.allinfinance.service.TestService;

import service.CopProPertiesUtil;
import service.ReadFile;
import service.RecordFileUploadService;


/**
 * @goal ��ʱ������;���ƽ̨�����ļ�
 * @author tjj
 * @date 
 */

public class Bus2ScheduledJob {
	RecordFileUploadService recordFileUploadService;
	private Logger logger = Logger.getLogger(Bus2ScheduledJob.class);
	public void bus2Job() throws IOException, DocumentException {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime= format.format(new Date());
		logger.info("��ʼ����CBSʱ��---��" +startTime);
		//��ȡ�����ļ����������ݸ�ʽ��������CSB������ƽ̨�ӿ�
		ReadFile rf = new ReadFile();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String fileTime = simpleDateFormat.format(new Date());
		String fileDir = CopProPertiesUtil.getProperties().getProperty("filePath");
		String filePath = fileDir+"csb" + (Long.parseLong(fileTime)) + ".txt";
		//filePath="F:/csb20180607.txt";
		try {
			//����Ϊ��������Ψһ��ʶ 310115O7913242100157
			UplaodFlown.setFlown("");//ÿ�췢��֮ǰ ��һ�δ��͵���ˮ�� �ÿ�
			String flown= rf.readFromFile(CopProPertiesUtil.getProperties().getProperty("uniqueNo"),filePath);//�����ϴ��ļ���ˮ��
			UplaodFlown.setFlown(flown);
			logger.info("����ʱ��---��" + format.format(new Date()));
		} catch (Exception e) {
			logger.error("�ļ�����ʧ��>>>>>>>>>>>>>>>"+filePath);
			e.printStackTrace();
		}
	}
	public RecordFileUploadService getRecordFileUploadService() {
		return recordFileUploadService;
	}
	public void setRecordFileUploadService(RecordFileUploadService recordFileUploadService) {
		this.recordFileUploadService = recordFileUploadService;
	}
	

}
