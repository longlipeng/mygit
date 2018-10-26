package com.allinfinance.scheduler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;

import com.allinfinance.model.UplaodFlown;
import service.RecordFileUploadService;
/**
 * ��ʱ��ѯÿ���ϴ��ļ���״̬
 * @author taojiajun
 *
 */
public class Bus3ScheduledJob {
	@Autowired
	RecordFileUploadService recordFileUploadService;
	private Logger logger = Logger.getLogger(Bus3ScheduledJob.class);
	public void bus3Job() throws IOException, DocumentException {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime= format.format(new Date());
		logger.info("��ʼ��ѯ�ļ��ϴ�״̬ʱ��---��" +startTime);
		//��ȡ�����ļ����������ݸ�ʽ��������CSB������ƽ̨�ӿ�	
		String flown="";
		try {
			//����Ϊ��������Ψһ��ʶ 310115O7913242100157			
			 flown=UplaodFlown.getFlown();		
			if(!flown.equals("")){
			String result= recordFileUploadService.recordFileUploadState(flown);// fileStatus ����ļ��ϴ�״̬0-�ϴ��ɹ���1-�ϴ�ʧ�ܣ�2-�ϴ��У�3-�ļ�����ɹ� 4-�ļ�������  5-�ļ�����ʧ�ܣ��޷����
			logger.info("����ʱ��---��" + format.format(new Date()));
			logger.info("������ˮ�Ų�CSB�����ļ��������ˮ��>>>>>"+flown+"�ϴ��Ľ��Ϊ��>>>>>>>"+result);
			}
		} catch (Exception e) {
			logger.error("�ļ��ϴ���ѯʧ��>>>>>>>>>>>>>>>"+flown);
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
