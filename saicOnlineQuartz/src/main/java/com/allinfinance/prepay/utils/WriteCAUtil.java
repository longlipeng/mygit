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
	 * 判断路径是否存在
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
						logger.info(file.getAbsolutePath()+"文件已存在,进行备份处理！");
						File bakFile = new File(file.getAbsolutePath()+""+System.currentTimeMillis());
						file.renameTo(bakFile);
						logger.info(file.getAbsolutePath()+"文件已备份！路径为:"+bakFile.getAbsolutePath());
					}
					return "S";
				} else {
					res = "E路径指向不是文件夹";
				}
			} else {
				res = "E路径错误";
			}
		} else {
			res = "E不存在";
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
			logger.info("文件写入成功！具体路径为:" + file.getAbsolutePath());
		} else {
			logger.info("文件写入失败，原因:" + isExist());
		}
	}

}
