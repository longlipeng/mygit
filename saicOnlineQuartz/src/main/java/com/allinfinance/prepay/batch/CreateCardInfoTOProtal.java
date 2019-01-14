package com.allinfinance.prepay.batch;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.allinfinance.prepay.mapper.svc_mng.SellOrderMapper;
import com.allinfinance.prepay.model.PortalSendCardInfo;
import com.allinfinance.prepay.utils.CSVUtil;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.SFTPUtil;
import com.jcraft.jsch.SftpException;

public class CreateCardInfoTOProtal {
	transient	private  Logger log=LoggerFactory.getLogger(CreateCardInfoTOProtal.class);
	@Autowired
	SellOrderMapper sellOrderMapper;
	String SFTPFilePath;
	String SFTPHost;
	String SFTPPort;
	String SFTPUserName;
	String SFTPPassWord;
	String SFTPLocalFilePath;
	/***
	 * 每天的制卡的信息发往protal  sftp 形式
	 */
	public void job(){
		Map<String,Object> condition=new HashMap<String,Object>();
		condition.put("date",DateUtil.getCurrentDateToStr().substring(0,8));
		condition.put("orderType","'10000011','10000001'");//订单类型
		List<PortalSendCardInfo> messageInfo=sellOrderMapper.queryCardInfoToProtal(condition);
		List<String []> csvContent=new ArrayList<String[]>();
		for (PortalSendCardInfo info : messageInfo) {
			String[] onceLineInfo={info.getCreateTime(),info.getModifyTime(),info.getCardHolderName(),
					info.getPhoneNo(),info.getIdNo(),info.getCardNo(),"","",info.getComment()};
			csvContent.add(onceLineInfo);
		}
		//文件格式  card_yyyyMMdd.csv  20170324
		String fileName="card_"+DateUtil.date2String(new Date())+".csv";
		CSVUtil.createCSV(SFTPLocalFilePath,fileName, null, csvContent);
		log.info("本地文件创建完成：{}",SFTPLocalFilePath+fileName);
		SFTPUtil sftp = new SFTPUtil(SFTPUserName,SFTPPassWord,SFTPHost,Integer.parseInt(SFTPPort));
		sftp.login();
		try {
			Thread.sleep(7000);
			sftp.upload(SFTPFilePath, SFTPLocalFilePath+fileName);
		} catch (Exception e) {
			log.info("sftp上传文件失败：{}"+SFTPLocalFilePath+fileName);
			e.printStackTrace();
		} 
		sftp.logout();
	}
	public String getSFTPFilePath() {
		return SFTPFilePath;
	}
	public void setSFTPFilePath(String sFTPFilePath) {
		SFTPFilePath = sFTPFilePath;
	}
	public String getSFTPHost() {
		return SFTPHost;
	}
	public void setSFTPHost(String sFTPHost) {
		SFTPHost = sFTPHost;
	}
	public String getSFTPPort() {
		return SFTPPort;
	}
	public void setSFTPPort(String sFTPPort) {
		SFTPPort = sFTPPort;
	}
	public String getSFTPUserName() {
		return SFTPUserName;
	}
	public void setSFTPUserName(String sFTPUserName) {
		SFTPUserName = sFTPUserName;
	}
	public String getSFTPPassWord() {
		return SFTPPassWord;
	}
	public void setSFTPPassWord(String sFTPPassWord) {
		SFTPPassWord = sFTPPassWord;
	}
	public String getSFTPLocalFilePath() {
		return SFTPLocalFilePath;
	}
	public void setSFTPLocalFilePath(String sFTPLocalFilePath) {
		SFTPLocalFilePath = sFTPLocalFilePath;
	}

}
