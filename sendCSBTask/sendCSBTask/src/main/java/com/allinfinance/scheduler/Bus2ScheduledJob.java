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
 * @goal 定时给单用途监管平台传送文件
 * @author tjj
 * @date 
 */

public class Bus2ScheduledJob {
	RecordFileUploadService recordFileUploadService;
	private Logger logger = Logger.getLogger(Bus2ScheduledJob.class);
	public void bus2Job() throws IOException, DocumentException {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime= format.format(new Date());
		logger.info("开始发送CBS时间---：" +startTime);
		//读取本地文件并处理数据格式，并发送CSB请求监管平台接口
		ReadFile rf = new ReadFile();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String fileTime = simpleDateFormat.format(new Date());
		String fileDir = CopProPertiesUtil.getProperties().getProperty("filePath");
		String filePath = fileDir+"csb" + (Long.parseLong(fileTime)) + ".txt";
		//filePath="F:/csb20180607.txt";
		try {
			//参数为联网发行唯一标识 310115O7913242100157
			UplaodFlown.setFlown("");//每天发送之前 上一次传送的流水号 置空
			String flown= rf.readFromFile(CopProPertiesUtil.getProperties().getProperty("uniqueNo"),filePath);//返回上传文件流水号
			UplaodFlown.setFlown(flown);
			logger.info("结束时间---：" + format.format(new Date()));
		} catch (Exception e) {
			logger.error("文件传输失败>>>>>>>>>>>>>>>"+filePath);
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
