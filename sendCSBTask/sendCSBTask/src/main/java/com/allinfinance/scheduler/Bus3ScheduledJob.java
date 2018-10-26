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
 * 定时查询每天上传文件的状态
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
		logger.info("开始查询文件上传状态时间---：" +startTime);
		//读取本地文件并处理数据格式，并发送CSB请求监管平台接口	
		String flown="";
		try {
			//参数为联网发行唯一标识 310115O7913242100157			
			 flown=UplaodFlown.getFlown();		
			if(!flown.equals("")){
			String result= recordFileUploadService.recordFileUploadState(flown);// fileStatus 检查文件上传状态0-上传成功，1-上传失败，2-上传中，3-文件处理成功 4-文件处理中  5-文件处理失败，无法入库
			logger.info("结束时间---：" + format.format(new Date()));
			logger.info("根据流水号查CSB处理文件结果：流水号>>>>>"+flown+"上传的结果为：>>>>>>>"+result);
			}
		} catch (Exception e) {
			logger.error("文件上传查询失败>>>>>>>>>>>>>>>"+flown);
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
