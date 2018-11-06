package com.allinfinance.prepay.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteCAUtil {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private FileOutputStream out = null;
	private String filePath;
	private String fileName;
	private File file;

	public WriteCAUtil(String filePath, String fileName) {
		super();
		this.filePath = filePath;
		this.fileName = fileName;
	}

	/**
	 * �ж�·���Ƿ����
	 * 
	 * @return
	 */
	public String isExist() {
		String res = null;
		file = new File(filePath);
		if (file != null) {
			if (file.exists()) {
				if (file.isDirectory()) {
					file = new File(filePath + "/" + fileName);
					if (file.exists()) {
						logger.info(file.getAbsolutePath()+"�ļ��Ѵ���,���б��ݴ���");
						File bakFile = new File(file.getAbsolutePath()+""+System.currentTimeMillis());
						file.renameTo(bakFile);
						logger.info(file.getAbsolutePath()+"�ļ��ѱ��ݣ�·��Ϊ:"+bakFile.getAbsolutePath());
					}
					return "S";
				} else {
					res = "E·��ָ�����ļ���";
				}
			} else {
				res = "E·������";
			}
		} else {
			res = "E������";
		}
		return res;
	}

	public void writeBytes(byte[] ca) {
		if ("S".equals(isExist())) {
			try {
				out = new FileOutputStream(file);
				out.write(ca);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("�ļ�д��ɹ�������·��Ϊ:" + file.getAbsolutePath());
		} else {
			logger.info("�ļ�д��ʧ�ܣ�ԭ��:" + isExist());
		}
	}

}
