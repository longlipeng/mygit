package com.allinfinance.service.uploadFile.dto;

import java.io.File;
import java.util.List;

public class UploadCardFileDTO {
	private String  filename;
	private String  filepath;

	private String binType;
	private String entityId;
	private String orderId;
	private String cardNo;
	private List<String> cardNos;
	private File cardNoFile;
	
	//重复卡号
	private List<String> repeatedCardNo;
	
	
	public List<String> getRepeatedCardNo() {
		return repeatedCardNo;
	}
	public void setRepeatedCardNo(List<String> repeatedCardNo) {
		this.repeatedCardNo = repeatedCardNo;
	}
	public File getCardNoFile() {
		return cardNoFile;
	}
	public void setCardNoFile(File cardNoFile) {
		this.cardNoFile = cardNoFile;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getBinType() {
		return binType;
	}
	public void setBinType(String binType) {
		this.binType = binType;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public List<String> getCardNos() {
		return cardNos;
	}
	public void setCardNos(List<String> cardNos) {
		this.cardNos = cardNos;
	}
	
	
	
}
