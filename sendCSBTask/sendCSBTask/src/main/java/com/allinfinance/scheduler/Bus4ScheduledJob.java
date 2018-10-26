package com.allinfinance.scheduler;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.allinfinance.service.GetDataService;

import service.FileUtil;
/**
 * 生成历史文件
 * @author taojiajun
 *
 */
public class Bus4ScheduledJob {
	@Autowired
	GetDataService getDataService;
	private Logger logger=LoggerFactory.getLogger(Bus4ScheduledJob.class);
	public  void bus4Job(){
		String his= getDataService.getHistoryCard();
		String fileName="/home/login01/danyongtuCSB/history.txt";
		File file = new File(fileName);
		try {
			FileUtil.createFile(file, his);
			logger.info("历史卡文件已经生成>>>>>>"+fileName);
		} catch (IOException e) {
			logger.error("历史卡文件生成失败>>>>>>"+fileName);
			e.printStackTrace();
		}
	}

}
