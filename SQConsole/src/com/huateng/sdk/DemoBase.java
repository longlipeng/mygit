package com.huateng.sdk;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.huateng.sdk.SDKConfig;
import com.huateng.sdk.SDKConstants;



/**
 * 名称： demo中用到的方法<br>
 * 日期： 2015-09<br>
 * 版本： 1.0.0 
 * 版权： 中国银联<br>
 * 声明：以下代码只是为了方便机构测试而提供的样例代码，机构可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 */
public class DemoBase {

	//默认配置的是UTF-8
	public static String encoding = "UTF-8";
	
	//固定值
	public static String version = SDKConfig.getConfig().getVersion();
	
	//后台服务对应的写法参照 BackRcvResponse.java
	public static String backUrl = SDKConfig.getConfig().getBackUrl();//受理方和发卡方自选填写的域[O]--后台通知地址

	// 商户发送交易时间 格式:yyyyMMddHHmmss
	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	// 商户发送交易时间 格式：HHmmss
	public static String getSendTime() {
		return new SimpleDateFormat("HHmmss").format(new Date());
	}
	
	
	// AN8..35 商户订单号，不能含"-"或"_"
	public static String getOrderId() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}
	
   /**
	 * 组装请求，返回报文字符串用于显示
	 * @param data
	 * @return
	 */
    public static String genHtmlResult(Map<String, String> data){

    	TreeMap<String, String> tree = new TreeMap<String, String>();
		Iterator<Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			tree.put(en.getKey(), en.getValue());
		}
		it = tree.entrySet().iterator();
		StringBuffer sf = new StringBuffer();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			String key = en.getKey(); 
			String value =  en.getValue();
			if("respCode".equals(key)){
				sf.append("<b>"+key + SDKConstants.EQUAL + value+"</br></b>");
			}else
				sf.append(key + SDKConstants.EQUAL + value+"</br>");
		}
		return sf.toString();
    }

	
	public static List<String> unzip(String zipFilePath,String outPutDirectory){
		List<String> fileList = new ArrayList<String>();
		try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFilePath));//输入源zip路径  
            BufferedInputStream bin = new BufferedInputStream(zin);
            BufferedOutputStream bout = null;
            File file=null;  
            ZipEntry entry;
            try {
                while((entry = zin.getNextEntry())!=null && !entry.isDirectory()){
                	file = new File(outPutDirectory,entry.getName());  
                    if(!file.exists()){  
                        (new File(file.getParent())).mkdirs();  
                    }
                    bout = new BufferedOutputStream(new FileOutputStream(file));  
                    int b;
                    while((b=bin.read())!=-1){  
                    	bout.write(b);  
                    }
                    bout.flush();
                    fileList.add(file.getAbsolutePath());
                    System.out.println(file+"解压成功");
                }
            } catch (IOException e) {  
                e.printStackTrace();  
            }finally{
                try {
					bin.close();
					zin.close();
					if(bout!=null){
						bout.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}  
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  
        }
		return fileList;
	}


}