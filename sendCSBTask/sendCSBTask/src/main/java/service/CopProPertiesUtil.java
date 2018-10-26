package service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author taojiajun
 *
 */
public class CopProPertiesUtil {
	private static final Logger logger=LoggerFactory.getLogger(CopProPertiesUtil.class);
	private static Properties properties;
	public static void init(){
		logger.info("��ʼ������ҵ�����ļ�...");		
		InputStream in =null;
		try {
			Properties sp = System.getProperties();		
			String home = sp.getProperty("IPOS_OPTS_QUAR");
			String settingFile=home+"/copInfo.properties";
			File f = new File(settingFile);
			if (!f.exists())
			{
				System.out.println("copInfo.properties�ļ������ڣ�������ȷ����");
				System.exit(1);
			}
			in= new BufferedInputStream(new FileInputStream(  
			        new File(settingFile)));
			properties=new Properties();  
		
//			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("D:/MyEclipse/copInfo.properties")); 			
			properties.load(in); 
			logger.info("��ʼ������ҵ�����ļ�:{}�ɹ���","copInfo.properties");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("��ʼ������ҵ�����ļ�����");
			System.exit(1);
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		}
	}
	public static Properties getProperties() {
		return properties;
	}

}
