package com.allinfinance.prepay.processor.ipos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.message.MessageSyncP023Req;
import com.allinfinance.prepay.message.MessageSyncP023Resp;
import com.allinfinance.prepay.processor.IProcessor;
import com.allinfinance.prepay.utils.CharUtil;
import com.allinfinance.prepay.utils.CheckDataMethod;
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.IDCardCheck;
import com.allinfinance.prepay.utils.ResponseCode;

@Service
public class SyncP023Processor implements IProcessor {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private CheckDataMethod checkDataMethod;
	
	@Override
	public BasicMessage process(BasicMessage req) throws Exception {
		logger.info("身份证姓名校验");
		MessageSyncP023Req reqData = (MessageSyncP023Req)req;
		MessageSyncP023Resp resp =(MessageSyncP023Resp) reqData.createResp() ;
		if(!checkDataMethod.checkIssueId(reqData.getISSUER_ID().trim())){
			resp.setRESP_CODE(ResponseCode.ISSUEID_ERROR);
			return resp;
		}
		
		if (StringUtils.isEmpty(reqData.getTXN_TYPE())){
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}
		
		if(StringUtils.isEmpty(reqData.getID_NO())){
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}else{
			String errMsg=IDCardCheck.IDCardValidate(reqData.getID_NO().toUpperCase());
			if (!StringUtils.isEmpty(errMsg)){
				resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
				return resp;
			}
		}
		
		if(StringUtils.isEmpty(reqData.getNAME())){
			resp.setRESP_CODE(ResponseCode.ISNULL);
			return resp;
		}else{
			if (!CharUtil.isChinese(reqData.getNAME())){
				resp.setRESP_CODE(ResponseCode.ILLEGAL_CHARACTER);
				return resp;
			}
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map = checkNameAndNo(reqData.getNAME(), reqData.getID_NO());
		String respCode = map.get("respCode");
		String message = map.get("message");
		resp.setRESP_CODE(respCode.equals("0000")?"0000":"0120");
		resp.setERROR_MSG(message);
		return resp;
	}
	
	private static HashMap<String, String> checkNameAndNo(String name,String no) throws Exception{
		String test2 ="<?xml version='1.0' encoding='utf-8'?><DATA><ORGID>"+Config.getOrgId()+"</ORGID><TRANSCODE>"+Config.getTransCode()+"</TRANSCODE><PROVIDER>"+Config.getProvider()+"</PROVIDER><ID>"+no+"</ID><NAME>"+name+"</NAME></DATA>";
		Socket s2;
		String s = "";
		String respMessage = null;
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			s2 = new Socket(Config.getCheckedIp(),Integer.parseInt(Config.getCheckedPort()));
			OutputStream os2 = s2.getOutputStream();
			String message = getUTF8StringFromGBKString(test2);
			int len = message.length();
			System.out.println(len);
			System.out.println(test2.length());
			os2.write(("0"+test2.getBytes("UTF-8").length+message).getBytes("UTF-8"));
			System.out.println("发送的数据："+("0"+len+message));
			os2.flush();
			
			InputStream ips = s2.getInputStream();
			InputStreamReader ipsr = new InputStreamReader(ips,"utf-8");
			BufferedReader br = new BufferedReader(ipsr);
			while((respMessage = br.readLine()) != null){
				s+=respMessage;
			}
			System.out.println("接收的数据："+s);
			String respCode = s.substring(s.indexOf("<RESPCODE>")+10, s.indexOf("</RESPCODE>"));
			String msg = s.substring(s.indexOf("<MESSAGE>")+9, s.indexOf("</MESSAGE>"));
			map.put("respCode", respCode);
			map.put("message", msg);
			s2.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public static void main(String[] args) {
		try {
			checkNameAndNo("燕井允","341281198208288387");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getUTF8StringFromGBKString(String gbkStr) {  
        try {  
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            throw new InternalError();  
        }  
    }  
      
    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {  
        int n = gbkStr.length();  
        byte[] utfBytes = new byte[3 * n];  
        int k = 0;  
        for (int i = 0; i < n; i++) {  
            int m = gbkStr.charAt(i);  
            if (m < 128 && m >= 0) {  
                utfBytes[k++] = (byte) m;  
                continue;  
            }  
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));  
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));  
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));  
        }  
        if (k < utfBytes.length) {  
            byte[] tmp = new byte[k];  
            System.arraycopy(utfBytes, 0, tmp, 0, k);  
            return tmp;  
        }  
        return utfBytes;  
    }  

}
