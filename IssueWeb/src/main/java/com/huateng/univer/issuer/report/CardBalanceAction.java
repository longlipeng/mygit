package com.huateng.univer.issuer.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.report.dto.CardBalanceDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.Const;
import com.huateng.framework.util.Config;

public class CardBalanceAction extends BaseAction {
	private Logger logger = Logger.getLogger(CardBalanceAction.class);
	private static final long serialVersionUID = -4617970767242693986L;

	private CardBalanceDTO cardBalanceDTO = new CardBalanceDTO();

	private List<CardBalanceDTO> cardBalanceList = new ArrayList<CardBalanceDTO>();

	private String fileName;// 初始的通过param指定的文件名属性

	private String inputPath;// 指定要被下载的文件路径

	private String downFileName;

	public String inQuery() {
		return "list";
	}

	public String downLoad() throws Exception {
		fileName = this.getName();
		downFileName = this.getDownFileName(fileName);
		inputPath = Config.getCardBalanceListPath() + fileName;
		if (!new File(inputPath).exists()) {
			this.addActionMessage("下载文件不存在!");
			return "list";
		}
		return "success";
	}

	public InputStream getInputStream() throws Exception {
		// 通过 ServletContext，也就是application 来读取数据
		return new FileInputStream(new File(inputPath));

	}

	public String getName() {
		return (Const.CARD_BALANCE + "_"+getUser().getEntityId() + "_"
				+ cardBalanceDTO.getDataDate().replace("-", "") + Const.CSV);
	}

	public String getDownFileName(String fileName) {
		try {
			fileName = new String((Const.CARD_BALANCE_CHS + "_"+getUser().getIssuerName() + "_" +cardBalanceDTO.getDataDate().replace("-", "") + Const.CSV).getBytes("gb2312"), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			this.logger.error(e.getMessage());
		}
		return fileName;

	}

	public CardBalanceDTO getCardBalanceDTO() {
		return cardBalanceDTO;
	}

	public void setCardBalanceDTO(CardBalanceDTO cardBalanceDTO) {
		this.cardBalanceDTO = cardBalanceDTO;
	}

	public List<CardBalanceDTO> getCardBalanceList() {
		return cardBalanceList;
	}

	public void setCardBalanceList(List<CardBalanceDTO> cardBalanceList) {
		this.cardBalanceList = cardBalanceList;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

	public void setDownFileName(String downFileName) {
		this.downFileName = downFileName;
	}

	public String getDownFileName() {
		return downFileName;
	}

}
