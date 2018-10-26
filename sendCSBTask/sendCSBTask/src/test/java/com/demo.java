package com;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class demo {
public static void main(String[] args) {
//	String ss="{\"body\":{\"dataMap\":{\"MQ_check_resultField\":\"\",\"dataMD5\":\"f98034d949307a5ec8bee2c714185efe\",\"uploadFlowNo\":\"\",\"MQ_executer_resultField\":\"\",\"state\":\"1\",\"errorCode\":\"ORSC0002\",\"errorMsg\":\"\"},\"errorCode\":\"ORSC0002\",\"errorMsg\":\"\"},\"code\":200,\"message\":\"SUCCESS\"}";
//	JSONObject resultbody=JSONObject.fromObject(ss);
//	JSONObject body=(JSONObject)resultbody.get("body");
//
////	JSONObject resultdataMap=JSONObject.fromObject(body);
//	String errorCode=(String)body.get("errorCode");
//	System.out.println(errorCode);
	//System.out.println(JSONObject.fromObject(object.get("dataMap")).get("uploadFlowNo"));
//	SimpleDateFormat format=new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\");
//	String time=format.format(new Date());
//	System.out.println(time);
	try {
	String path="F:/zzz.txt";
	File file = new File(path);
	int length = 1024 * 1024 * 2;//2097152
	// 字节数组
	byte[] buffer = new byte[length];
	long fileLength = file.length();//文件长度
	System.out.println("fileLength"+fileLength);//598
		InputStream ins = new BufferedInputStream(new FileInputStream(path));
		int bytesRead = 0;
		bytesRead = ins.read(buffer);//返回实际读取的字节数
		System.out.println("bytesRead"+bytesRead);//598
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}
