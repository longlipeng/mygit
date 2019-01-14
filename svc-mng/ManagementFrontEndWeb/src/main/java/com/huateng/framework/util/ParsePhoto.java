package com.huateng.framework.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;




import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xml.security.utils.Base64;


public class ParsePhoto {
	private Logger logger = Logger.getLogger(this.getClass());
	public void getPhoto(String id) {
		if(StringUtils.isEmpty(id)){
//			logger.error("证件号为空");
			return;
		}
		String pathFile = Config.getImgFilePath() + id;
		String pathjpg = Config.getImgPath() + id +".jpg";
		StringBuffer sb = new StringBuffer();
		BufferedReader bf = null;
		FileInputStream input = null;
		try {
			input = new FileInputStream(pathFile);
			InputStreamReader reader = new InputStreamReader(input);  
	        bf = new BufferedReader(reader);

            sb.append(bf.readLine());  
            while(bf.readLine() != null){  
            	sb.append(bf.readLine());  
            }  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            logger.error("FileNotFoundException!");
            return;
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace(); 
            logger.error("IOException!");
            return;
        }  finally{
				try{
				bf.close();
				input.close();
				}catch (Exception e) {  
	            // TODO Auto-generated catch block  
					e.printStackTrace(); 
//	           	 logger.error("Exception!");
		}
        }
	
		base64ToImage(sb.toString(),pathjpg);
	}
	
	
	public static void base64ToImage(String base64, String path) {// 对字节数组字符串进行Base64解码并生成图片
	    if (StringUtils.isEmpty(base64)){ // 图像数据为空
	        return;
	    }
	    try {
	        // Base64解码
	        byte[] bytes = Base64.decode(base64);
	        for (int i = 0; i < bytes.length; ++i) {
	            if (bytes[i] < 0) {// 调整异常数据
	                bytes[i] += 256;
	            }
	        }
	        // 生成jpeg图片
//	        System.out.println(new String(Hex.encodeHex(bytes)));
	        OutputStream out = new FileOutputStream(path);
	        out.write(bytes);
	        out.flush();
	        out.close();
	        return;
	    } catch (Exception e) {
	        return;
	    }
	}

	
	public static void main(String[] args)
	{
		ParsePhoto p =new ParsePhoto();
		p.getPhoto("33070219630306042X");
	}

}
