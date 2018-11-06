package com.webservice.util;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;





public class JsonUtil {
	public static void main(String[] args) throws JSONException {
		
		String a="{\"Errcode\":200,\"Errmsg\":\"成功返回\",\"Result\":{\"User_Id\":708229,\"Fied1\":\"05555\",\"Fied2\":\"05555\"},\"Method_Id\":0}";
		a=JsonUtil.convertToPospJson(a);
		System.out.println(a);
		
	}
	
	public static String  convertToPospJson(String str) throws JSONException {
		
		String code=String.valueOf(JsonUtil.getErrcode(str));
		String msg=JsonUtil.getErrmsg(str);
		String methodId=JsonUtil.getMethodId(str);
		Map<String, Object> map=new HashMap<String, Object>();
		String status="";
		if(code.equals("200")){
			String b=JsonUtil.getResult(str);
			status="1";
			map=JsonConvert.parseJSON2Map(b);
			map.put("Method_Id", methodId);
			map.put("Statu", status);
			map.put("Errcode", code);
			map.put("Errmsg", msg);
		}else{
			status="0";
			map.put("Method_Id", methodId);
			map.put("Errcode", code);
			map.put("Errmsg", msg);
		}
		
		JSONObject jsonObject =new JSONObject(map);
		return jsonObject.toString();
		
	}
	
	public static int  getErrcode(String str) throws JSONException {
		JSONObject jsonObj = new JSONObject(str); 
		int Item_Count =jsonObj.getInt("Errcode");  
		return Item_Count;
		
	}
	
	public static String getResult(String str) throws JSONException {
		JSONObject jsonObj = new JSONObject(str); 
		String Item_Count =jsonObj.getString("Result");  
		return Item_Count;
		
	}
	public static String  getErrmsg(String str) throws JSONException {
		JSONObject jsonObj = new JSONObject(str); 
		String Item_Count =jsonObj.getString("Errmsg");  
		return Item_Count;
		
	}
	
	public static String  getMethodId(String str) throws JSONException {
		JSONObject jsonObj = new JSONObject(str); 
		 String Method_ID = jsonObj.getString("Method_Id");  
		return Method_ID;
		
	}
	
	public  String  getStatus(String str) throws JSONException {
		JSONObject jsonObj = new JSONObject(str); 
		 String Method_ID = jsonObj.getString("Status");  
		return Method_ID;
		
	}
	
	public  String  getUserId(String str) throws JSONException {
		JSONObject jsonObj = new JSONObject(str); 
		 String Method_ID = jsonObj.getString("User_Id");  
		return Method_ID;
		
	}
	
	

}
