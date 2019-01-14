package com.allinfinance.shangqi.gateway.dto;

import java.io.File;
import java.util.Map;

import com.allinfinance.framework.dto.BaseDTO;

public class BatchCardHolderMessageDTO extends BaseDTO{
	//private byte[] fileContents;
	private File batchFile;
	private Map<Integer,String> fileMap ;
	private String countNum;
	
	// ---lk---
	private int typeKH;
		
	public int getTypeKH() {
		return typeKH;
	}

	public void setTypeKH(int typeKH) {
		this.typeKH = typeKH;
	}

	public Map<Integer, String> getFileMap() {
		return fileMap;
	}

	public void setFileMap(Map<Integer, String> fileMap) {
		this.fileMap = fileMap;
	}

	public File getBatchFile() {
		return batchFile;
	}

	public void setBatchFile(File batchFile) {
		this.batchFile = batchFile;
	}

	public String getCountNum() {
		return countNum;
	}

	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}
	
	
	
	
}
